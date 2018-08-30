/*
 * @(#)SHAUtil.java
 *
 * Copyright(c) 2017-2018, NTTDATA Corporation.
 */
package com.simulator.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jboss.logging.Logger;

import com.simulator.common.CommonConst;

/**
 * SHAUtil.
 *
 * @author NTTDATA
 * @version 1.0 2017/11/30
 */
public class SHAUtil {
    private static final Logger log = Logger.getLogger(SHAUtil.class);

    /**
     *  SHA-1 を返却する
     *
     * @param strText 文字列
     * @return SHA-1
     */
    public static String SHA1(final String strText) {
        return SHA(strText, "SHA-1");
    }

    /**
     * SHA-256 を返却する
     *
     * @param strText 文字列
     * @return SHA-256
     */
    public static String SHA256(final String strText) {
        return SHA(strText, "SHA-256");
    }

    /**
     * SHA-512を返却する
     *
     * @param strText 文字列
     * @return SHA-512
     */
    public static String SHA512(final String strText) {
        return SHA(strText, "SHA-512");
    }

    /**
     * 暗号化文字列の判定
     *
     * @param strText 文字列
     * @param strSign サイン
     * @return 判断結果
     */
    public static boolean CheckSign(String strText, String strSign) {

        boolean flag = false;

        String strKey = CommonConst.SHA_KEY;


        //暗号化
        String strSha = SHA1(strText + strKey);

        if (strSign.equals(strSha))
        {
            flag = true;
        } else {
            log.error("hash値チェックエラー");
            log.error("受信hash値：" + strSign);
            log.error("計算hash値：" + strSha);
        }

        return flag;
    }

    /**
     * SHA暗号化
     *
     * @param strText 文字列
     * @param strType タイプ
     * @return SHA
     */
    private static String SHA(final String strText, final String strType) {
        // 返却値
        String strResult = null;

        if (strText != null && 0 < strText.length() ) {
            try {
                // SHA 暗号化start
                // 暗号化対象を作成する
                MessageDigest messageDigest =
                        MessageDigest.getInstance(strType);
                messageDigest.update(strText.getBytes());
                byte byteBuffer[] = messageDigest.digest();

                // byte → string
                StringBuffer strHexString = new StringBuffer();
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return strResult;
    }
}
