/*
 * ファイル名    RequestTeleIF44Dto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/16
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/16   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if44;

import com.simulator.telegram.RequestTeleHeaderDto;

/**
 * IF44_取引一覧照会DTO.
 *
 * @author NJK
 * @version 1.0 2018/03/16
 */
public class RequestTeleIF44Dto extends RequestTeleHeaderDto {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 150923965192826648L;

    /**
     * クーポントランザクションID.
     */
    private String transactionID = "";

    /**
     * キャンペーン利用日（FROM）.
     */
    private String campaignUsedayFrom = "";

    /**
     * キャンペーン利用日（TO）.
     */
    private String campaignUsedayTo = "";

    /**
     * ページ目.
     */
    private String pageNo = "";

    /**
     * 行数.
     */
    private String pageLine = "";

    /**
     * 取引区分フラグ.
     */
    private String transaction_diff = "";

    /**
     * クーポントランザクションIDの取得.
     *
     * @return transactionID クーポントランザクションID
     */
    public final String getTransactionID() {
        return transactionID;
    }

    /**
     * クーポントランザクションIDの設定
     *
     * @param transactionID
     *            クーポントランザクションID
     */
    public final void setTransactionID(final String transactionID) {
        this.transactionID = transactionID;
    }

    /**
     * キャンペーン利用日（FROM）の取得.
     *
     * @return campaignUsedayFrom キャンペーン利用日（FROM）
     */
    public final String getCampaignUsedayFrom() {
        return campaignUsedayFrom;
    }

    /**
     * キャンペーン利用日（FROM）の設定
     *
     * @param campaignUsedayFrom
     *            キャンペーン利用日（FROM）
     */
    public final void setCampaignUsedayFrom(final String campaignUsedayFrom) {
        this.campaignUsedayFrom = campaignUsedayFrom;
    }

    /**
     * キャンペーン利用日（TO）の取得.
     *
     * @return campaignUsedayTo キャンペーン利用日（TO）
     */
    public final String getCampaignUsedayTo() {
        return campaignUsedayTo;
    }

    /**
     * キャンペーン利用日（TO）の設定
     *
     * @param campaignUsedayTo
     *            キャンペーン利用日（TO）
     */
    public final void setCampaignUsedayTo(final String campaignUsedayTo) {
        this.campaignUsedayTo = campaignUsedayTo;
    }

    /**
     * ページ目の取得.
     *
     * @return pageNo ページ目
     */
    public final String getPageNo() {
        return pageNo;
    }

    /**
     * ページ目の設定
     *
     * @param pageNo
     *            ページ目
     */
    public final void setPageNo(final String pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 行数の取得.
     *
     * @return pageLine 行数
     */
    public final String getPageLine() {
        return pageLine;
    }

    /**
     * 行数の設定
     *
     * @param pageLine
     *            行数
     */
    public final void setPageLine(final String pageLine) {
        this.pageLine = pageLine;
    }

    /**
     * 取引区分フラグの取得.
     *
     * @return transaction_diff 取引区分フラグ
     */
    public final String getTransaction_diff() {
        return transaction_diff;
    }

    /**
     * 取引区分フラグの設定
     *
     * @param transaction_diff
     *            取引区分フラグ
     */
    public final void setTransaction_diff(final String transaction_diff) {
        this.transaction_diff = transaction_diff;
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
        builder.append("transactionID=");
        builder.append(getTransactionID());
        builder.append(",campaignUsedayFrom=");
        builder.append(getCampaignUsedayFrom());
        builder.append(",campaignUsedayTo=");
        builder.append(getCampaignUsedayTo());
        builder.append(",pageNo=");
        builder.append(getPageNo());
        builder.append(",pageLine=");
        builder.append(getPageLine());
        builder.append(",transaction_diff=");
        builder.append(getTransaction_diff());
        builder.append("]");
        return builder.toString();
    }
}
