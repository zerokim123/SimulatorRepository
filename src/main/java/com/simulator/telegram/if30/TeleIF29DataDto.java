/*
 * ファイル名    TeleIF29DataDto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/20
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/20   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if30;

import java.io.Serializable;

/**
 * IF29_クーポン消込情報応答DTO(Data部).
 *
 * @author NJK
 * @version 1.0 2018/03/20
 */
public class TeleIF29DataDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 6176658897921372092L;

    /**
     * トランザクションID.
     */
    private String transactionID = "";

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
     * クーポン残利用回数.
     */
    private String couponRemainUseNum = "";

    /**
     * トランザクションIDの取得.
     *
     * @return transactionID トランザクションID
     */
    public final String getTransactionID() {
        return transactionID;
    }

    /**
     * トランザクションIDの設定
     *
     * @param transactionID
     *            トランザクションID
     */
    public final void setTransactionID(final String transactionID) {
        this.transactionID = transactionID;
    }

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
     * @return afterDiscountKingk 優待後金額
     */
    public final Double getAfterDiscountKingk() {
        return afterDiscountKingk;
    }

    /**
     * 優待後金額の設定
     *
     * @param afterDiscountKingk
     *            優待後金額
     */
    public final void setAfterDiscountKingk(final Double afterDiscountKingk) {
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
     * オブジェクトの文字列表現を返す.
     *
     * @return オブジェクトの文字列表現
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append("transactionID=");
        builder.append(getTransactionID());
        builder.append(",campaignUseDatetime=");
        builder.append(getCampaignUseDatetime());
        builder.append(",transactionKingk=");
        builder.append(getTransactionKingk());
        builder.append(",afterDiscountKingk=");
        builder.append(getAfterDiscountKingk());
        builder.append(",discountKingk=");
        builder.append(getDiscountKingk());
        builder.append(",couponRemainUseNum=");
        builder.append(getCouponRemainUseNum());
        builder.append("]");
        return builder.toString();
    }
}
