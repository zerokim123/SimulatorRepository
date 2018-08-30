package com.simulator.http.controller.use;

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
import com.simulator.dataservice.Service.CouponInfoService;
import com.simulator.dataservice.Service.LoginMngmtService;
import com.simulator.dataservice.Service.TradeInfoService;
import com.simulator.dataservice.Service.TradeSeqService;
import com.simulator.dataservice.entity.CouponInfoEntity;
import com.simulator.dataservice.entity.LoginMngmtEntity;
import com.simulator.dataservice.entity.TradeInfoEntity;
import com.simulator.telegram.if30.RequestTeleIF30Dto;
import com.simulator.telegram.if30.ResponseTeleIF29Dto;
import com.simulator.telegram.if30.TeleIF29DataDto;

@RestController
@RequestMapping()
@Validated
public class UseController {

    private static final Logger log = Logger.getLogger(UseController.class);

    @Autowired
    private CouponInfoService couponInfoService;

    @Autowired
    private LoginMngmtService loginMngmtService;

    @Autowired
    private TradeSeqService tradeSeqService;

    @Autowired
    private TradeInfoService tradeInfoService;

    /** 消込あり：1*/
    private static final String USED_FLG_1 = "1";

    /** 消込なし：0*/
    private static final String USED_FLG_0 = "0";

    /** 1：消込確認異常 */
    private static final String USE_CONFIRM_FAULT = "1";

    /** 2：消込確認 408*/
    private static final String USE_CONFIRM_408 = "2";

    /** 3：消込確認 504*/
    private static final String USE_CONFIRM_504 = "3";

    /** 4：消込確認 400*/
    private static final String USE_CONFIRM_400 = "4";

    /** 1：消込異常 */
    private static final String USE_FAULT = "1";

    /** 2：消込 408*/
    private static final String USE_408 = "2";

    /** 3：消込 504*/
    private static final String USE_504 = "3";

    /** 4：消込 400*/
    private static final String USE_400 = "4";

    /**画面金額入力有無制御フラグ:"0"無*/
    private static String KINGK_EXIST_FLG_OFF = "0";

    /**インセンティブ提供方法:"2"ギフト*/
    private static String GIFT = "2";

    /**取引制御フラグ:"4"未達*/
    private static String UNACHIEVED = "4";

