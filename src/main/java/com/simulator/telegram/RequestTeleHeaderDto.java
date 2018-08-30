/*
 * ファイル名    RequestTeleHeaderDto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/16
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/16   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram;

import java.io.Serializable;

/**
 * 集信情報の共通ヘッダDTO.
 *
 * @author NJK
 * @version 1.0 2018/03/16
 */
public class RequestTeleHeaderDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 1849798146663660130L;

    /**
     * 識別情報.
     */
    private String identityInfoText = "";

    /**
     * リクエスト日時.
     */
    private String requestTime = "";

    /**
     * 端末ログインID.
     */
    private String terminalLoginId = "";

    /**
     * TID.
     */
    private String tid = "";

    /**
     * メーカコード.
     */
    private String makerCd = "";

    /**
     * OSバージョン.
     */
    private String osVersion = "";

    /**
     * アプリ識別コード.
     */
    private String appCd = "";

    /**
     * アプリバージョン.
     */
    private String app = "";

    /**
     * 識別情報の取得.
     *
     * @return identityInfoText 識別情報
     */
    public final String getIdentityInfoText() {
        return identityInfoText;
    }

    /**
     * 識別情報の設定
     *
     * @param identityInfoText
     *            識別情報
     */
    public final void setIdentityInfoText(final String identityInfoText) {
        this.identityInfoText = identityInfoText;
    }

    /**
     * リクエスト日時の取得.
     *
     * @return requestTime リクエスト日時
     */
    public final String getRequestTime() {
        return requestTime;
    }

    /**
     * リクエスト日時の設定
     *
     * @param requestTime
     *            リクエスト日時
     */
    public final void setRequestTime(final String requestTime) {
        this.requestTime = requestTime;
    }

    /**
     * 端末ログインIDの取得.
     *
     * @return terminalLoginId 端末ログインID
     */
    public final String getTerminalLoginId() {
        return terminalLoginId;
    }

    /**
     * 端末ログインIDの設定
     *
     * @param terminalLoginId
     *            端末ログインID
     */
    public final void setTerminalLoginId(final String terminalLoginId) {
        this.terminalLoginId = terminalLoginId;
    }

    /**
     * TIDの取得.
     *
     * @return tid TID
     */
    public final String getTid() {
        return tid;
    }

    /**
     * TIDの設定
     *
     * @param tid
     *            TID
     */
    public final void setTid(final String tid) {
        this.tid = tid;
    }

    /**
     * メーカコードの取得.
     *
     * @return makerCd メーカコード
     */
    public final String getMakerCd() {
        return makerCd;
    }

    /**
     * メーカコードの設定
     *
     * @param makerCd
     *            メーカコード
     */
    public final void setMakerCd(final String makerCd) {
        this.makerCd = makerCd;
    }

    /**
     * OSバージョンの取得.
     *
     * @return osVersion OSバージョン
     */
    public final String getOsVersion() {
        return osVersion;
    }

    /**
     * OSバージョンの設定
     *
     * @param osVersion
     *            OSバージョン
     */
    public final void setOsVersion(final String osVersion) {
        this.osVersion = osVersion;
    }

    /**
     * アプリ識別コードの取得.
     *
     * @return appCd アプリ識別コード
     */
    public final String getAppCd() {
        return appCd;
    }

    /**
     * アプリ識別コードの設定
     *
     * @param appCd
     *            アプリ識別コード
     */
    public final void setAppCd(final String appCd) {
        this.appCd = appCd;
    }

    /**
     * アプリバージョンの取得.
     *
     * @return app アプリバージョン
     */
    public final String getApp() {
        return app;
    }

    /**
     * アプリバージョンの設定
     *
     * @param app
     *            アプリバージョン
     */
    public final void setApp(final String app) {
        this.app = app;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("identityInfoText:").append(getIdentityInfoText());
        builder.append(", requestTime:").append(getRequestTime());
        builder.append(", terminalLoginId:").append(getTerminalLoginId());
        builder.append(", tid:").append(getTid());
        builder.append(", makerCd:").append(getMakerCd());
        builder.append(", osVersion:").append(getOsVersion());
        builder.append(", appCd:").append(getAppCd());
        builder.append(", app:").append(getApp());
        return builder.toString();
    }
}
