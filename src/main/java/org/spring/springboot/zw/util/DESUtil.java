/**
 * <p>Description: </p>
 * <p>Title: DESUtil.java</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: cei</p>
 * <p>@author zhaowei</p>
 * <p>@version 2018年4月26日 上午11:40:39</p>
 */
package org.spring.springboot.zw.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Title: DESUtil  
 * Description: 
 * @author zhaowei
 * @version 2018年4月26日 上午11:40:39
 */
public class DESUtil {
    /**
     * DES算法密钥 
     */
    private static final byte[] DES_KEY = {21, 1, 11, 82, 22, 44, 55, 33};
    private static final String code = "xzxzxzxz";

    /**
     * 数据加密，算法（DES） 
     *
     * @param data
     *            要进行加密的数据 
     * @return 加密后的数据
     */
    public static String encryptBasedDes(String data) {
        String encryptedData = null;
        try {
            // DES算法要求有一个可信任的随机数源  
            SecureRandom sr = new SecureRandom();
            System.out.println("sr---" + sr);
            DESKeySpec deskey = new DESKeySpec(DES_KEY);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 加密对象  
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
            // 加密，并把字节数组编码成字符串  
            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return encryptedData;
    }

    /**
     * 数据解密，算法（DES）
     *
     * @param cryptData
     *            加密数据
     * @return 解密后的数据
     */
    public static String decryptBasedDes(String cryptData) {
        String decryptedData = null;
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(DES_KEY);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 解密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            // 把字符串解码为字节数组，并解密
            decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));
        } catch (Exception e) {
            //log.error("解密错误，错误信息：", e);
            throw new RuntimeException("解密错误，错误信息：", e);
        }
        return decryptedData;
    }

    /**
     * 针对ios的加密方法
     * @param encryptString
     */
    public static String encryptDES(String encryptString, String encryptKey)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(DES_KEY);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.encode(encryptedData);
    }

    /**
     * 针对ios的解密方法
     * @param decryptString
     */
    public static String decryptDES(String decryptString, String decryptKey)
            throws Exception {
        byte[] byteMi = Base64.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(DES_KEY);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
    public static String base64Encode(byte[] bytes){
        return org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
    }
    public static byte[] base64Decode(String base64Code) throws Exception{
        return new BASE64Decoder().decodeBuffer(base64Code);
    }
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes);
    }
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String str ="123456";
        // DES数据加密
        // String s1=des.encryptBasedDes(str);
        //System.out.println(s1);
        // DES数据解密
        //String s2=decryptBasedDes(s1);
        String s2 = "";
        String s3 = "";
        try {
            s2 = encryptDES(str, code);
            s3 = decryptDES(s2, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println(s2);
        System.err.println(s3);

    }

}
