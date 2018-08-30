/*
 * @(#)WrappedHttpServletRequest.java
 *
 * Copyright(c) 2017-2018, NTTDATA Corporation.
 */
package com.simulator.http.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

/**
 * WrappedHttpServletRequest.
 *
 * @author NTTDATA
 * @version 1.0 2017/11/30
 */
public class WrappedHttpServletRequest extends HttpServletRequestWrapper {
    /**
     * byte.
     */
    private byte[] bytes;

    /**
     * WrappedServletInputStream.
     */
    private WrappedServletInputStream wrappedServletInputStream;

    /**
     * リクエスト
     *
     * @param request リクエスト
     * @throws IOException IOException
     */
    public WrappedHttpServletRequest(
            HttpServletRequest request)
            throws IOException {
        super(
                request);
        // パラメータを取得する
        bytes = IOUtils.toByteArray(request.getInputStream());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                bytes);
        this.wrappedServletInputStream = new WrappedServletInputStream(
                byteArrayInputStream);

        // 取得したパラメータをストリームに出力する
        reWriteInputStream();

    }

    /**
     * 取得したパラメータをストリームに出力
     */
    public void reWriteInputStream() {
        wrappedServletInputStream.setStream(new ByteArrayInputStream(
                        bytes != null ? bytes : new byte[0]));
    }

    /**
     * ServletInputStream
     *
     * @return ServletInputStream
     * @throws IOException IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return wrappedServletInputStream;
    }

    /**
     * BufferedReader
     *
     * @return BufferedReader
     * @throws IOException IOException
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(
                new InputStreamReader(wrappedServletInputStream));
    }

    /**
     * POSTパラメータの取得
     *
     * @return POSTパラメータ
     * @throws IOException IOException
     */
    public String getRequestParams() throws IOException {
        return new String(bytes, this.getCharacterEncoding());
    }

    /**
     * WrappedServletInputStream
     */
    private class WrappedServletInputStream extends ServletInputStream {
        /**
         * InputStreamを設定する
         *
         * @param stream InputStream
         */
        public void setStream(InputStream stream) {
            this.stream = stream;
        }

        /**
         * InputStream
         */
        private InputStream stream;

        /**
         * WrappedServletInputStream
         *
         * @param stream InputStream
         */
        public WrappedServletInputStream(
                InputStream stream) {
            this.stream = stream;
        }

        /**
         * read
         *
         * @return 結果
         * @throws IOException IOException
         */
        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            // TODO 自動生成されたメソッド・スタブ
            return false;
        }

        @Override
        public boolean isReady() {
            // TODO 自動生成されたメソッド・スタブ
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            // TODO 自動生成されたメソッド・スタブ

        }
    }
}
