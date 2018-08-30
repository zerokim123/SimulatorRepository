/*
 * ファイル名    RequestTeleIF30Dto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/20
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/20   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if30;

import com.simulator.telegram.RequestTeleHeaderDto;

/**
 * IF30_クーポン消込情報DTO.
 *
 * @author NJK
 * @version 1.0 2018/03/20
 */
public class RequestTeleIF30Dto extends RequestTeleHeaderDto {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 344361633355620880L;

    /**
     * 提携先クーポンID.
     */
    private String partnerCouponID = "";

    /**
     * キャンペーンID.
     */
    private String campaignID = "";

    /**
     * 取引金額.
     */
    private String transactionKingk = null;

    /**
     * 提携先ユーザID.
     */
    private String partnerCompUserID = "";

    /**
     * クーポン消込フラグ.
     */
    private String couponUsedFlg = "";

    /**
     * 提携先クーポンIDの取得.
     *
     * @return partnerCouponID 提携先クーポンID
     */
    public final String getPartnerCouponID() {
        return partnerCouponID;
    }

    /**
     * 提携先クーポンIDの設定
     *
     * @param partnerCouponID
     *            提携先クーポンID
     */
    public final void setPartnerCouponID(final String partnerCouponID) {
        this.partnerCouponID = partnerCouponID;
    }

    /**
     * キャンペーンIDの取得.
     *
     * @return campaignID キャンペーンID
     */
    public final String getCampaignID() {
        return campaignID;
    }

    /**
     * キャンペーンIDの設定
     *
     * @param campaignID
     *            キャンペーンID
     */
    public final void setCampaignID(final String campaignID) {
        this.campaignID = campaignID;
    }

    /**
     * 取引金額の取得.
     *
     * @return transactionKingk 取引金額
     */
    public final String getTransactionKingk() {
        return transactionKingk;
    }

    /**
     * 取引金額の設定
     *
     * @param transactionKingk
     *            取引金額
     */
    public final void setTransactionKingk(final String transactionKingk) {
        this.transactionKingk = transactionKingk;
    }

    /**
     * 提携先ユーザIDの取得.
     *
     * @return partnerCompUserID 提携先ユーザID
     */
    public final String getPartnerCompUserID() {
        return partnerCompUserID;
    }

    /**
     * 提携先ユーザIDの設定
     *
     * @param partnerCompUserID
     *            提携先ユーザID
     */
    public final void setPartnerCompUserID(final String partnerCompUserID) {
        this.partnerCompUserID = partnerCompUserID;
    }

    /**
     * クーポン消込フラグの取得.
     *
     * @return couponUsedFlg クーポン消込フラグ
     */
    public final String getCouponUsedFlg() {
        return couponUsedFlg;
    }

    /**
     * クーポン消込フラグの設定
     *
     * @param couponUsedFlg
     *            クーポン消込フラグ
     */
    public final void setCouponUsedFlg(final String couponUsedFlg) {
        this.couponUsedFlg = couponUsedFlg;
    }

    /**
     * オブジェクトの文字列表現を返す.
     *
     * @return オブジェクトの文字列表現
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append("identityInfoText=");
        builder.append(getIdentityInfoText());
        builder.append(",requestTime=");
        builder.append(getRequestTime());
        builder.append(",tid=");
        builder.append(getTid());
        builder.append(",makerCd=");
        builder.append(getMakerCd());
        builder.append(",osVersion=");
        builder.append(getOsVersion());
        builder.append(",appCd=");
        builder.append(getAppCd());
        builder.append(",app=");
        builder.append(getApp());
        builder.append(",campaignID=");
        builder.append(getCampaignID());
        builder.append(",transactionKingk=");
        builder.append(getTransactionKingk());
        builder.append(",couponUsedFlg=");
        builder.append(getCouponUsedFlg());
        builder.append("]");
        return builder.toString();
    }
}
