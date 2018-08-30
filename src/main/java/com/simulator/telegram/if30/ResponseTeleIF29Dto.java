/*
 * ファイル名    ResponseTeleIF29Dto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/20
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/20   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if30;

import com.simulator.telegram.ResponseTeleHeaderDto;

/**
 * IF29_クーポン消込情報応答DTO.
 *
 * @author NJK
 * @version 1.0 2018/03/20
 */
public class ResponseTeleIF29Dto extends ResponseTeleHeaderDto {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -6064991731809328967L;

    /**
     * クーポン消込情報応答DTO(Data部).
     */
    private TeleIF29DataDto data = null;

    /**
     * エラーメッセージ.
     */
    private String errorMessage = "";

    /**
     * エラーメッセージの取得.
     *
     * @return errorMessage エラーメッセージ
     */
    public final String getErrorMessage() {
        return errorMessage;
    }

    /**
     * エラーメッセージの設定
     *
     * @param errorMessage
     *            エラーメッセージ
     */
    public final void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * クーポン消込情報応答DTO(Data部)の取得.
     *
     * @return data
     */
    public final TeleIF29DataDto getData() {
        return data;
    }

    /**
     * クーポン消込情報応答DTO(Data部)の設定.
     *
     * @param data
     *            クーポン消込情報応答DTO(Data部)
     */
    public final void setData(TeleIF29DataDto data) {
        this.data = data;
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
        builder.append("data=");
        builder.append(getData().toString());
        builder.append("time=");
        builder.append(getTime());
        builder.append(",resultCode=");
        builder.append(getResultCode());
        builder.append(",errorCode=");
        builder.append(getErrorCode());
        builder.append(",errorMessage=");
        builder.append(getErrorMessage());
        builder.append("]");
        return builder.toString();
    }
}
