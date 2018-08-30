package com.simulator.http.controller.confirm;

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
import com.simulator.dataservice.entity.CouponInfoEntity;
import com.simulator.dataservice.entity.LoginMngmtEntity;
import com.simulator.telegram.if22.RequestTeleIF22Dto;
import com.simulator.telegram.if22.ResponseTeleIF21Dto;
import com.simulator.telegram.if22.TeleIF21DataDto;

@RestController
@RequestMapping()
@Validated
public class ConfirmController {

    private static final Logger log = Logger.getLogger(ConfirmController.class);

    @Autowired
    private CouponInfoService couponInfoService;

    @Autowired
    private LoginMngmtService loginMngmtService;

    /**利用確認処理制御フラグ：異常*/
    private static String FAULT = "1";

    /**利用確認処理制御フラグ：通信エラー408*/
    private static String STS_408 = "2";

    /**利用確認処理制御フラグ：通信エラー504*/
    private static String STS_504 = "3";

    /**利用確認処理制御フラグ：通信エラー400*/
    private static String STS_400 = "4";

    /**画面金額入力有無制御フラグ:"0"無*/
    private static String KINGK_EXIST_FLG_OFF = "0";

    @RequestMapping(value = CommonConst.CONFIRM, method = {
            RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String runConfirm(@Validated @RequestBody String strBody) {

        log.info("利用確認処理を開始");
        log.info("利用確認要求電文：" + strBody);
        // 初期化
        ResponseTeleIF21Dto if21Dto = new ResponseTeleIF21Dto();
        String responseStr = "";

        // 受信電文Parse
        RequestTeleIF22Dto if22Dto = CommonUtil.jsonConvertObject(strBody, RequestTeleIF22Dto.class);

        // 受信電文Parse結果NULLの場合、ステータスコード400を返却
        if (if22Dto == null) {
            throw new HttpStatus400Exception();
        }

        // ログイン情報取得
        LoginMngmtEntity loginInfo = loginMngmtService.findByPk(if22Dto.getTerminalLoginId());

        //ログイン管理情報の検索結果、0の場合
        if (loginInfo == null) {
            if21Dto.setResultCode(1);
            if21Dto.setErrorCode("100000");
            if21Dto.setErrorMessage("本サービスはご利用頂けません。");
        } else {

            /**▼提携先ユーザIDを取得▼**/
            // 提携先ユーザIDを初期化
            String partnerCompUserID = "";
            // 提携先クーポンIDを取得
            String partnerCompuserId = if22Dto.getPartnerCouponID();
            if (partnerCompuserId.length() >= loginInfo.getPartnerUserIdEnd()) {
                partnerCompUserID = partnerCompuserId.substring(loginInfo.getPartnerUserIdStrat(),
                        loginInfo.getPartnerUserIdEnd());
            } else {
                log.error("提携先クーポンIDレングスが提携先ユーザID終了位置より小さい");
                throw new HttpStatus400Exception();
            }
            /**▲提携先ユーザIDを取得▲**/

            // クーポン情報を取得
            CouponInfoEntity couponInfo = couponInfoService
                    .findOne(partnerCompUserID);

            // クーポンユーザ情報の検索結果、0の場合
            if (couponInfo == null) {
                if21Dto.setResultCode(1);
                if21Dto.setErrorCode("100001");
                if21Dto.setErrorMessage("利用できないクーポンです。");
            } else {
                // 利用確認処理制御フラグを判定
                // 通信408
                if (STS_408.equals(loginInfo.getConfirmControl())) {
                    throw new HttpStatus408Exception();
                    // 通信504
                } else if (STS_504.equals(loginInfo.getConfirmControl())) {
                    throw new HttpStatus504Exception();
                    // 異常
                } else if (FAULT.equals(loginInfo.getConfirmControl())) {
                    if21Dto.setResultCode(1);
                    if21Dto.setErrorCode("100002");
                    if21Dto.setErrorMessage("只今、クーポンサービスをご利用頂けません。");
                    // 通信エラー400
                } else if (STS_400.equals(loginInfo.getConfirmControl())) {
                    throw new HttpStatus400Exception();
                } else {


                    if ((couponInfo.getBalance() <= 0) && (!couponInfo.getIncentiveOfferType().equals("2")) ) {
                        if21Dto.setResultCode(1);
                        if21Dto.setErrorCode("100003");
                        if21Dto.setErrorMessage("当該クーポンは残高ない為、ご利用頂けません。");
                    } else if ((couponInfo.getIncentiveOfferType().equals("2")) && (couponInfo.getCouponRemainUseNum().equals(0))) {
                        if21Dto.setResultCode(1);
                        if21Dto.setErrorCode("100004");
                        if21Dto.setErrorMessage("本クーポンは利用済みです。<br>ご利用頂けません。");
                    } else {
                        /**▼クーポン情報により応答データ部を設定▼**/
                        TeleIF21DataDto if21Data = new TeleIF21DataDto();
                        if21Data.setCampaignID(couponInfo.getCampaignId());
                        if21Data.setCampaignName(couponInfo.getCampaignName());
                        if21Data.setCampaignComment(couponInfo.getCampaignComment());
                        if21Data.setIncentiveOfferType(couponInfo.getIncentiveOfferType());
                        if21Data.setKingkExistFlg(couponInfo.getKingkExistFlg());
                        /**▲クーポン情報により応答データ部を設定▲**/

                        // 応答電文項目最低金額の設定
                        // 画面金額入力なしの場合
                        if (KINGK_EXIST_FLG_OFF.equals(couponInfo.getKingkExistFlg())) {
                            if21Data.setTargetKingkMin("0");
                            // 画面金額入力ありの場合
                        } else {
                            // 応答電文項目最低金額の設定
                            if21Data.setTargetKingkMin(String.valueOf(couponInfo.getDiscountKingk().longValue()));
//                            if (couponInfo.getDiscountKingk() <= Double.valueOf(couponInfo.getTargetKingkMin())) {
//                                if21Data.setTargetKingkMin(String.valueOf(couponInfo.getBalance()));
//                            } else {
//                                if21Data.setTargetKingkMin(couponInfo.getTargetKingkMin());
//                            }
                        }

                        // 応答電文項目提携先ユーザIDの設定
                        if21Data.setPartnerCompUserID(partnerCompUserID);
                        if21Dto.setData(if21Data);
                        if21Dto.setResultCode(0);
                    }
                }
            }
        }
        // 応答電文項目.処理日時を設定
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if21Dto.setTime(time);

        // 応答電文をJSON文字列に変換
        responseStr = CommonUtil.convertJson(if21Dto);

        log.info("利用確認応答電文：" + responseStr);
        log.info("利用確認処理を終了");
        // 応答電文返却
        return responseStr;
    }

    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    private class HttpStatus408Exception extends RuntimeException {
        private static final long serialVersionUID = -3925111947236719052L;
    }

    @ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
    private class HttpStatus504Exception extends RuntimeException {
        private static final long serialVersionUID = 8994393036609424263L;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private class HttpStatus400Exception extends RuntimeException {
        private static final long serialVersionUID = -5654258441098267002L;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private class HttpStatus500Exception extends RuntimeException {
        private static final long serialVersionUID = -733553029881639728L;
    }
}
