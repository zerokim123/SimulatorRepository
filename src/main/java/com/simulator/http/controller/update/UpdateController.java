package com.simulator.http.controller.update;

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
import com.simulator.telegram.if42.RequestTeleIF42Dto;
import com.simulator.telegram.if42.ResponseTeleIF41Dto;

@RestController
@RequestMapping()
@Validated
public class UpdateController {

    private static final Logger log = Logger.getLogger(UpdateController.class);

    @Autowired
    private LoginMngmtService loginMngmtService;

    /** パスワード更新制御フラグ：1異常 */
    private static String FAULT = "1";

    /** パスワード更新制御フラグ：通信エラー408 */
    private static String STS_408 = "2";

    /** パスワード更新制御フラグ：通信エラー504 */
    private static String STS_504 = "3";

    /** パスワード更新制御フラグ：通信エラー504 */
    private static String STS_400 = "4";

    @RequestMapping(value = CommonConst.UPDATE, method = {
            RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String runUpdate(@Validated @RequestBody String strBody) {

        log.info("パスワード変更処理を開始");
        log.info("パスワード変更要求電文：" + strBody);
        // 初期化
        ResponseTeleIF41Dto if41Dto = new ResponseTeleIF41Dto();
        String responseStr = "";

        // 受信電文をParse
        RequestTeleIF42Dto if42Dto = CommonUtil.jsonConvertObject(strBody, RequestTeleIF42Dto.class);

        // 受信電文Parse結果NULLの場合、ステータスコード400を返却
        if (if42Dto == null) {
            throw new HttpStatus400Exception();
        }

        // ログインユーザ情報を取得する。
        LoginMngmtEntity loginInfo = loginMngmtService.findByPk(if42Dto.getTerminalLoginId());

        // ログインユーザIDの検索結果、0の場合
        if (loginInfo == null) {
            if41Dto.setResultCode(1);
            if41Dto.setErrorCode("300001");
            if41Dto.setErrorMessage("ご利用出来ないログインIDです。\nもう一度ご確認お願いします。");
        } else {
            // パスワード更新処理制御フラグを設定
            String updateFlg = loginInfo.getUpdateControl();

            if (STS_408.equals(updateFlg)) {
                throw new HttpStatus408Exception();
            } else if (STS_504.equals(updateFlg)) {
                throw new HttpStatus504Exception();
            } else if (STS_400.equals(updateFlg)) {
                throw new HttpStatus400Exception();
            } else {

                // パスワード変更可否を判定する
                if (FAULT.equals(updateFlg)) {
                    if41Dto.setResultCode(1);
                    if41Dto.setErrorCode("300001");
                    if41Dto.setErrorMessage("本サービスはご利用頂けません。");
                } else {

                    // ログインパスワードとNEWパスワードを設定
                    String requestPassword = if42Dto.getPassword();
                    String newPassword = if42Dto.getNewPassword();
                    /** ▼受信パスワードの復号化 ▼**/
                    requestPassword = RSAEncryptor.decrypt(requestPassword);
                    newPassword = RSAEncryptor.decrypt(newPassword);
                    /** ▲受信パスワードの復号化 ▲**/

                    // ログインパスワードを設定
                    String password = loginInfo.getLoginPass();

                    // 現在のパスワードチェックOK
                    if (password.equals(requestPassword)) {
                        if (password.equals(newPassword)) {
                            if41Dto.setResultCode(1);
                            if41Dto.setErrorCode("300003");
                            if41Dto.setErrorMessage("現在のパスワードと変更パスワードが同じです。");
                        } else {
                            /** ▼パスワードを更新▼**/
                            LoginMngmtEntity updateInfo = new LoginMngmtEntity();
                            updateInfo.setTerminalLoginId(if42Dto.getTerminalLoginId());
                            updateInfo.setLoginPass(newPassword);
                            loginMngmtService.updateLoginPassByPK(updateInfo);
                            /** ▲パスワードを更新▲**/
                            // 応答電文.処理結果コード=0正常
                            if41Dto.setResultCode(0);
                        }
                        // 現在のパスワードチェックNG
                    } else {
                        if41Dto.setResultCode(1);
                        if41Dto.setErrorCode("300002");
                        if41Dto.setErrorMessage("ログインパスワードが間違いました。");
                    }
                }
            }
        }

        // 応答電文項目.処理日時を設定
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if41Dto.setTime(time);

        // 応答電文をJSON文字列に変換
        responseStr = CommonUtil.convertJson(if41Dto);

        log.info("パスワード変更応答電文：" + responseStr);
        log.info("パスワード変更処理を終了");
        // 応答電文返却
        return responseStr;
    }

    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    private class HttpStatus408Exception extends RuntimeException {
        private static final long serialVersionUID = 4622535996824077793L;
    }

    @ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
    private class HttpStatus504Exception extends RuntimeException {
        private static final long serialVersionUID = 1372311004528494096L;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private class HttpStatus400Exception extends RuntimeException {
        private static final long serialVersionUID = 2430747116965743847L;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private class HttpStatus500Exception extends RuntimeException {
        private static final long serialVersionUID = -4041777557110386908L;
    }
}
