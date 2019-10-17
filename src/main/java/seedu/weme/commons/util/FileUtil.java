package seedu.weme.commons.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Writes and reads files
 */
public class FileUtil {

    public static final String MESSAGE_READ_FILE_FAILURE = "Error encountered while reading the file %s";
    public static final String MESSAGE_COPY_FAILURE_SOURCE_DOES_NOT_EXIST = "Copy failed: source file does not exist";

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
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path file, String content) throws IOException {
        Files.write(file, content.getBytes(CHARSET));
    }

    /**
     * Computes the SHA-1 hash of a file.
     *
     * @param file the file to compute
     * @return the SHA-1 hash of the file
     */
    public static String hash(Path file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] content = Files.readAllBytes(file);
            return StringUtil.byteArrayToHex(digest.digest(content));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format(MESSAGE_READ_FILE_FAILURE, file.toString()));
        }
    }

    /**
     * Copies the file from a directory to another directory.
     *
     * @param from the source
     * @param to   the destination
     * @throws IOException if the copy has
     */
    public static void copy(Path from, Path to) throws IOException {
        if (isFileExists(from)) {
            createParentDirsOfFile(to);
            Files.copy(from, to);
        } else {
            throw new IOException(MESSAGE_COPY_FAILURE_SOURCE_DOES_NOT_EXIST);
        }
    }

    /**
     * Gets the extension of {@code Path}.
     *
     * @param path the {@code Path} to extract the extension from
     * @return the extension if present, or {@code Optional#empty()} if there is none
     */
    public static Optional<String> getExtension(Path path) {
        String pathString = path.toString();
        if (pathString.contains(".")) {
            return Optional.of(pathString.substring(pathString.lastIndexOf(".") + 1));
        } else {
            return Optional.empty();
        }
    }
}
