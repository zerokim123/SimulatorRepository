/*
 * ファイル名    ResponseTeleIF43Dto.java
 * 開発システム  次期決済システム本格対応 オンライン
 * 著作権        Copyright(C) 2018 NTT DATA CORPORATION
 * 会社名        NJK
 * 作成日        2018/03/16
 * 作成者        NJK 呂世紀
 * 履歴情報
 *     2018/03/16   NJK 呂世紀   初版作成
 */
package com.simulator.telegram.if44;

import java.io.Serializable;
import java.util.List;

/**
 * IF43_取引一覧照会応答DTO(繰り返し).
 *
 * @author NJK
 * @version 1.0 2018/03/16
 */
public class TeleIF43DataDto implements Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 4952019877596817922L;

    /**
     * IF43_取引一覧照会応答DTO(繰り返し).
     */
    private List<TeleIF43LoopDto> data = null;


    /**
     * ページサイズ.
     */
    private Integer pageSize = null;

    /**
     * スタート.
     */
    private Integer start = null;

    /**
     * トータルカウント.
     */
    private Integer totalCount = null;

    /**
     * ページ.
     */
    private Integer currentPageNo = null;

    /**
     * トータル.
     */
    private Integer total = null;

    /**
     * IF43_取引一覧照会応答DTO(繰り返し)の取得.
     *
     * @return data IF43_取引一覧照会応答DTO(繰り返し)
     */
    public List<TeleIF43LoopDto> getData() {
        return data;
    }

    /**
     * IF43_取引一覧照会応答DTO(繰り返し)の設定.
     *
     * @param data
     *            IF43_取引一覧照会応答DTO(繰り返し)
     */
    public void setData(List<TeleIF43LoopDto> data) {
        this.data = data;
    }

    /**
     * ページサイズの取得.
     *
     * @return pageSize ページサイズ
     */
    public final Integer getPageSize() {
        return pageSize;
    }

    /**
     * ページサイズの設定
     *
     * @param pageSize
     *            ページサイズ
     */
    public final void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * スタートの取得.
     *
     * @return start スタート
     */
    public final Integer getStart() {
        return start;
    }

    /**
     * スタートの設定
     *
     * @param start
     *            スタート
     */
    public final void setStart(final Integer start) {
        this.start = start;
    }

    /**
     * トータルカウントの取得.
     *
     * @return totalCount トータルカウント
     */
    public final Integer getTotalCount() {
        return totalCount;
    }

    /**
     * トータルカウントの設定
     *
     * @param totalCount
     *            トータルカウント
     */
    public final void setTotalCount(final Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * ページの取得.
     *
     * @return page ページ
     */
    public final Integer getCurrentPageNo() {
        return currentPageNo;
    }

    /**
     * ページの設定
     *
     * @param page
     *            ページ
     */
    public final void setCurrentPageNo(final Integer currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    /**
     * トータルの取得.
     *
     * @return total トータル
     */
    public final Integer getTotal() {
        return total;
    }

    /**
     * トータルの設定
     *
     * @param total
     *            トータル
     */
    public final void setTotal(final Integer total) {
        this.total = total;
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
        builder.append(",pageSize=");
        builder.append(getPageSize());
        builder.append(",start=");
        builder.append(getStart());
        builder.append(",totalCount=");
        builder.append(getTotalCount());
        builder.append(",currentPageNo=");
        builder.append(getCurrentPageNo());
        builder.append(",total=");
        builder.append(getTotal());
        builder.append("]");
        return builder.toString();
    }
}
