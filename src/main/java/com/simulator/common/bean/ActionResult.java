/*
 * @(#)ActionResult.java
 *
 * Copyright(c) 2017-2018, NTTDATA Corporation.
 */
package com.simulator.common.bean;

import java.io.Serializable;

import com.simulator.common.util.DateUtil;


/**
 * アクション実行結果.
 *
 * @author NTTDATA
 * @version 1.0 2017/11/30
 */
public class ActionResult implements Serializable {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -5691581769888737685L;

    /**
     * 成功.
     */
    public static final int SUCCESS = 0;

    /**
     * 失敗.
     */
    public static final int FAILED = 1;

    /**
     * 時間.
     */
    private String time = DateUtil.getDateNowStr();

    /**
     * 返却結果コード 0:成功 1:失敗
     */
    private int resultCode = SUCCESS;

    /**
     * エラーコード.
     */
    private String errorCode;

    /**
     * エラーメッセージ
     */
    private String errorMessage;

    /**
     * 返却データ.
     */
    private Object data;

    /**
     * @return the resultCode
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * @param resultCode
     *            the resultCode to set
     */
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode
     *            the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage
     *            the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return this.time;
    }
}