    @RequestMapping(value = CommonConst.USE, method = {
            RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String runUse(@Validated @RequestBody String strBody) {

        log.info("クーポン消込処理を開始");
        log.info("クーポン消込要求電文：" + strBody);
        // 初期化
        ResponseTeleIF29Dto if29Dto = new ResponseTeleIF29Dto();
        String responseStr = "";

        // 受信電文Parse
        RequestTeleIF30Dto if30Dto = CommonUtil.jsonConvertObject(strBody, RequestTeleIF30Dto.class);

        // 受信電文Parse結果NULLの場合、ステータスコード400を返却
        if (if30Dto == null) {
            throw new HttpStatus400Exception();
        }

        // クーポンユーザ情報を取得
        LoginMngmtEntity loginInfo = loginMngmtService.findByPk(if30Dto.getTerminalLoginId());

        //クーポンユーザ情報の検索結果、0の場合
        if (loginInfo == null) {
            if29Dto.setResultCode(1);
            if29Dto.setErrorCode("100003");
            if29Dto.setErrorMessage("利用できないクーポンです。");
        } else {

            // 利用業務処理結果制御フラグを判定
            // 通信エラー408
            if ((USE_CONFIRM_408.equals(loginInfo.getUseOffControl()) && USED_FLG_0.equals(if30Dto.getCouponUsedFlg()))
                    || (USE_408.equals(loginInfo.getUseOnControl()) && USED_FLG_1.equals(if30Dto.getCouponUsedFlg()))) {
                throw new HttpStatus408Exception();
                // 通信エラー504
            } else if (USE_CONFIRM_504.equals(loginInfo.getUseOffControl())
                    && USED_FLG_0.equals(if30Dto.getCouponUsedFlg())
                    || (USE_504.equals(loginInfo.getUseOnControl()) && USED_FLG_1.equals(if30Dto.getCouponUsedFlg()))) {
                throw new HttpStatus504Exception();
                // 通信エラー400
            } else if (USE_CONFIRM_400.equals(loginInfo.getUseOffControl())
                    && USED_FLG_0.equals(if30Dto.getCouponUsedFlg())
                    || (USE_400.equals(loginInfo.getUseOnControl())
                            &&
                            USED_FLG_1.equals(if30Dto.getCouponUsedFlg()))) {
                throw new HttpStatus400Exception();
                // 消込確認異常
            } else if (USE_CONFIRM_FAULT.equals(loginInfo.getUseOffControl())
                    && USED_FLG_0.equals(if30Dto.getCouponUsedFlg())) {
                if29Dto.setResultCode(1);
                if29Dto.setErrorCode("100004");
                if29Dto.setErrorMessage("本クーポンはご利用頂けません。");
                // 消込異常
            } else if (USE_FAULT.equals(loginInfo.getUseOnControl()) && USED_FLG_1.equals(if30Dto.getCouponUsedFlg())) {
                if29Dto.setResultCode(1);
                if29Dto.setErrorCode("100005");
                if29Dto.setErrorMessage("本クーポンはご利用頂けません。");
            } else {

                // クーポンユーザ情報を取得
                CouponInfoEntity couponInfo = couponInfoService
                        .findOne(if30Dto.getPartnerCompUserID());

                // クーポン情報取得0件の場合
                if (couponInfo == null) {
                    log.error("クーポン属性テーブルから情報取得出来ません。");
                    log.error("キャンペーンIDと紐づく情報がありません。");
                    throw new HttpStatus500Exception();

                } else if (Double.valueOf(if30Dto.getTransactionKingk()) > couponInfo.getBalance()) {
                    if29Dto.setResultCode(1);
                    if29Dto.setErrorCode("100006");
                    if29Dto.setErrorMessage("ご利用可能額を超えています。再度ご確認お願いします。");
                } else {
                    TeleIF29DataDto data = new TeleIF29DataDto();
                    // クーポン消込ありの場合
                    if (USED_FLG_1.equals(if30Dto.getCouponUsedFlg())) {
                        data = couponUseProssecing(couponInfo, if30Dto);
                    } else {
                        data = couponConfirmUseProssecing(couponInfo, if30Dto);
                    }
                    if29Dto.setData(data);
                    // 処理結果コード0正常を設定
                    if29Dto.setResultCode(0);
                    // 取引制御フラグが未達の場合
                    if (UNACHIEVED.equals(couponInfo.getRegistCancelDivision())) {
                        if29Dto.setErrorCode("I00006");
                        if29Dto.setErrorMessage("未達エラーメッセージ");
                    }
                }
            }
        }

        // 応答電文項目.処理日時を設定
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if29Dto.setTime(time);

        // 応答電文をJSON文字列に変換
        responseStr = CommonUtil.convertJson(if29Dto);

        log.info("クーポン消込応答電文：" + responseStr);
        log.info("クーポン消込処理を終了");
        // 応答電文返却
        return responseStr;
    }

    /**クーポン消込なしの応答電文作成処理
     * @parameter CouponUserInfoBean
     * @parameter CouponPrptyBean
     *  @parameter RequestTeleIF30Dto
     * @return TeleIF29DataDto
     * */
    private TeleIF29DataDto couponConfirmUseProssecing(final CouponInfoEntity couponInfo,
            final RequestTeleIF30Dto if30Dto) {
        // 応答電文データ部の初期化
        TeleIF29DataDto if29Data = new TeleIF29DataDto();

        // 金額入力なしの場合
        if (KINGK_EXIST_FLG_OFF.equals(couponInfo.getKingkExistFlg())) {
            // ギフトの場合
            if (GIFT.equals(couponInfo.getIncentiveOfferType())) {
                if29Data.setTransactionKingk((double) 0);
                if29Data.setAfterDiscountKingk((double) 0);
                if29Data.setDiscountKingk((double) 0);
                // ギフト以外の場合
            } else if (Integer.valueOf(if30Dto.getTransactionKingk()) == 0){
                if29Data.setTransactionKingk((double) 0);
                if29Data.setAfterDiscountKingk((double) 0 - couponInfo.getDiscountKingk());
                if29Data.setDiscountKingk(couponInfo.getDiscountKingk());
            } else {
                if29Data.setTransactionKingk((double) 0);
                if29Data.setAfterDiscountKingk(Double.valueOf(if30Dto.getTransactionKingk()) - couponInfo.getDiscountKingk());
                if29Data.setDiscountKingk(couponInfo.getDiscountKingk());
            }
            // 金額入力ありの場合
        } else {

            if29Data.setTransactionKingk(Double.valueOf(if30Dto.getTransactionKingk()));
            if29Data.setAfterDiscountKingk( Double.valueOf(if30Dto.getTransactionKingk()) - couponInfo.getDiscountKingk());
            // ギフトの場合
            if (GIFT.equals(couponInfo.getIncentiveOfferType())) {
                if29Data.setDiscountKingk((double) 0);
                // ギフト以外の場合
            } else {
                if29Data.setDiscountKingk(couponInfo.getDiscountKingk());
            }
        }
        return if29Data;
    }

    /**
     * クーポン消込ありの応答電文作成処理
     * @parameter CouponUserInfoBean
     * @parameter CouponPrptyBean
     *  @parameter RequestTeleIF30Dto
     * @return TeleIF29DataDto
     * */
    private TeleIF29DataDto couponUseProssecing(final CouponInfoEntity couponInfo,
            final RequestTeleIF30Dto if30Dto) {
        // 応答電文データ部の初期化
        TeleIF29DataDto if29Data = new TeleIF29DataDto();

        // 現在の日時を取得
        Date date = new Date();
        /** ▼トランザクションIDを生成▼**/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String strDate = simpleDateFormat.format(date);
        String transactionID = strDate.concat(tradeSeqService.getSeq());
        /** ▲トランザクションIDを生成▲**/
        /** ▼キャンペン利用日時を設定▼**/
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss");
        String campaignUseDatetime = simpleDate.format(date);
        /** ▲キャンペン利用日時を設定▲**/

        // 金額入力なしの場合
        if (KINGK_EXIST_FLG_OFF.equals(couponInfo.getKingkExistFlg())) {
            // ギフトの場合
            if (GIFT.equals(couponInfo.getIncentiveOfferType())) {
                if29Data.setTransactionKingk((double) 0);
                if29Data.setAfterDiscountKingk((double) 0);
                if29Data.setDiscountKingk((double) 0);
                // ギフト以外の場合
            } else if (Integer.valueOf(if30Dto.getTransactionKingk()) == 0){
                if29Data.setTransactionKingk((double) 0);
                if29Data.setAfterDiscountKingk((double) 0 - couponInfo.getDiscountKingk());
                if29Data.setDiscountKingk(couponInfo.getDiscountKingk());
            } else {
                if29Data.setTransactionKingk((double) 0);
                if29Data.setAfterDiscountKingk(Double.valueOf(if30Dto.getTransactionKingk()) - couponInfo.getDiscountKingk());
                if29Data.setDiscountKingk(couponInfo.getDiscountKingk());
            }
            // 金額入力ありの場合
        } else {

            if29Data.setTransactionKingk(Double.valueOf(if30Dto.getTransactionKingk()));
            if29Data.setAfterDiscountKingk(Double.valueOf(if30Dto.getTransactionKingk()) - couponInfo.getDiscountKingk());
            // ギフトの場合
            if (GIFT.equals(couponInfo.getIncentiveOfferType())) {
                if29Data.setDiscountKingk((double) 0);
                // ギフト以外の場合
            } else {
                if29Data.setDiscountKingk(couponInfo.getDiscountKingk());
            }
        }

        /**▼クーポン残利用回数を計算▼**/
        String couponRemainUseNum = "";
        if (couponInfo.getCouponRemainUseNum() < 0) {
            couponRemainUseNum = "-1";
        } else {
            couponRemainUseNum = String.valueOf(couponInfo.getCouponRemainUseNum() - 1);
        }
        if29Data.setCouponRemainUseNum(couponRemainUseNum);
        /**▲クーポン残利用回数を計算▲**/

        if29Data.setTransactionID(transactionID);
        if29Data.setCampaignUseDatetime(campaignUseDatetime);
        if29Data.setPartnerCompUserID(couponInfo.getPartnerCompUserId());

        /**▼取引情報をDBに反映▼**/
        TradeInfoEntity tradeInfoInser = new TradeInfoEntity();
        tradeInfoInser.setTransactionId(transactionID);
        tradeInfoInser.setPartnerCompUserId(if29Data.getPartnerCompUserID());
        tradeInfoInser.setTransactionKingk(if29Data.getTransactionKingk());
        tradeInfoInser.setAfterDiscountKingk(if29Data.getAfterDiscountKingk());
        tradeInfoInser.setDiscountKingk(if29Data.getDiscountKingk());
        tradeInfoInser.setIncentiveOfferType(couponInfo.getIncentiveOfferType());
        tradeInfoInser.setCampaignName(couponInfo.getCampaignName());
        tradeInfoInser.setCampaignComment(couponInfo.getCampaignComment());
        tradeInfoInser.setRegistCancelDivision(couponInfo.getRegistCancelDivision());
        tradeInfoInser.setStatus("0");
        tradeInfoInser.setTerminalLoinId(if30Dto.getTerminalLoginId());
        tradeInfoInser.setCouponRemainUseNum(couponRemainUseNum);
        tradeInfoService.insertByValue(tradeInfoInser);
        /**▲取引情報をDBに反映▲**/

        /**▼クーポン残高情報をDBに反映▼**/
        Double balance = couponInfo.getBalance() - couponInfo.getDiscountKingk();

        couponInfo.setBalance(balance);
        couponInfo.setCouponRemainUseNum(Integer.valueOf(couponRemainUseNum));
        couponInfoService.save(couponInfo);
        /**▲クーポン残高情報をDBに反映▲**/

        return if29Data;

    }

    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    private class HttpStatus408Exception extends RuntimeException {
        private static final long serialVersionUID = 3303072281551296489L;
    }

    @ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
    private class HttpStatus504Exception extends RuntimeException {
        private static final long serialVersionUID = 7604508793140677611L;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private class HttpStatus400Exception extends RuntimeException {
        private static final long serialVersionUID = 2217588269501541903L;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private class HttpStatus500Exception extends RuntimeException {
        private static final long serialVersionUID = 1300406671277144724L;

    }
}
