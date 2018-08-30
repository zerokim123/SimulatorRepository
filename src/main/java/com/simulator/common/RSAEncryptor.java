package com.simulator.common;

/*
 * @(#)RSAEncryptor.java
 *
 * Copyright(c) 2017-2018, NTTDATA Corporation.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Component;



/**
 * RSAEncryptor.
 *
 * @author NTTDATA
 * @version 1.0 2017/11/30
 */
@Component
public class RSAEncryptor {

    /**
     * RSAEncryptor.
     *
     * @throws Exception Exception
     */
    public RSAEncryptor() {
        String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxEWdn7lbbmpJfa/mbM5IOjqLvZC/5DRzQEV1Gl5Ou919c1gr1JUQI0oACa4eoO2EYDetpDleHcER96d1Y6lJPyPHUuwInTBBOhSOsdYqrDRoW8anAwNhrYVLA/rRZ1I3OLVGkt5BIPn7yoaOygwHWskIvKQ0IBpgIlo2KIiOAPQIDAQAB";
        String private_key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALERZ2fuVtuakl9r+Zszkg6Oou9kL/kNHNARXUaXk673X1zWCvUlRAjSgAJrh6g7YRgN62kOV4dwRH3p3VjqUk/I8dS7AidMEE6FI6x1iqsNGhbxqcDA2GthUsD+tFnUjc4tUaS3kEg+fvKho7KDAdayQi8pDQgGmAiWjYoiI4A9AgMBAAECgYEAlld9oiwnCYzDrUuDgriV0dBgHbzGUsNEbJHhzddTaKLXEH8YjK8Yhhkc4XElnLzWItNmxyuLPA8hyHsyGpXSTklADBAvXB3aYl/WrwyyOkhE4K88jlADoROVAgKOejt8i+gJ5d3PqB9erJjVFb0XnCmAvW2cDTbKStVTTOsVSYECQQDzPfrTodsKz3TzpQcL2TjKaAO6KFOY7NaYxI2cSzeEi5g5PKim4hAp1JygT4Tyk4J2bJMTKMV+viydODAzrw8hAkEAulrm4YppCjIamxwUNjhus7VcL7FSFVtr0sg+YcAkNBtoCZOr7cOk8vQCj9jyzydjAEjnzhjgr5ySaFqzXq0ZnQJBAMlMVbOmkvuTTBoGTJqFnfYDtnHsxaIc/ktW/kqGBN/wFn3nsTrKgs3bYfNZW5DUenc+y0hjyW1mPM2vnlRnX4ECQCBJA1rrucr0ZzucH7xNBpDHyNLSa6AbmJCJ9JTGzv7gCKQwG0cz7+3PbGozcc2Fy1cPSmmgUbza6Vp+7Dho0NkCQQCqesw25mb91HbgnRZoKTtN4e6SULehn7IyNLWUN52rv0dFNry/o4U1I80YCQBEZ9WoKTuWparbSdH0IsbuxAmh";

        loadPublicKey(public_key);
        loadPrivateKey(private_key);

    }

    /**
     * getKeyFromFile.
     *
     * @param filePath ファイルパス
     * @return KeyFromFile
     * @throws Exception Exception
     */
    public static String getKeyFromFile(final String filePath){
        BufferedReader bufferedReader = null;
        List<String> list = new ArrayList<String>();
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // remove the firt line and last line
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < list.size() - 1; i++) {
            stringBuilder.append(list.get(i)).append("\r");
        }


