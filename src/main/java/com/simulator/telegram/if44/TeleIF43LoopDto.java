/*
 * ファイル名    ResponseTeleIF43Dto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/16
 * 作成者        NJK 呂世紀
 * 履歴情報
 *     2018/03/16   NJK 呂世紀   初版作成
 */
package com.simulator.telegram.if44;

import java.io.Serializable;

/**
 * IF43_取引一覧照会応答DTO(繰り返し).
 *
 * @author NJK
 * @version 1.0 2018/03/16
 */
public class TeleIF43LoopDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 8443188477408749066L;

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
     * 優待金額.
     */
    private Double discountKingk = null;

    /**
     * 優待後金額.
     */
    private Double afterDiscountKingk = null;

    /**
     * インセンティブ提供方法.
     */
    private String incentiveOfferType = "";

    /**
     * 取引区分.
     */
    private String registCancelDivision = "";

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
        builder.append(",discountKingk=");
        builder.append(getDiscountKingk());
        builder.append(",afterDiscountKingk=");
        builder.append(getAfterDiscountKingk());
        builder.append(",incentiveOfferType=");
        builder.append(getIncentiveOfferType());
        builder.append(",registCancelDivision=");
        builder.append(getRegistCancelDivision());
        builder.append("]");
        return builder.toString();
    }
}
