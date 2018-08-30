package com.simulator.dataservice.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TRADE_INFO")
@Getter
@Setter
@ToString
public class TradeInfoEntity implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6042495548975205308L;

    /** クーポントランザクションID. */
    @Id
    @Column
    private String transactionId;

    /** 端末ログインID. */
    @Column(name = "TERMINAL_LOGIN_ID")
    private String terminalLoinId;

    /** 提携先ユーザID. */
    @Column
    private String partnerCompUserId;

    /** キャンペーン利用日時. */
    @Column
    private Timestamp tradeTime;

    /** 取引金額. */
    @Column
    private Double transactionKingk;

    /** 優待後金額. */
    @Column
    private Double afterDiscountKingk;

    /** 優待金額. */
    @Column
    private Double discountKingk;

    /** インセンティブ提供方法. */
    @Column
    private String incentiveOfferType;

    /** キャンペーン名称. */
    @Column
    private String campaignName;

    /** キャンペーン説明文. */
    @Column
    private String campaignComment;

    /** 取引区分. */
    @Column
    private String registCancelDivision;

    /** ステータス. */
    @Column
    private String status;

    /** クーポン残利用回数. */
    @Column
    private String couponRemainUseNum;

}
