﻿/*
 * ファイル名    RequestTeleIF40Dto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/20
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/20   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if40;

import com.simulator.telegram.RequestTeleHeaderDto;

/**
 * ログイン情報DTO.
 *
 * @author NJK
 * @version 1.0 2018/03/20
 */
public class RequestTeleIF40Dto extends RequestTeleHeaderDto {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -2697433593180500407L;
    /**
     * パスワード.
     */
    private String password = "";

    /**
     * パスワードの取得.
     *
     * @return password パスワード
     */
    public final String getPassword() {
        return password;
    }

    /**
     * パスワードの設定
     *
     * @param password
     *            パスワード
     */
    public final void setPassword(final String password) {
        this.password = password;
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
