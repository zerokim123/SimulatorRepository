/*
 * ファイル名    ResponseTeleHeaderDto.java
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
 * 配信情報の共通ヘッダDTO.
 *
 * @author NJK
 * @version 1.0 2018/03/16
 */
public class ResponseTeleHeaderDto implements Serializable {

    /**
     *シリアルバージョンUID.
     */
    private static final long serialVersionUID = -7992225098910748173L;

    /**
     * 処理日時.
     */
    private String time = "";

    /**
     * 処理結果.
     */
    private Integer resultCode = null;

    /**
     * エラーコード.
     */
    private String errorCode = "";

    /**
     * 処理日時の取得.
     *
     * @return time 処理日時
     */
    public final String getTime() {
        return time;
    }

    /**
     * 処理日時の設定
     *
     * @param time
     *            処理日時
     */
    public final void setTime(final String time) {
        this.time = time;
    }

    /**
     * 処理結果の取得.
     *
     * @return resultCode 処理結果
     */
    public final Integer getResultCode() {
        return resultCode;
    }

    /**
     * 処理結果の設定
     *
     * @param resultCode
     *            処理結果
     */
    public final void setResultCode(final Integer resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * エラーコードの取得.
     *
     * @return errorCode エラーコード
     */
    public final String getErrorCode() {
        return errorCode;
    }

    /**
     * エラーコードの設定
     *
     * @param errorCode
     *            エラーコード
     */
    public final void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("time:").append(getTime());
        builder.append(", resultCode:").append(getResultCode());
        builder.append(", errorCode:").append(getErrorCode());
        return builder.toString();
    }
}
