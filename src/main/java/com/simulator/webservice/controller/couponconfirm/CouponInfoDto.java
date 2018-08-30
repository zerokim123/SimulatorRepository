package com.simulator.webservice.controller.couponconfirm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponInfoDto {

    private String partnerCompUserId;

    private String campaignId;

    private String campaignName;

    private String campaignComment;

    private String incentiveOfferType;

    private String kingkExistFlg;

    private String targetKingkMin;

    private String registCancelDivision;

    private Double balance;

    private Integer couponRemainUseNum;

    private Double discountKingk;

}
