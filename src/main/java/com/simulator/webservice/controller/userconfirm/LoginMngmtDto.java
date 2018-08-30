package com.simulator.webservice.controller.userconfirm;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginMngmtDto {
    /** 端末ログインID. */
    private String terminalLoginId;

    /** 事業者名. */
    private String companyName;

    /** 館名. */
    private String buildingName;

    /** 店舗名. */
    private String storeName;

    /** ログインパスワード. */
    private String loginPass;

    /** 有効期限. */
    private Timestamp expDate;

    /** 有効期限画面表示用. */
    private String displayExpDate;

    /** 提携先ユーザID開始位置. */
    private Integer partnerUserIdStrat;

    /** 提携先ユーザID終了位置. */
    private Integer partnerUserIdEnd;

    /** ログイン制御. */
    private String loginControl;

    /** パスワード更新制御. */
    private String updateControl;

    /** 利用確認制御. */
    private String confirmControl;

    /** クーポン消込制御_消込なし. */
    private String useOffControl;

    /** クーポン消込制御_消込あり. */
    private String useOnControl;

    /** 取引一覧照会制御. */
    private String rusultControl;

    /** 取消制御. */
    private String cancelControl;

    /** 詳細照会制御. */
    private String detailControl;
}
