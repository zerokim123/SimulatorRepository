/*
 * ファイル名    ResponseTeleIF39Dto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/20
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/20   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if40;

import com.simulator.telegram.ResponseTeleHeaderDto;

/**
 * ログイン情報応答DTO.
 *
 * @author NJK
 * @version 1.0 2018/03/20
 */
public class ResponseTeleIF39Dto extends ResponseTeleHeaderDto {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -6985587462171480458L;

    /**
     * ログイン情報応答DTO(Data部).
     */
    private TeleIF39DataDto data = null;

    /**
     * エラーメッセージ.
     */
    private String errorMessage = "";

    /**
     * ログイン情報応答DTO(Data部)の取得.
     *
     * @return data
     */
    public final TeleIF39DataDto getData() {
        return data;
    }

    /**
     * ログイン情報応答DTO(Data部)の設定.
     *
     * @param data
     *            ログイン情報応答DTO(Data部)
     */
    public final void setData(TeleIF39DataDto data) {
        this.data = data;
    }

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
        builder.append(",time=");
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
