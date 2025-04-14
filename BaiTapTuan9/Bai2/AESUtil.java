package Bai2;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

public class AESUtil implements Encryptable {

    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private static final String KEY = "1234567890123456"; 
    private static final String IV = "1234567890123456"; 

    @Override
    public String encrypt(String input) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(IV.getBytes())); 

            byte[] encryptedBytes = cipher.doFinal(input.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String decrypt(String input) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(IV.getBytes()));

            byte[] decodedBytes = Base64.getDecoder().decode(input);

            if (decodedBytes.length == 0) {
                System.out.println("Decoded data is empty or incorrect.");
                return null;
            }

            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes); 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


