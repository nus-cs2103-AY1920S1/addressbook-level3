package seedu.address.model.util;

import seedu.address.model.file.EncryptedFile;

/**
 * Contains utility methods for formatting file names.
 */
public class FileNameUtil {
    /**
     * Returns a file name with the encrypted file prefix.
     */
    public static String getFileNameWithPrefix(String input) {
        if (isFileNameWithPrefix(input)) {
            return input;
        }
        return EncryptedFile.PREFIX + " " + input;
    }

    /**
     * Returns a file name without the encrypted file prefix.
     */
    public static String getFileNameWithoutPrefix(String input) {
        if (isFileNameWithPrefix(input)) {
            return input.substring(EncryptedFile.PREFIX.length() + 1);
        }
        return input;
    }

    /**
     * Checks if a file name contains the encrypted file prefix.
     */
    private static boolean isFileNameWithPrefix(String input) {
        return input.length() > EncryptedFile.PREFIX.length() + 1
                && input.substring(0, EncryptedFile.PREFIX.length() + 1).equals(EncryptedFile.PREFIX + " ");
    }
}
