/*
 * ファイル名    TeleIF21DataDto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/20
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/20   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if22;

import java.io.Serializable;

/**
 * IF21_クーポン利用確認情報応答DTO(Data部).
 *
 * @author NJK
 * @version 1.0 2018/03/20
 */
public class TeleIF21DataDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 7264749228299698705L;

    /**
     * キャンペーンID.
     */
    private String campaignID = "";

    /**
     * キャンペーン名称.
     */
    private String campaignName = "";

    /**
     * キャンペーン説明文.
     */
    private String campaignComment = "";

    /**
     * インセンティブ提供方法.
     */
    private String incentiveOfferType = "";

    /**
     * 金額入力設定有無.
     */
    private String kingkExistFlg = "";

    /**
     * 最低金額.
     */
    private String targetKingkMin = "";

    /**
     * 提携先ユーザID.
     */
    private String partnerCompUserID = "";

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
    public final String getCampaignComment() {
        return campaignComment;
    }

    /**
     * キャンペーン説明文の設定
     *
     * @param campaignComment
     *            キャンペーン説明文
     */
    public final void setCampaignComment(final String campaignComment) {
        this.campaignComment = campaignComment;
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
     * 金額入力設定有無の取得.
     *
     * @return kingkExistFlg 金額入力設定有無
     */
    public final String getKingkExistFlg() {
        return kingkExistFlg;
    }

    /**
     * 金額入力設定有無の設定
     *
     * @param kingkExistFlg
     *            金額入力設定有無
     */
    public final void setKingkExistFlg(final String kingkExistFlg) {
        this.kingkExistFlg = kingkExistFlg;
    }

    /**
     * 最低金額の取得.
     *
     * @return targetKingkMin 最低金額
     */
    public final String getTargetKingkMin() {
        return targetKingkMin;
    }

    /**
     * 最低金額の設定
     *
     * @param targetKingkMin
     *            最低金額
     */
    public final void setTargetKingkMin(final String targetKingkMin) {
        this.targetKingkMin = targetKingkMin;
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
     * オブジェクトの文字列表現を返す.
     *
     * @return オブジェクトの文字列表現
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append("campaignID=");
        builder.append(getCampaignID());
        builder.append(",campaignName=");
        builder.append(getCampaignName());
        builder.append(",campaignComment=");
        builder.append(getCampaignComment());
        builder.append(",incentiveOfferType=");
        builder.append(getIncentiveOfferType());
        builder.append(",kingkExistFlg=");
        builder.append(getKingkExistFlg());
        builder.append(",targetKingkMin=");
        builder.append(getTargetKingkMin());
        builder.append("]");
        return builder.toString();
    }
}
