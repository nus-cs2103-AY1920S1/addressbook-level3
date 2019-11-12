package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

import seedu.address.commons.util.EncryptionUtil;

/**
 * Handles initialisation and validation of master password.
 */
public class TestStorage {
    private static final String MAGIC_WORD = "hello world";
    private static final String TEST_FILENAME = "magic_word";

    /**
     * Initialises a master password and store the encrypted magic word in the storage.
     * @param password the master password.
     * @throws IOException if the master password cannot be stored.
     */
    public static void initPassword(String password) throws IOException {
        try {
            byte[] encryptedByteArray = EncryptionUtil.encryptBytesFromString(MAGIC_WORD, password);
            Files.write(Paths.get(TEST_FILENAME), encryptedByteArray);
        } catch (GeneralSecurityException e) {
            throw new IOException("Init password failed.");
        }
    }

    /**
     * Returns whether the given password matches the master password set previously.
     * @param password the given password.
     * @return whether the given password matches the master password.
     * @throws IOException if the encrypted magic word file cannot be retrieved.
     */
    public static boolean testPassword(String password) throws IOException {
        byte[] byteArrayFromFile = Files.readAllBytes(Paths.get(TEST_FILENAME));
        try {
            String decryptedMagicWord = EncryptionUtil.decryptBytesToString(byteArrayFromFile, password);
            return MAGIC_WORD.equals(decryptedMagicWord);
        } catch (GeneralSecurityException e) {
            return false;
        }
    }

    /**
     * Returns whether the user has already set the master password.
     * @return whether the master password is already set.
     */
    public static boolean isUserExist() {
        return Files.exists(Paths.get(TEST_FILENAME));
    }
}
