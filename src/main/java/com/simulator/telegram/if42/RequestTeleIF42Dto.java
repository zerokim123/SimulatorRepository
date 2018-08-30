/*
 * ファイル名    RequestTeleIF42Dto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/20
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/20   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if42;

import com.simulator.telegram.RequestTeleHeaderDto;

/**
 * IF42_パスワード変更DTO.
 *
 * @author NJK
 * @version 1.0 2018/03/20
 */
public class RequestTeleIF42Dto extends RequestTeleHeaderDto {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -4870061960321478537L;

    /**
     * 変更前パスワード.
     */
    private String password = "";

    /**
     * 変更後パスワード.
     */
    private String newPassword = null;

    /**
     * 変更前パスワードの取得.
     *
     * @return password 変更前パスワード
     */
    public final String getPassword() {
        return password;
    }

    /**
     * 変更前パスワードの設定
     *
     * @param password
     *            変更前パスワード
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * 変更後パスワードの取得.
     *
     * @return newPassword 変更後パスワード
     */
    public final String getNewPassword() {
        return newPassword;
    }

    /**
     * 変更後パスワードの設定
     *
     * @param newPassword
     *            変更後パスワード
     */
    public final void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
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
        builder.append("]");
        return builder.toString();
    }
}
