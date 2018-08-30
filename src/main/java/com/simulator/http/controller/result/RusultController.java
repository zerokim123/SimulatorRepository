package com.simulator.http.controller.result;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.simulator.telegram.if44.RequestTeleIF44Dto;
import com.simulator.telegram.if44.ResponseTeleIF43Dto;
import com.simulator.telegram.if44.TeleIF43DataDto;
import com.simulator.telegram.if44.TeleIF43LoopDto;

@RestController
@RequestMapping()
@Validated
public class RusultController {

    private static final Logger log = Logger.getLogger(RusultController.class);

    @Autowired
    private LoginMngmtService loginMngmtService;

//    @Autowired
//    private TradeInfoRepository tradeInfoRepository;

    @Autowired
    private TradeInfoService tradeInfoService;


    /** 1：一覧照会異常 */
    private static final String FAULT = "1";

    /** 2：一覧照会 408*/
    private static final String STS_408 = "2";

    /** 3：一覧照会 504*/
    private static final String STS_504 = "3";

    /** 4：一覧照会 400*/
    private static final String STS_400 = "4";

    @RequestMapping(value = CommonConst.RESULT, method = {
            RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String runRusult(@Validated @RequestBody String strBody) throws ParseException {

        log.info("取引一覧照会処理を開始");
        log.info("取引一覧照会要求電文：" + strBody);
        // 初期化
        ResponseTeleIF43Dto if43Dto = new ResponseTeleIF43Dto();
        String responseStr = "";

        // 受信電文Parse
        RequestTeleIF44Dto if44Dto = CommonUtil.jsonConvertObject(strBody, RequestTeleIF44Dto.class);

        // 受信電文Parse結果NULLの場合、ステータスコード400を返却
        if (if44Dto == null) {
            throw new HttpStatus400Exception();
        }

        // ログイン管理情報を取得
        LoginMngmtEntity loginInfo = loginMngmtService.findByPk(if44Dto.getTerminalLoginId());

        //ログイン管理情報の検索結果、0の場合
        if (loginInfo == null) {
            if43Dto.setResultCode(1);
            if43Dto.setErrorCode("200001");
            if43Dto.setErrorMessage("本サービスはご利用頂けません。");
        } else {

            // 一覧照会処理制御フラグを判定
            // 異常
            if (FAULT.equals(loginInfo.getRusultControl())) {
                if43Dto.setResultCode(1);
                if43Dto.setErrorCode("200002");
                if43Dto.setErrorMessage("クーポンサービス利用時間外です。");
             // 通信エラー408
            } else if (STS_408.equals(loginInfo.getRusultControl())) {
                throw new HttpStatus408Exception();
             // 通信エラー504
            } else if (STS_504.equals(loginInfo.getRusultControl())) {
                throw new HttpStatus504Exception();
            } else if (STS_400.equals(loginInfo.getRusultControl())) {
                throw new HttpStatus400Exception();
            }else {

                // 応答電文.データ部の初期化
                List<TeleIF43LoopDto> dataList = new ArrayList<TeleIF43LoopDto>();

                /**▼キャンペーン利用日時により取引情報一覧を取得▼**/
                TradeInfoEntity getListentity = new TradeInfoEntity();
                getListentity.setTerminalLoinId(if44Dto.getTerminalLoginId());
                // キャンペーン利用日時
                Timestamp tradeTime = new Timestamp(new SimpleDateFormat("yyyyMMdd").parse(if44Dto.getCampaignUsedayTo()).getTime());
                getListentity.setTradeTime(tradeTime);

                List<TradeInfoEntity> tradeList = tradeInfoService.selectTradeListByValue(getListentity);

                /**▲キャンペーン利用日時により取引情報一覧を取得▲**/
                // 取引一覧取得結果0件の場合
                if (tradeList.size() == 0) {
                    if43Dto.setResultCode(1);
                    if43Dto.setErrorCode("200003");
                    if43Dto.setErrorMessage("ご指定の日付の取引情報がありません。");
                } else {
                    TeleIF43DataDto rusultData = new TeleIF43DataDto();

                    /**▼取引情報一覧を応答電文データリストに格納▼**/
                    for (TradeInfoEntity couponTradeInfoBean : tradeList) {
                        TeleIF43LoopDto data = new TeleIF43LoopDto();
                        data.setTransactionID(couponTradeInfoBean.getTransactionId());
                        data.setCampaignUseDatetime(new SimpleDateFormat("yyyyMMddHHmmss").format(couponTradeInfoBean.getTradeTime()));
                        data.setTransactionKingk(couponTradeInfoBean.getTransactionKingk());
                        data.setDiscountKingk(couponTradeInfoBean.getTransactionKingk());
                        data.setAfterDiscountKingk(couponTradeInfoBean.getAfterDiscountKingk());
                        data.setIncentiveOfferType(couponTradeInfoBean.getIncentiveOfferType());
                        data.setRegistCancelDivision(couponTradeInfoBean.getRegistCancelDivision());
                        dataList.add(data);
                    }
                    /**▲取引情報一覧を応答電文データリストに格納▲**/
                    /**▼取引情報一覧を応答電文データ部に格納▼**/
                    rusultData.setData(dataList);
                    rusultData.setPageSize(dataList.size());
                    rusultData.setStart(0);
                    rusultData.setTotalCount(dataList.size());
                    rusultData.setCurrentPageNo(0);
                    rusultData.setTotal(dataList.size());
                    /**▲取引情報一覧を応答電文データ部に格納▲**/
                    /**▼応答電文項目を設定▼**/
                    if43Dto.setData(rusultData);
                    if43Dto.setResultCode(0);
                    /**▲応答電文項目を設定▲**/
                }
            }

        }

        // 応答電文項目.処理日時を設定
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if43Dto.setTime(time);

        // 応答電文をJSON文字列に変換
        responseStr = CommonUtil.convertJson(if43Dto);

        log.info("取引一覧照会応答電文：" + responseStr);
        log.info("取引一覧照会処理を終了");
        // 応答電文返却
        return responseStr;
    }

    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    private class HttpStatus408Exception extends RuntimeException {
        private static final long serialVersionUID = 2750863489864695268L;
    }

    @ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
    private class HttpStatus504Exception extends RuntimeException {
        private static final long serialVersionUID = -7605024444518736433L;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private class HttpStatus400Exception extends RuntimeException {
        private static final long serialVersionUID = -9047069459786080619L;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private class HttpStatus500Exception extends RuntimeException {
        private static final long serialVersionUID = -8529791685780096355L;
    }
}
