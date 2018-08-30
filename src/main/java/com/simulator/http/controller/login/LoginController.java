package com.simulator.http.controller.login;

import java.sql.Timestamp;
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
import com.simulator.common.RSAEncryptor;
import com.simulator.dataservice.Service.LoginMngmtService;
import com.simulator.dataservice.entity.LoginMngmtEntity;
import com.simulator.telegram.if40.RequestTeleIF40Dto;
import com.simulator.telegram.if40.ResponseTeleIF39Dto;
import com.simulator.telegram.if40.TeleIF39DataDto;

@RestController
@RequestMapping()
@Validated
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    @Autowired
    private LoginMngmtService loginMngmtService;

    /**
     * 初期パスワード.
     * */
    private static String LOGIN_STS_INIT = "1";

    /**
     * 通信エラー408.
     * */
    private static String LOGIN_STS_408 = "2";

    /**
     * 通信エラー504.
     * */
    private static String LOGIN_STS_504 = "3";

    /**
     * 通信エラー400.
     * */
    private static String LOGIN_STS_400 = "4";

    /**
     * ログイン異常.
     * */
    private static String LOGIN_STS_5 = "5";

    /**
     *取消異常.
     * */
    private static String CANCEL_STS_6 = "6";

    /**
     *取消通信エラー408.
     * */
    private static String CANCEL_STS_408 = "7";

    /**
     *取消通信エラー504.
     * */
    private static String CANCEL_STS_504 = "8";

    /**
     *取消通信エラー400.
     * */
    private static String CANCEL_STS_400 = "9";


    @RequestMapping(value = CommonConst.LOGIN, method = {
            RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String runLogin(@Validated @RequestBody String strBody) {

        log.info("ログイン処理を開始");
        log.info("ログイン要求電文：" + strBody);

        // 初期化
        ResponseTeleIF39Dto if39Dto = new ResponseTeleIF39Dto();
        TeleIF39DataDto data = new TeleIF39DataDto();
        String responseStr = "";

        // 受信電文をParse
        RequestTeleIF40Dto if40Dto = CommonUtil.jsonConvertObject(strBody, RequestTeleIF40Dto.class);

        // 受信電文Parse結果NULLの場合、ステータスコード400を返却
        if (if40Dto == null) {
            throw new HttpStatus400Exception();
        }

        // ログインユーザ情報を取得する。
        LoginMngmtEntity loginInfo = loginMngmtService.findByPk(if40Dto.getTerminalLoginId());

        // ログインユーザIDの検索結果、0の場合
        if (loginInfo == null) {
            if39Dto.setResultCode(1);
            if39Dto.setErrorCode("000001");
            if39Dto.setErrorMessage("ご利用出来ないログインIDです。\nもう一度ご確認お願いします。");
        } else {

            // ユーザ管理ステータスを取得
            String logSts = loginInfo.getLoginControl();

            // 通信エラー408に設定した場合
            if (LOGIN_STS_408.equals(logSts) || CANCEL_STS_408.equals(logSts)) {
                log.info("ログイン処理を終了しました。");
                throw new HttpStatus408Exception();
                // 通信エラー504に設定した場合
            } else if (LOGIN_STS_504.equals(logSts) || CANCEL_STS_504.equals(logSts)) {
                log.info("ログイン処理を終了しました。");
                throw new HttpStatus504Exception();
                // 通信エラー400に設定した場合
            } else if (LOGIN_STS_400.equals(logSts) || CANCEL_STS_400.equals(logSts)) {
                log.info("ログイン処理を終了しました。");
                throw new HttpStatus400Exception();

            } else if (LOGIN_STS_5.equals(logSts) || CANCEL_STS_6.equals(logSts)) {
                // ログインパスワードが誤った場合
                if39Dto.setResultCode(1);
                if39Dto.setErrorCode("000006");
                if39Dto.setErrorMessage("ログイン処理出来ません。");
            }else {
                // 受信パスワードを設定
                //                String password = if40Dto.getPassword();
                /** ▼受信パスワードの復号化 ▼**/
                String password = RSAEncryptor.decrypt(if40Dto.getPassword());
                /** ▲受信パスワードの復号化 ▲**/

                // ログインパスワードが正しい場合
                if (loginInfo.getLoginPass().equals(password)) {

                    // ログイン状態を判定：初期パスワード
                    if (LOGIN_STS_INIT.equals(logSts)) {
                        data.setStoreName(loginInfo.getStoreName());
                        data.setBuildingName(loginInfo.getBuildingName());
                        data.setCompanyName(loginInfo.getCompanyName());
                        if39Dto.setData(data);
                        if39Dto.setResultCode(2);
                        if39Dto.setErrorCode("000002");
                        if39Dto.setErrorMessage("初期パスワードの為、パスワードを変更してください。");
                    } else {
                        Timestamp requestTime = null;
                        try {
                            // 要求電文項目.リクエスト日時をデータ型変換
                            requestTime = new Timestamp(
                                    new SimpleDateFormat("yyyyMMddHHmmss").parse(if40Dto.getRequestTime()).getTime());
                        } catch (ParseException e) {
                            log.fatal("要求電文項目.リクエスト日時のフォーマット変換エラー", e);
                            log.info("ログイン処理を終了しました。");
                            throw new HttpStatus400Exception();
                        }
                        // 有効期限以内の場合
                        if (requestTime.getTime() <= loginInfo.getExpDate().getTime()) {
                            if39Dto.setResultCode(0);
                            data.setStoreName(loginInfo.getStoreName());
                            data.setBuildingName(loginInfo.getBuildingName());
                            data.setCompanyName(loginInfo.getCompanyName());
                            if39Dto.setData(data);
                            // 有効期限切れの場合
                        } else {
                            if39Dto.setResultCode(3);
                            if39Dto.setErrorCode("000003");
                            if39Dto.setErrorMessage("パスワード有効期限切れ");
                        }
                    }
                } else {
                    // ログインパスワードが誤った場合
                    if39Dto.setResultCode(1);
                    if39Dto.setErrorCode("000004");
                    if39Dto.setErrorMessage("パスワードが間違いました。");
                    log.info("パスワードチェックエラー[ログインパスワード=" + loginInfo.getLoginPass() + ",受信パスワード=" + password + "]");
                }
            }
        }

        // 応答電文項目.処理日時を設定
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if39Dto.setTime(time);

        // 応答電文をJSON文字列に変換
        responseStr = CommonUtil.convertJson(if39Dto);

        log.info("ログイン処理応答電文：" + responseStr);
        log.info("ログイン処理を終了");

        // 応答電文返却
        return responseStr;
    }

    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    private class HttpStatus408Exception extends RuntimeException {
        private static final long serialVersionUID = 8670697701903346390L;
    }

    @ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
    private class HttpStatus504Exception extends RuntimeException {
        private static final long serialVersionUID = 1773895407682497289L;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private class HttpStatus400Exception extends RuntimeException {
        private static final long serialVersionUID = 958978001184042262L;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private class HttpStatus500Exception extends RuntimeException {
        private static final long serialVersionUID = 8246902392076871875L;
    }
}
