/*
 * ファイル名    ResponseTeleIF33Dto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/20
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/20   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if34;

import com.simulator.telegram.ResponseTeleHeaderDto;

/**
 * IF33_クーポン利用取消情報応答DTO.
 *
 * @author NJK
 * @version 1.0 2018/03/20
 */
public class ResponseTeleIF33Dto extends ResponseTeleHeaderDto {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -8653172529317314333L;

    /**
     * クーポン利用取消情報応答DTO(Data部).
     */
    private TeleIF33DataDto data = null;

    /**
     * エラーメッセージ.
     */
    private String errorMessage = "";

    /**
     * クーポン利用取消情報応答DTO(Data部)の取得.
     *
     * @return errorMessage エラーメッセージ
     */
    public final TeleIF33DataDto getData() {
        return data;
    }

    /**
     * クーポン利用取消情報応答DTO(Data部)の設定
     *
     * @param errorMessage
     *            エラーメッセージ
     */
    public final void setData(TeleIF33DataDto data) {
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
        builder.append("time=");
        builder.append(getTime());
        builder.append(",resultCode=");
        builder.append(getResultCode());
        builder.append(",errorCode=");
        builder.append(getErrorCode());
        builder.append("data=");
        builder.append(getData().toString());
        builder.append(",errorMessage=");
        builder.append(getErrorMessage());
        builder.append("]");
        return builder.toString();
    }
}
