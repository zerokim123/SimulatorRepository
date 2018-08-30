package com.simulator.http.controller.detail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simulator.common.CommonConst;
import com.simulator.common.CommonUtil;
import com.simulator.dataservice.Service.LoginMngmtService;
import com.simulator.dataservice.Service.TradeInfoService;
import com.simulator.dataservice.entity.LoginMngmtEntity;
import com.simulator.dataservice.entity.TradeInfoEntity;
import com.simulator.telegram.if46.RequestTeleIF46Dto;
import com.simulator.telegram.if46.ResponseTeleIF45Dto;
import com.simulator.telegram.if46.TeleIF45DataDto;

@RestController
@RequestMapping()
@Validated
public class DetailController {

    private static final Logger log = Logger.getLogger(DetailController.class);

    @Autowired
    private LoginMngmtService loginMngmtService;

    @Autowired
    private TradeInfoService tradeInfoService;

    /** 1：詳細照会異常 */
    private static final String CONTROL_FAULT = "1";

    /** 2：詳細照会 408*/
    private static final String CONTROL408 = "2";

    /** 3：詳細照会 504*/
    private static final String CONTROL504 = "3";

    /** 4：詳細照会 400*/
    private static final String CONTROL400 = "4";

    @RequestMapping(value = CommonConst.DETAIL, method = {
            RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String runDetail(@Validated @RequestBody String strBody) throws ParseException {

        log.info("詳細照会処理を開始");
        log.info("詳細照会要求電文：" + strBody);
        // 初期化
        ResponseTeleIF45Dto if45Dto = new ResponseTeleIF45Dto();
        String responseStr = "";

        // 受信電文Parse
        RequestTeleIF46Dto if46Dto = CommonUtil.jsonConvertObject(strBody, RequestTeleIF46Dto.class);

        // 受信電文Parse結果NULLの場合、ステータスコード400を返却
        if (if46Dto == null) {
            throw new HttpStatus400Exception();
        }

        // ログイン管理情報を取得
        LoginMngmtEntity loginInfo = loginMngmtService.findByPk(if46Dto.getTerminalLoginId());

        //ログイン管理情報の検索結果、0の場合
        if (loginInfo == null) {
            if45Dto.setResultCode(1);
            if45Dto.setErrorCode("300001");
            if45Dto.setErrorMessage("本サービスはご利用頂けません。");
        } else {

            // 詳細照会処理制御フラグを判定
            // 異常
            if (CONTROL_FAULT.equals(loginInfo.getDetailControl())) {
                if45Dto.setResultCode(1);
                if45Dto.setErrorCode("300002");
                if45Dto.setErrorMessage("クーポンサービス利用時間外です。");
             // 通信エラー408
            } else if (CONTROL408.equals(loginInfo.getDetailControl())) {
                throw new HttpStatus408Exception();
             // 通信エラー504
            } else if (CONTROL504.equals(loginInfo.getDetailControl())) {
                throw new HttpStatus504Exception();
             // 通信エラー400
            } else if (CONTROL400.equals(loginInfo.getDetailControl())) {
                throw new HttpStatus400Exception();
             // トランザクションID長さ不整合
            } else if (if46Dto.getTransactionID().length() != 15) {
                log.info("トランザクションID長さ不整合：[受信トランザクションID=" + if46Dto.getTransactionID() + "]");

                if45Dto.setResultCode(1);
                if45Dto.setErrorCode("300004");
                if45Dto.setErrorMessage("取引番号形式が誤っています。<br>ご確認してください。");

                // 応答電文項目.処理日時を設定
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                if45Dto.setTime(time);

                // 応答電文をJSON文字列に変換
                responseStr = CommonUtil.convertJson(if45Dto);

                log.info("詳細照会応答電文：" + responseStr);
                log.info("詳細照会処理終了");
                // 応答電文返却
                return responseStr;

                //正常
            } else {
                // トランザクションIDと紐づく取引情報を取得
                TradeInfoEntity tradeInfo = tradeInfoService.selectByPk(if46Dto.getTransactionID());

                // 取引情報が存在しない場合
                if (tradeInfo == null) {
                    if45Dto.setResultCode(1);
                    if45Dto.setErrorCode("300003");
                    if45Dto.setErrorMessage("ご指定の取引情報ありません。");
                } else {
                    /**▼取引情報を応答電文に設定▼**/
                    TeleIF45DataDto data = new TeleIF45DataDto();
                    String campaignUseDatetime = new SimpleDateFormat("yyyyMMddHHmmss").format(tradeInfo.getTradeTime());
                    data.setCampaignUseDatetime(campaignUseDatetime);
                    data.setTransactionKingk(tradeInfo.getTransactionKingk());
                    data.setAfterDiscountKingk(tradeInfo.getAfterDiscountKingk());
                    data.setDiscountKingk(tradeInfo.getDiscountKingk());
                    data.setPartnerCompUserID(tradeInfo.getPartnerCompUserId());
                    data.setCampaignName(tradeInfo.getCampaignName());
                    data.setCampaigncomment(tradeInfo.getCampaignComment());
                    data.setIncentiveOfferType(tradeInfo.getIncentiveOfferType());
                    data.setRegistCancelDivision(tradeInfo.getRegistCancelDivision());
                    data.setStatus(tradeInfo.getStatus());
                    data.setCouponRemainUseNum(tradeInfo.getCouponRemainUseNum());
                    // 当該取引が取消、または、未達の場合、取消元トランザクションIDを設定する
                    if (tradeInfo.getRegistCancelDivision().equals("2")/*取消*/
                            || tradeInfo.getRegistCancelDivision().equals("3")/*未達*/) {
                        data.setBaseTransactionID(tradeInfo.getTransactionId());
                    }
                    if45Dto.setData(data);
                    if45Dto.setResultCode(0);
                    /**▲取引情報を応答電文に設定▲**/
                }
            }
        }

        // 応答電文項目.処理日時を設定
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if45Dto.setTime(time);

        // 応答電文をJSON文字列に変換
        responseStr = CommonUtil.convertJson(if45Dto);

        log.info("詳細照会応答電文：" + responseStr);
        log.info("詳細照会処理を終了");
        // 応答電文返却
        return responseStr;
    }

    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    private class HttpStatus408Exception extends RuntimeException {
        private static final long serialVersionUID = 6122479922018060915L;
    }

    @ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
    private class HttpStatus504Exception extends RuntimeException {
        private static final long serialVersionUID = 6093957642675560078L;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private class HttpStatus400Exception extends RuntimeException {
        private static final long serialVersionUID = 2264695741963670200L;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private class HttpStatus500Exception extends RuntimeException {
        private static final long serialVersionUID = 5475047317698000080L;
    }
}
