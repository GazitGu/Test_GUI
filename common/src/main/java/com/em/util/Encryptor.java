package com.em.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by guchong on 5/22/2018.
 */
public class Encryptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Encryptor.class);

    private static final String AES_KEY = "effectmarket0001";
    private static final String INIT_VECTOR = "1234567890abcdef"; // 16 bytes IV
    public static String encrypt(String value) {
        return encrypt(AES_KEY, INIT_VECTOR, value);
    }

    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            LOGGER.error("Error when encrypt for key: {} ", key, ex);
        }
        return value;
    }

    public static String decrypt(String encrypted){
        return decrypt(AES_KEY, INIT_VECTOR, encrypted);
    }

    public static String decrypt(String key, String initVector, String encrypted){
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            LOGGER.error("Error when decrypt for: {}", ex);
            throw new RuntimeException(ex);
        }
    }
}
