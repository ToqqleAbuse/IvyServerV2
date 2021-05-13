package system.tools.skills;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class ISP {

    public static String getMd5(final String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static final int keySize = 128;
    public static final int iterationCount = 1000;

    public static String aesEncrypt(final String input, final String key, final String salt, final String four){
        SecretKey sKey = generateKey(salt, key);
        byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, sKey, four, input.getBytes(StandardCharsets.UTF_8));
        return base64(encrypted);
    }

    public static String aesDecrypt(final String input, final String key, final String salt, final String four){
        SecretKey sKey = generateKey(salt, key);
        byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, sKey, four, base64(input));
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    static byte[] doFinal(int encryptMode, SecretKey key, String iv, byte[] bytes){
        try{
            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(encryptMode, key, new IvParameterSpec(hex(iv)));
            return cipher.doFinal(bytes);
        }catch(InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | NoSuchAlgorithmException e){
            return null;
        }
    }

    static SecretKey generateKey(String salt, String passphrase){
        try{
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex(salt), iterationCount, keySize);
            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        }catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            return null;
        }
    }

    static String base64(byte[] bytes){
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(bytes));
    }

    static byte[] base64(String str){
        return org.apache.commons.codec.binary.Base64.decodeBase64(str.getBytes());
    }

    public static byte[] hex(String str){
        try{
            return Hex.decodeHex(str.toCharArray());
        }catch(DecoderException e){
            throw new IllegalStateException(e);
        }
    }

}
