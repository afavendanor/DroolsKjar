package co.com.suramericana.security;

import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import co.com.suramericana.exception.TechnicalException;

public final class Encryptor {

    private static final int KEY_SIZE = 256;
    private static final String SEPARATOR = "&&";
    private static final String ENCRYTOR_SPECIFICATION = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/ECB/PKCS5Padding";

    private Encryptor() {
    }

    public static final String encode(String itemToEncode) {
        try {
            final KeyGenerator keyGen = KeyGenerator.getInstance(ENCRYTOR_SPECIFICATION);
            keyGen.init(KEY_SIZE);
            final SecretKey secretKey = keyGen.generateKey();
            final javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, secretKey);
            final byte[] encodedItem = cipher.doFinal(itemToEncode.getBytes());
            final byte[] key = secretKey.getEncoded();
            return Base64.getEncoder().withoutPadding().encodeToString(encodedItem)
                    + SEPARATOR + Base64.getEncoder().withoutPadding().encodeToString(key);
        } catch (Exception e) {
            throw new TechnicalException(e.getMessage(),e);
        }
    }

    public static final String decode(String secret) {
        try {
        final String[] secretItems = secret.split(SEPARATOR);
        final byte[] decodedSecret = Base64.getDecoder().decode(secretItems[0]);
        final byte[] decodedKey = Base64.getDecoder().decode(secretItems[1]);
        final SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, ENCRYTOR_SPECIFICATION);
        final javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, originalKey, cipher.getParameters());
        return new String(cipher.doFinal(decodedSecret));
        } catch (Exception  e) {
            throw new TechnicalException(e.getMessage(),e);
        }
    }
}
