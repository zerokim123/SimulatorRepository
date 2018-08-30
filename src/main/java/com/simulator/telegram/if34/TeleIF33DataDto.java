/*
 * ファイル名    TeleIF33DataDto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/30
 * 作成者        NJK 姜春雷
 * 履歴情報
 *     2018/03/30   NJK 姜春雷   初版作成
 */
package com.simulator.telegram.if34;

import java.io.Serializable;

/**
 * IF33_クーポン利用取消情報応答DTO(Data部).
 *
 * @author NJK
 * @version 1.0 2018/03/30
 */
public class TeleIF33DataDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 6876409587034598923L;

    /**
     * クーポントランザクションID(取消).
     */
    private String transactionID = "";

    /**
     * キャンペーン利用取消日時.
     */
    private String campaignUseDatetime = "";

    /**
     * クーポントランザクションID(取消)の取得.
     *
     * @return transactionID クーポントランザクションID(取消)
     */
    public final String getTransactionID() {
        return transactionID;
    }

    /**
     * クーポントランザクションID(取消)の設定
     *
     * @param transactionID
     *            クーポントランザクションID(取消)
     */
    public final void setTransactionID(final String transactionID) {
        this.transactionID = transactionID;
    }

    /**
     * キャンペーン利用取消日時の取得.
     *
     * @return campaignUseDatetime キャンペーン利用取消日時
     */
    public final String getCampaignUseDatetime() {
        return campaignUseDatetime;
    }

    /**
     * キャンペーン利用取消日時の設定
     *
     * @param campaignUseDatetime
     *            キャンペーン利用取消日時
     */
    public final void setCampaignUseDatetime(final String campaignUseDatetime) {
        this.campaignUseDatetime = campaignUseDatetime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append("transactionID=");
        builder.append(getTransactionID());
        builder.append(",campaignUseDatetime=");
        builder.append(getCampaignUseDatetime());
        builder.append("]");
        return builder.toString();
    }
}
