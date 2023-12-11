package com.example.proyekapp;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESDecrypt {
    private byte encryptionKey[] = {9,115,51,86,105,4,-31,-33,-68,88,17,20,3,-105,119,-53};
    private Cipher decipher;
    private SecretKeySpec secretKeySpec;

    public String AESDecrypt (String string) throws UnsupportedEncodingException {
        try {
            decipher = Cipher.getInstance("AES");
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        secretKeySpec = new SecretKeySpec(encryptionKey, "AES");

        byte[] encryptedByte = string.getBytes("ISO-8859-1");

        String decryptedString = string;
        byte[] decryption;

        try {
            decipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            decryption = decipher.doFinal(encryptedByte);
            decryptedString = new String(decryption);
        }catch (InvalidKeyException e) {
            e.printStackTrace();
        }catch (BadPaddingException e) {
            e.printStackTrace();
        }catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return decryptedString;
    }
}
