package seedu.address.commons.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(Path file) {
        return Files.exists(file) && Files.isRegularFile(file);
    }

    /**
     * Returns true if {@code path} can be converted into a {@code Path} via {@link Paths#get(String)},
     * otherwise returns false.
     * @param path A string representing the file path. Cannot be null.
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ipe) {
            return false;
        }
        return true;
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(Path file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     */
    public static void createFile(Path file) throws IOException {
        if (Files.exists(file)) {
            return;
        }

        createParentDirsOfFile(file);

        Files.createFile(file);
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(Path file) throws IOException {
        Path parentDir = file.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file), CHARSET);
    }

    /**
     * Read data as string from an encrypted file.
     * @param file the file to be decrypted.
     * @param password used to decrypt the file.
     * @return the decrypted string.
     * @throws IOException if the file cannot be decrypted using the password.
     */
    public static String readFromEncryptedFile (Path file, String password) throws IOException {
        try {
            return new String(EncryptionUtil.decryptBytes(Files.readAllBytes(file), password), CHARSET);
        } catch (GeneralSecurityException e) {
            throw new IOException("Read encrypted file failed.");
        }

    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path file, String content) throws IOException {
        Files.write(file, content.getBytes(CHARSET));
    }

    /**
     * Write data as string to an encrypted file.
     * @param file the file to be written.
     * @param password used to encrypt the string.
     * @throws IOException if the file cannot be encrypted using the password.
     */
    public static void writeToEncryptedFile (Path file, String content, String password) throws IOException {
        try {
            Files.write(file, EncryptionUtil.encryptBytes(content.getBytes(CHARSET), password));
        } catch (GeneralSecurityException e) {
            throw new IOException("Write encrypted file failed.");
        }
    }

}
