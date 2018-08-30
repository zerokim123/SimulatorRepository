package com.simulator.dataservice.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "LOGIN_MNGMT")
@Getter
@Setter
@ToString
public class LoginMngmtEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8844591509296624822L;

    /** 端末ログインID. */
    @Id
    @Column
    private String terminalLoginId;

    /** 事業者名. */
    @Column
    private String companyName;

    /** 館名. */
    @Column
    private String buildingName;

    /** 店舗名. */
    @Column
    private String storeName;

    /** ログインパスワード. */
    @Column
    private String loginPass;

    /** 有効期限. */
    @Column
    private Timestamp expDate;

    /** 提携先ユーザID開始位置. */
    @Column
    private Integer partnerUserIdStrat;

    /** 提携先ユーザID終了位置. */
    @Column
    private Integer partnerUserIdEnd;

    /** ログイン制御. */
    @Column
    private String loginControl;

    /** パスワード更新制御. */
    @Column
    private String updateControl;

    /** 利用確認制御. */
    @Column
    private String confirmControl;

    /** クーポン消込制御_消込なし. */
    @Column
    private String useOffControl;

    /** クーポン消込制御_消込あり. */
    @Column
    private String useOnControl;

    /** 取引一覧照会制御. */
    @Column
    private String rusultControl;

    /** 取消制御. */
    @Column
    private String cancelControl;

    /** 詳細照会制御. */
    @Column
    private String detailControl;

}
