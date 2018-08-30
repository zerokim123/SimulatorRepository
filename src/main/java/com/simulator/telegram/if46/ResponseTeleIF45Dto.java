/*
 * ファイル名    ResponseTeleIF45Dto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/20
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/20   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if46;

import com.simulator.telegram.ResponseTeleHeaderDto;

/**
 * IF45_取引詳細照会応答DTO.
 *
 * @author NJK
 * @version 1.0 2018/03/20
 */
public class ResponseTeleIF45Dto extends ResponseTeleHeaderDto {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -1021583554258827396L;

    /**
     * IF45_取引詳細照会応答DTO(Data部).
     */
    private TeleIF45DataDto data = null;

    /**
     * エラーメッセージ.
     */
    private String errorMessage = "";

    /**
     * IF45_取引詳細照会応答DTO(Data部)の取得.
     *
     * @return data IF45_取引詳細照会応答DTO(Data部)
     */
    public final TeleIF45DataDto getData() {
        return data;
    }

    /**
     * IF45_取引詳細照会応答DTO(Data部)の設定.
     *
     * @param data
     *            IF45_取引詳細照会応答DTO(Data部)
     */
    public final void setData(TeleIF45DataDto data) {
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