        String key = stringBuilder.toString();
        return key;
    }

    /**
     * decryptWithBase64.
     *
     * @param base64String base64文字列
     * @return 文字列
     */
    public static String decryptWithBase64(final  String base64String) {
        // http://commons.apache.org/proper/commons-codec/ :
        // org.apache.commons.codec.binary.Base64
        // sun.misc.BASE64Decoder
        String string;
        try {
            byte[] binaryData;
            binaryData = decrypt(
                    getPrivateKey(), /*
                                         * new BASE64Decoder().decodeBuffer
                                         * (base64String)
                                         */
                    org.apache.commons.codec.binary.Base64.decodeBase64(
                            base64String.getBytes()));

            if (binaryData == null) {
                return "";
            }
            string = new String(binaryData);
        } catch (Exception e) {
            return "";
        }
        return string;
    }

    /**
     * encryptWithBase64.
     *
     * @param string 文字列
     * @return 暗号化文字列
     * @throws Exception Exception
     */
    public static String encryptWithBase64(final String string) throws Exception {
        // http://commons.apache.org/proper/commons-codec/ :
        // org.apache.commons.codec.binary.Base64
        // sun.misc.BASE64Encoder
        // Base64.encodeBase64String(signBytes).replaceAll("\n|\r",
        byte[] binaryData = encrypt(getPublicKey(), string.getBytes());
                                                                        // "");
        String base64String = new String(
                org.apache.commons.codec.binary.Base64.encodeBase64(
                        binaryData));
        return base64String;
    }

    /**
     * convenient properties
     */
    public static RSAEncryptor sharedInstance = null;

    /**
     * SharedInstanceを設定する
     *
     * @param rsaEncryptor RSAEncryptor
     */
    public static void setSharedInstance(RSAEncryptor rsaEncryptor) {
        sharedInstance = rsaEncryptor;
    }

    /**
     * privateKey
     */
    private static RSAPrivateKey privateKey;

    /**
     * publicKey
     */
    private static RSAPublicKey publicKey;

    /**
     * PrivateKeyを取得する
     *
     * @return PrivateKey
     */
    public static RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * PublicKeyを取得する
     *
     * @return PublicKey
     */
    public static RSAPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * KeyPairを取得する
     */
    public void genKeyPair() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAEncryptor.privateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAEncryptor.publicKey = (RSAPublicKey) keyPair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * publicKeyを設定する
     *
     * @param publicKeyStr publicKey
     * @throws Exception Exception
     */
    public void loadPublicKey(final String publicKeyStr) {
        // BASE64Decoder base64Decoder= new BASE64Decoder();
        // base64Decoder.decodeBuffer(publicKeyStr);
        byte[] buffer = org.apache.commons.codec.binary.Base64.decodeBase64(
                publicKeyStr.getBytes());
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            RSAEncryptor.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("該当計算方法はなし");
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("公開鍵が不法である");
        } catch (NullPointerException e) {
            throw new RuntimeException("公開鍵が空である");
        }
    }

    /**
     * privateKeyを設定する
     *
     * @param privateKeyStr privateKey
     * @throws Exception Exception
     */
    public void loadPrivateKey(final String privateKeyStr) {
        try {
            // BASE64Decoder base64Decoder= new BASE64Decoder();
            // base64Decoder.decodeBuffer(privateKeyStr);
            byte[] buffer = org.apache.commons.codec.binary.Base64.decodeBase64(
                    privateKeyStr.replaceAll("\n|\r", "").getBytes());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAEncryptor.privateKey = (RSAPrivateKey)
                    keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("該当計算方法はなし");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new RuntimeException("プライベート鍵が不法である");
        } catch (NullPointerException e) {
            throw new RuntimeException("プライベート鍵が空である");
        }
    }

    /**
     * 暗号化
     *
     * @param publicKey 公開鍵
     * @param plainTextData クリアテキスト
     * @return 暗号化バイト配列
     * @throws Exception 異常
     */
    public static byte[] encrypt(final RSAPublicKey publicKey, final byte[] plainTextData)
            throws Exception {
        if (publicKey == null) {
            throw new Exception("公開鍵が空である、設定してください");
        }
        Cipher cipher = null;
        try {
            // , new BouncyCastleProvider());
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("該当計算方法はなし");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("公開鍵が不法である、チェックしてください");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("クリアテキストの桁数が不法である");
        } catch (BadPaddingException e) {
            throw new Exception("クリアテキストが崩れた");
        }
    }

    /**
     * デコード
     *
     * @param privateKey プライベート鍵
     * @param cipherData 暗号化データ
     * @return クリアテキスト
     * @throws Exception 異常
     */
    public static byte[] decrypt(final RSAPrivateKey privateKey, final byte[] cipherData)
            throws Exception {
        if (privateKey == null) {
            throw new Exception("プライベート鍵が空である、設定してください");
        }
        Cipher cipher = null;
        try {
            // , new BouncyCastleProvider());
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("デコードできない");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("プライベート鍵が不法である、チェックしてください");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("暗号化データ の桁数が不法である");
        } catch (BadPaddingException e) {
            throw new Exception("暗号化データが崩れた");
        }
    }

    /**
     * HEX_CHAR.
     */
    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * byte→String
     *
     * @param data バイト配列
     * @return 文字列
     */
    public static String byteArrayToString(final byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
            if (i < data.length - 1) {
                stringBuilder.append(' ');
            }
        }
        return stringBuilder.toString();
    }

    public static String decrypt(final String str) {
        String password = null;
        try{
//            RSAEncryptor rsae = new RSAEncryptor();
            password = RSAEncryptor.decryptWithBase64(str);
        }catch(Exception e){
            e.printStackTrace();
        }
        return password;
    }

}