/*
 * ファイル名    ResponseTeleIF43Dto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/16
 * 作成者        NJK 戦彦鵬
 * 履歴情報
 *     2018/03/16   NJK 戦彦鵬   初版作成
 */
package com.simulator.telegram.if44    ;

import com.simulator.telegram.ResponseTeleHeaderDto;

/**
 * IF43_取引一覧照会応答DTO.
 *
 * @author NJK
 * @version 1.0 2018/03/16
 */
public class ResponseTeleIF43Dto extends ResponseTeleHeaderDto {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -2362745027504986682L;

    /**
     * エラーメッセージ.
     */
    private String errorMessage = "";

    /**
     * データ部.
     */
    private TeleIF43DataDto data = null;


    /**
     * エラーメッセージの取得.
     *
     * @return errorMessage エラーメッセージ
     */
    public final String getErrorMessage() {
        return errorMessage;
    }

    /**
     * エラーメッセージの設定.
     *
     * @param errorMessage
     *            エラーメッセージ
     */
    public final void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * データの取得.
     *
     * @return data データ
     */
    public TeleIF43DataDto getData() {
        return data;
    }

    /**
     * データ部の設定.
     *
     * @param data
     *            データ部
     */
    public void setData(TeleIF43DataDto data) {
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
