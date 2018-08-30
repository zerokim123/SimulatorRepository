/*
 * ファイル名    TeleIF39DataDto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/20
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/20   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if40;

import java.io.Serializable;

/**
 * ログイン情報応答DTO(Data部).
 *
 * @author NJK
 * @version 1.0 2018/03/20
 */
public class TeleIF39DataDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -6134731966154265362L;

    /**
     * 事業者名.
     */
    private String companyName = "";

    /**
     * 館名.
     */
    private String buildingName = "";

    /**
     * 店舗名.
     */
    private String storeName = "";

    /**
     * 事業者名の取得.
     *
     * @return companyName 事業者名
     */
    public final String getCompanyName() {
        return companyName;
    }

    /**
     * 事業者名の設定
     *
     * @param companyName
     *            事業者名
     */
    public final void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    /**
     * 館名の取得.
     *
     * @return buildingName 館名
     */
    public final String getBuildingName() {
        return buildingName;
    }

    /**
     * 館名の設定
     *
     * @param buildingName
     *            館名
     */
    public final void setBuildingName(final String buildingName) {
        this.buildingName = buildingName;
    }

    /**
     * 店舗名の取得.
     *
     * @return storeName 店舗名
     */
    public final String getStoreName() {
        return storeName;
    }

    /**
     * 店舗名の設定
     *
     * @param storeName
     *            店舗名
     */
    public final void setStoreName(final String storeName) {
        this.storeName = storeName;
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
        builder.append("companyName=");
        builder.append(getCompanyName());
        builder.append(",buildingName=");
        builder.append(getBuildingName());
        builder.append(",storeName=");
        builder.append(getStoreName());
        builder.append("]");
        return builder.toString();
    }
}
