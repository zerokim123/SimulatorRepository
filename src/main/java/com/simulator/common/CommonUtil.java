package com.simulator.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {

    /**
     * JSON文字列を作成する.<br /><br />
     *
     * 引数のオブジェクトをJSON文字列に変換する.<br />
     * 変換に失敗した場合は、nullを返す.
     *
     * @param obj 変換対象オブジェクト
     * @return JSON文字列
     */
    public static String convertJson(final Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;

        try {
            json = mapper.writeValueAsString(obj);

            // 例外発生時はnullを返却する
        } catch (Exception e) {
            json = null;
        }

        return json;
    }

    /**
     * JSON文字列からオブジェクトを作成する.<br /><br />
     *
     * 引数のJSON文字列を引数で指定されたクラスに変換する.<br />
     * 変換に失敗した場合は、nullを返す.
     *
     * @param <T> 型
     * @param json 変換対象JSON文字列
     * @param valueType 変換対象クラス
     * @return オブジェクト
     */
    public static <T> T jsonConvertObject(
            final String json, final Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        T ret = null;
        try {
            ret = mapper.readValue(json, valueType);

            // 例外発生時はnullを返却する
        } catch (Exception e) {
            e.printStackTrace();
            ret = null;
        }

        return ret;
    }

    /**
     * 指定フォーマットの文字列を取得する。
     *
     * @param count 文字列の桁数
     *
     * @return String 指定された桁数のゼロ
     */
    public static String getZero(final Integer count) {
        return (StringUtils.leftPad("", count,
                "0"));
    }

    /**
     * SHA-1 を返却する.
     *
     * @param strText
     *            文字列
     * @return SHA-1
     */
    public static String getEncryptionSha1(final String strText) {
        return sha(strText, "SHA-1");
    }

    /**
     * SHA-256 を返却する.
     *
     * @param strText
     *            文字列
     * @return SHA-256
     */
    public static String getEncryptionSha256(final String strText) {
        return sha(strText, "SHA-256");
    }

    /**
     * SHA-512を返却する.
     *
     * @param strText
     *            文字列
     * @return SHA-512
     */
    public static String getEncryptionSha512(final String strText) {
        return sha(strText, "SHA-512");
    }

    /**
     * SHA暗号化.
     *
     * @param strText
     *            文字列
     * @param strType
     *            タイプ
     * @return SHA
     */
    private static String sha(final String strText, final String strType) {
        // 返却値
        String strResult = null;

        if (strText != null && 0 < strText.length()) {
            try {
                // SHA 暗号化start
                // 暗号化対象を作成する
                MessageDigest messageDigest = MessageDigest
                        .getInstance(strType);
                messageDigest.update(strText.getBytes());
                byte[] byteBuilder = messageDigest.digest();

                // byte → string
                StringBuilder strHexString = new StringBuilder();
                for (int i = 0; i < byteBuilder.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuilder[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
                return strResult;
            }
        }

        return strResult;
    }

    public static String requestHashValue(final Object obj) {
        String hashValue = null;

        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();

        treeMap = beanToMap(obj);

        if (treeMap.size() == 0) {
            return null;
        }

        treeMap.remove("class");
        treeMap.remove("identityInfoText");

        hashValue = MapToString(treeMap);
        if (StringUtils.isEmpty(hashValue)) {
            return null;
        }

        return getEncryptionSha1(hashValue.concat(CommonConst.SHA_KEY));

    }

    public static TreeMap<String, Object> beanToMap(final Object bean) {

        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(bean.getClass());
        } catch (IntrospectionException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        TreeMap<String, Object> beanMap = new TreeMap<String, Object>();

        for (PropertyDescriptor property : info.getPropertyDescriptors()) {

            Method getter = property.getReadMethod();
            String name = property.getName();
            Object value = null;
            try {
                value = getter.invoke(bean);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
            beanMap.put(name, value);

        }

        return beanMap;
    }

    public static String MapToString(final TreeMap<String, Object> map) {
        StringWriter writer = new StringWriter();
        try {
            MapToWriter(map, writer);
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
        String str = writer.toString();

        if (str.length() == 0) {

            return null;

        }

        str = str.substring(0, str.length() - 1);

        return str;

    }

    public static void MapToWriter(final TreeMap<String, Object> map, final Writer writer)
            throws IOException {

        //WriterをBufferedWriterに変換する
        BufferedWriter out;
        if (writer instanceof BufferedWriter) {
            out = (BufferedWriter) writer;
        } else {
            out = new BufferedWriter(writer);
        }

        Set<String> keys = map.keySet();
        Iterator<String> iter = keys.iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            Object value = map.get(key);
            out.write(key + "=" + value + "&");
        }
        out.flush();
    }

    public static String formattedTimestamp(final Timestamp timestamp, final String timeFormat) {
        return new SimpleDateFormat(timeFormat).format(timestamp);
    }

    public static Timestamp formattedString(String strDate, String timeFormat) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                timeFormat);
        Date date = null;
        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
            return null;
        }
        Timestamp timestamp = new Timestamp(date.getTime());

        return timestamp;
    }

}
