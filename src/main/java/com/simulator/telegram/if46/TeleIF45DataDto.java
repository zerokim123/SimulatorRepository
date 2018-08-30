/*
 * ファイル名    TeleIF45DataDto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/20
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/20   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if46;

import java.io.Serializable;

/**
 * IF45_取引詳細照会応答DTO(Data部).
 *
 * @author NJK
 * @version 1.0 2018/03/20
 */
public class TeleIF45DataDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 4271004050945935327L;

    /**
     * キャンペーン利用日時.
     */
    private String campaignUseDatetime = "";

    /**
     * 取引金額.
     */
    private Double transactionKingk = null;

    /**
     * 優待後金額.
     */
    private Double afterDiscountKingk = null;

    /**
     * 優待金額.
     */
    private Double discountKingk = null;

    /**
     * 提携先ユーザID.
     */
    private String partnerCompUserID = "";

    /**
     * キャンペーン名称.
     */
    private String campaignName = "";

    /**
     * キャンペーン説明文.
     */
    private String campaigncomment = "";

    /**
     * インセンティブ提供方法.
     */
    private String incentiveOfferType = "";

    /**
     * 取引区分.
     */
    private String registCancelDivision = "";

    /**
     * ステータス.
     */
    private String status = "";

    /**
     * クーポン残利用回数.
     */
    private String couponRemainUseNum = "";

    /**
     * 取消元トランザクションID.
     */
    private String baseTransactionID = "";

    /**
     * キャンペーン利用日時の取得.
     *
     * @return campaignUseDatetime キャンペーン利用日時
     */
    public final String getCampaignUseDatetime() {
        return campaignUseDatetime;
    }

    /**
     * キャンペーン利用日時の設定
     *
     * @param campaignUseDatetime
     *            キャンペーン利用日時
     */
    public final void setCampaignUseDatetime(final String campaignUseDatetime) {
        this.campaignUseDatetime = campaignUseDatetime;
    }

    /**
     * 取引金額の取得.
     *
     * @return transactionKingk 取引金額
     */
    public final Double getTransactionKingk() {
        return transactionKingk;
    }

    /**
     * 取引金額の設定
     *
     * @param transactionKingk
     *            取引金額
     */
    public final void setTransactionKingk(final Double transactionKingk) {
        this.transactionKingk = transactionKingk;
    }

    /**
     * 優待後金額の取得.
     *
     * @return afterDiscount_Kingk 優待後金額
     */
    public final Double getAfterDiscountKingk() {
        return afterDiscountKingk;
    }

    /**
     * 優待後金額の設定
     *
     * @param afterDiscount_Kingk
     *            優待後金額
     */
    public final void setAfterDiscountKingk(
            final Double afterDiscountKingk) {
        this.afterDiscountKingk = afterDiscountKingk;
    }

    /**
     * 優待金額の取得.
     *
     * @return discountKingk 優待金額
     */
    public final Double getDiscountKingk() {
        return discountKingk;
    }

    /**
     * 優待金額の設定
     *
     * @param discountKingk
     *            優待金額
     */
    public final void setDiscountKingk(final Double discountKingk) {
        this.discountKingk = discountKingk;
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
     * キャンペーン名称の取得.
     *
     * @return campaignName キャンペーン名称
     */
    public final String getCampaignName() {
        return campaignName;
    }

    /**
     * キャンペーン名称の設定
     *
     * @param campaignName
     *            キャンペーン名称
     */
    public final void setCampaignName(final String campaignName) {
        this.campaignName = campaignName;
    }

    /**
     * キャンペーン説明文の取得.
     *
     * @return campaignComment キャンペーン説明文
     */
    public final String getCampaigncomment() {
        return campaigncomment;
    }

    /**
     * キャンペーン説明文の設定
     *
     * @param campaignComment
     *            キャンペーン説明文
     */
    public final void setCampaigncomment(final String campaigncomment) {
        this.campaigncomment = campaigncomment;
    }

    /**
     * インセンティブ提供方法の取得.
     *
     * @return incentiveOfferType インセンティブ提供方法
     */
    public final String getIncentiveOfferType() {
        return incentiveOfferType;
    }

    /**
     * インセンティブ提供方法の設定
     *
     * @param incentiveOfferType
     *            インセンティブ提供方法
     */
    public final void setIncentiveOfferType(final String incentiveOfferType) {
        this.incentiveOfferType = incentiveOfferType;
    }

    /**
     * 取引区分の取得.
     *
     * @return registCancelDivision 取引区分
     */
    public final String getRegistCancelDivision() {
        return registCancelDivision;
    }

    /**
     * 取引区分の設定
     *
     * @param registCancelDivision
     *            取引区分
     */
    public final void setRegistCancelDivision(
            final String registCancelDivision) {
        this.registCancelDivision = registCancelDivision;
    }

    /**
     * ステータスの取得.
     *
     * @return status ステータス
     */
    public final String getStatus() {
        return status;
    }

    /**
     * ステータスの設定
     *
     * @param status
     *            ステータス
     */
    public final void setStatus(final String status) {
        this.status = status;
    }

    /**
     * クーポン残利用回数の取得.
     *
     * @return couponRemainUseNum クーポン残利用回数
     */
    public final String getCouponRemainUseNum() {
        return couponRemainUseNum;
    }

    /**
     * クーポン残利用回数の設定
     *
     * @param couponRemainUseNum
     *            クーポン残利用回数
     */
    public final void setCouponRemainUseNum(final String couponRemainUseNum) {
        this.couponRemainUseNum = couponRemainUseNum;
    }

    /**
     * 取消元トランザクションIDの取得.
     *
     * @return baseTransactionID 取消元トランザクションID
     */
    public final String getBaseTransactionID() {
        return baseTransactionID;
    }

    /**
     * 取消元トランザクションIDの設定
     *
     * @param baseTransactionID
     *            取消元トランザクションID
     */
    public final void setBaseTransactionID(final String baseTransactionID) {
        this.baseTransactionID = baseTransactionID;
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
        builder.append("campaignUseDatetime=");
        builder.append(getCampaignUseDatetime());
        builder.append(",transactionKingk=");
        builder.append(getTransactionKingk());
        builder.append(",afterDiscount_Kingk=");
        builder.append(getAfterDiscountKingk());
        builder.append(",discountKingk=");
        builder.append(getDiscountKingk());
        builder.append(",campaignName=");
        builder.append(getCampaignName());
        builder.append(",campaignComment=");
        builder.append(getCampaigncomment());
        builder.append(",incentiveOfferType=");
        builder.append(getIncentiveOfferType());
        builder.append(",registCancelDivision=");
        builder.append(getRegistCancelDivision());
        builder.append(",status=");
        builder.append(getStatus());
        builder.append(",couponRemainUseNum=");
        builder.append(getCouponRemainUseNum());
        builder.append(",baseTransactionID=");
        builder.append(getBaseTransactionID());
        builder.append("]");
        return builder.toString();
    }
}
