package com.simulator.dataservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "COUPON_INFO")
@Getter
@Setter
@ToString
public class CouponInfoEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6763566076304771463L;

    @Id
    @Column
    private String partnerCompUserId;

    @Column
    private String campaignId;

    @Column
    private String campaignName;

    @Column
    private String campaignComment;

    @Column
    private String incentiveOfferType;

    @Column
    private String kingkExistFlg;

    @Column
    private String targetKingkMin;

    @Column
    private String registCancelDivision;

    @Column
    private Double balance;

    @Column
    private Integer couponRemainUseNum;

    @Column
    private Double discountKingk;
}
