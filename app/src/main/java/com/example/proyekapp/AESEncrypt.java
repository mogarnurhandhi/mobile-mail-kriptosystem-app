package com.example.proyekapp;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESEncrypt {
    private byte encryptionKey[] = {9,115,51,86,105,4,-31,-33,-68,88,17,20,3,-105,119,-53};
    private Cipher cipher;
    private SecretKeySpec secretKeySpec;

    public String AESEncrypt(String string){
        try {
            cipher = Cipher.getInstance("AES");
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        secretKeySpec = new SecretKeySpec(encryptionKey, "AES");

        byte[] stringByte = string.getBytes();
        byte[] encryptedByte = new byte[stringByte.length];

        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encryptedByte = cipher.doFinal(stringByte);
        }catch (InvalidKeyException e) {
            e.printStackTrace();
        }catch (BadPaddingException e) {
            e.printStackTrace();
        }catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        String retrurnString=null;
        try {
            retrurnString = new String(encryptedByte, "ISO-8859-1");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retrurnString;
    }
}
