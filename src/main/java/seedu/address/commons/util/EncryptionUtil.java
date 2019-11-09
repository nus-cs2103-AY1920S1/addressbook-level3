package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import seedu.address.commons.exceptions.TargetFileExistException;

/**
 * A class for handling encryption and decryption.
 */
public class EncryptionUtil {
    /**
     * An enumeration to represent encryption mode and decryption mode.
     */
    private enum EncryptionMode {
        ENCRYPT,
        DECRYPT
    }

    private static final byte[] SALT = {
        (byte) 0x43, (byte) 0x76, (byte) 0x95, (byte) 0xc7,
        (byte) 0x5b, (byte) 0xd7, (byte) 0x45, (byte) 0x17
    };
    private static final int ITERATION = 68;

    private static final String SIGNATURE = "[LOCKED]";

    /**
     * Encrypts or decrypts a byte array using a given password.
     * @param input the byte array to be encrypted or decrypted.
     * @param password the password used for encryption or decryption.
     * @param mode whether to encrypt or decrypt the byte array.
     * @return the encrypted or decrypted byte array.
     */
    private static byte[] cipherBytes(byte[] input, String password, EncryptionMode mode)
            throws GeneralSecurityException {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndTripleDES");
        SecretKey key = keyFactory.generateSecret(keySpec);
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(SALT, ITERATION);
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndTripleDES");
        switch (mode) {
        case ENCRYPT:
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
            break;
        case DECRYPT:
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
            break;
        default:
            break;
        }
        return cipher.doFinal(input);
    }

    /**
     * Encrypts a byte array using a given password.
     * @param input the byte array to be encrypted.
     * @param password the password used for encryption.
     * @return the encrypted byte array.
     */
    public static byte[] encryptBytes(byte[] input, String password) throws GeneralSecurityException {
        return cipherBytes(input, password, EncryptionMode.ENCRYPT);
    }

    /**
     * Decrypts a byte array using a given password.
     * @param input the byte array to be decrypted.
     * @param password the password used for encryption.
     * @return the decrypted byte array.
     */
    public static byte[] decryptBytes(byte[] input, String password) throws GeneralSecurityException {
        return cipherBytes(input, password, EncryptionMode.DECRYPT);
    }

    /**
     * Encrypts a string into byte array using a given password.
     * @param input the string to be encrypted.
     * @param password the password used for encryption.
     * @return the encrypted byte array.
     */
    public static byte[] encryptBytesFromString(String input, String password) throws GeneralSecurityException {
        return encryptBytes(input.getBytes(), password);
    }

    /**
     * Decrypts a byte array into string using a given password.
     * @param input the byte array to be decrypted.
     * @param password the password used for decryption.
     * @return the decrypted string.
     */
    public static String decryptBytesToString(byte[] input, String password) throws GeneralSecurityException {
        return new String(decryptBytes(input, password));
    }

    /**
     * Decrypts a file to a byte array.
     * @param filepath the file path of the file to be decrypted.
     * @param password the password used for decryption.
     * @return the decrypted byte array.
     */
    public static byte[] decryptFileToBytes(String filepath, String password)
            throws IOException, GeneralSecurityException {
        Path oldPath = Paths.get(filepath);
        byte[] fileData = new byte[(int) Files.size(oldPath) - SIGNATURE.length()];
        InputStream inStream = new FileInputStream(new File(filepath));
        inStream.skip(SIGNATURE.length());
        inStream.read(fileData, 0, fileData.length);
        inStream.close();
        return decryptBytes(fileData, password);
    }

    /**
     * Encrypts a file using the given file paths and password.
     * @param source the path of the original file.
     * @param target the path of the encrypted file.
     * @param password the password used for decryption.
     * @throws IOException if the encryption fails.
     */
    public static void encryptFile(String source, String target, String password)
            throws IOException, TargetFileExistException, GeneralSecurityException {
        Path newPath = Paths.get(target);
        if (Files.exists(newPath)) {
            throw new TargetFileExistException(target);
        }
        Path oldPath = Paths.get(source);
        byte[] fileData = Files.readAllBytes(oldPath);
        byte[] processedData = encryptBytes(fileData, password);
        Files.write(newPath, SIGNATURE.getBytes());
        Files.write(newPath, processedData, StandardOpenOption.APPEND);
        try {
            Files.deleteIfExists(oldPath);
        } catch (IOException e) {
            // If deletion of original file fails, delete the encrypted file to prevent duplication.
            Files.deleteIfExists(newPath);
            throw e;
        }
    }

    /**
     * Decrypts a file using the given file paths and password.
     * @param source the path of the original file.
     * @param target the path of the decrypted file.
     * @param password the password used for decryption.
     * @throws IOException if the decryption fails.
     */
    public static void decryptFile(String source, String target, String password)
            throws IOException, TargetFileExistException, GeneralSecurityException {
        Path newPath = Paths.get(target);
        if (Files.exists(newPath)) {
            throw new TargetFileExistException(target);
        }
        byte[] processedData = decryptFileToBytes(source, password);
        Files.write(newPath, processedData);
        try {
            Path oldPath = Paths.get(source);
            Files.deleteIfExists(oldPath);
        } catch (IOException e) {
            Files.deleteIfExists(newPath);
            throw e;
        }
    }

    /**
     * Returns true if the file is an encrypted file.
     */
    public static boolean isFileEncrypted(String path) throws IOException {
        byte[] fileSignature = new byte[SIGNATURE.length()];
        InputStream inStream = new FileInputStream(new File(path));
        int signLength = inStream.read(fileSignature, 0, SIGNATURE.length());
        inStream.close();
        if (signLength != SIGNATURE.length()) {
            return false;
        }
        return Arrays.equals(SIGNATURE.getBytes(), fileSignature);
    }
}
