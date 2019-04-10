package com.nc.portal.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class Aes256 {
    private SecretKey secretKey;

    public Aes256() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            this.secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public byte[] makeAes(byte[] rawMessage, int cipherMode) {
/*        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, this.secretKey);
            byte[] output = cipher.doFinal(rawMessage);
            if (cipherMode == Cipher.DECRYPT_MODE) {
                byte[] result = new byte[output.length];
                //System.arraycopy(output, 0, result, 0, output.length);
                return result;
            }
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, this.secretKey);
            byte [] output = cipher.doFinal(rawMessage);
            return output;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
