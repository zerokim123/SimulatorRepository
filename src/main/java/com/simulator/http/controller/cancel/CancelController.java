package com.simulator.http.controller.cancel;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
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
import com.simulator.dataservice.Service.CouponInfoService;
import com.simulator.dataservice.Service.LoginMngmtService;
import com.simulator.dataservice.Service.TradeInfoService;
import com.simulator.dataservice.Service.TradeSeqService;
import com.simulator.dataservice.entity.CouponInfoEntity;
import com.simulator.dataservice.entity.LoginMngmtEntity;
import com.simulator.dataservice.entity.TradeInfoEntity;
import com.simulator.telegram.if34.RequestTeleIF34Dto;
import com.simulator.telegram.if34.ResponseTeleIF33Dto;
import com.simulator.telegram.if34.TeleIF33DataDto;

@RestController
@RequestMapping()
@Validated
public class CancelController {

    private static final Logger log = Logger.getLogger(CancelController.class);

    @Autowired
    private CouponInfoService couponInfoService;

    @Autowired
    private LoginMngmtService loginMngmtService;

    @Autowired
    private TradeSeqService tradeSeqService;

    @Autowired
    private TradeInfoService tradeInfoService;

    /**利用取消処理制御フラグ：異常*/
    private static String FAULT = "1";

    /**利用取消処理制御フラグ：通信エラー408*/
    private static String STS_408 = "2";

    /**利用取消処理制御フラグ：通信エラー504*/
    private static String STS_504 = "3";

    /**利用取消処理制御フラグ：通信エラー400*/
    private static String STS_400 = "4";

    @RequestMapping(value = CommonConst.CANCEL, method = {
            RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String runCancel(@Validated @RequestBody String strBody) {

        log.info("利用取消処理を開始");
        log.info("利用取消要求電文：" + strBody);
        // 初期化
        ResponseTeleIF33Dto if33Dto = new ResponseTeleIF33Dto();
        String responseStr = "";

        // 受信電文Parse
        RequestTeleIF34Dto if34Dto = CommonUtil.jsonConvertObject(strBody, RequestTeleIF34Dto.class);

        // 受信電文Parse結果NULLの場合、ステータスコード400を返却
        if (if34Dto == null) {
            throw new HttpStatus400Exception();
        }

        // ログイン情報取得
        LoginMngmtEntity userInfo = loginMngmtService.getOne(if34Dto.getTerminalLoginId());

        //ログイン管理情報の検索結果、0の場合
        if (userInfo == null) {
            if33Dto.setResultCode(1);
            if33Dto.setErrorCode("500001");
            if33Dto.setErrorMessage("利用できないクーポンです。");
        } else {

            // 利用取消処理制御フラグを判定
            // 通信エラー408
            if (STS_408.equals(userInfo.getCancelControl())) {
                throw new HttpStatus408Exception();
                // 通信エラー504
            } else if (STS_504.equals(userInfo.getCancelControl())) {
                throw new HttpStatus504Exception();
                // 通信エラー400
            }
            if (STS_400.equals(userInfo.getCancelControl())) {
                throw new HttpStatus400Exception();
                // 異常
            } else if (FAULT.equals(userInfo.getCancelControl())) {
                if33Dto.setResultCode(1);
                if33Dto.setErrorCode("500002");
                if33Dto.setErrorMessage("只今、クーポンサービスをご利用頂けません。");
            } else {

                // 取消対象レコードを取得する
                TradeInfoEntity tradeInfo = tradeInfoService.getTradeInfo(if34Dto.getTransactionID());

                if (tradeInfo == null) {
                    log.error("取引情報がありません。");
                    throw new HttpStatus500Exception();

                } else {

                    // クーポン情報を取得
                    CouponInfoEntity couponInfo = couponInfoService.findOne(tradeInfo.getPartnerCompUserId());

                    // クーポンユーザ情報を取得の検索結果、0の場合
                    if (couponInfo == null) {
                        log.error("クーポンユーザ情報ありません。");
                        throw new HttpStatus500Exception();
                    }

                    /**▼ クーポン残高更新▼**/
                    double balance = 0;
                    Integer couponRemainUseNum = 0;
                    // ギフトの場合
                    if (couponInfo.getIncentiveOfferType().equals("2")) {
                        balance = tradeInfo.getDiscountKingk();
                        couponRemainUseNum = 1;
                    // ギフトではない場合
                    } else {
                        balance = couponInfo.getBalance() + tradeInfo.getDiscountKingk();
                        couponRemainUseNum = Integer.valueOf(tradeInfo.getCouponRemainUseNum())  + 1;
                    }


                    couponInfo.setPartnerCompUserId(tradeInfo.getPartnerCompUserId());
                    couponInfo.setBalance(balance);
                    couponInfo.setCouponRemainUseNum(couponRemainUseNum);
                    couponInfoService.save(couponInfo);
                    /**▲ クーポン残高更新 ▲**/

                    /**▼ 利用情報更新 ▼**/
                    tradeInfo.setStatus("1");
                    tradeInfoService.save(tradeInfo);
                    /**▲ 利用情報更新 ▲**/

                    /**▼ 利用取消情報登録 ▼**/
                    // 利用取消トランザクションID作成
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                    String strDate = simpleDateFormat.format(date);
                    String transactionId = strDate.concat(tradeSeqService.getSeq());
                    // 利用取消登録情報作成
                    TradeInfoEntity tradeInfoInsert = new TradeInfoEntity();
                    BeanUtils.copyProperties(tradeInfo, tradeInfoInsert);
                    tradeInfoInsert.setTransactionId(transactionId);
                    tradeInfoInsert.setRegistCancelDivision("2");
                    tradeInfoInsert.setStatus("0");
                    tradeInfoService.insertByValue(tradeInfoInsert);
                    /**▲ 利用取消情報登録 ▲**/

                    /**▼ 応答電文作成 ▼**/
                    TeleIF33DataDto data = new TeleIF33DataDto();
                    data.setTransactionID(transactionId);
                    String campaignUseDatetime = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
                    data.setCampaignUseDatetime(campaignUseDatetime);
                    if33Dto.setData(data);
                    /**▲ 応答電文作成 ▲**/
                    if33Dto.setResultCode(0);
                }
            }
        }

        // 応答電文項目.処理日時を設定
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if33Dto.setTime(time);

        // 応答電文をJSON文字列に変換
        responseStr = CommonUtil.convertJson(if33Dto);

        log.info("利用取消応答電文：" + responseStr);
        log.info("利用取消処理を終了");
        // 応答電文返却
        return responseStr;
    }

    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    private class HttpStatus408Exception extends RuntimeException {
        private static final long serialVersionUID = -160295958892797373L;
    }

    @ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
    private class HttpStatus504Exception extends RuntimeException {
        private static final long serialVersionUID = 3365073811057154529L;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private class HttpStatus400Exception extends RuntimeException {
        private static final long serialVersionUID = -7748389402158493135L;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private class HttpStatus500Exception extends RuntimeException {
        private static final long serialVersionUID = 8277583190470653805L;
    }
}
