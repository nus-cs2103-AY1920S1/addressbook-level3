package seedu.address.testutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Utility class for dealing with {@code File}s.
 */
public class FileUtil {

    /**
     * Checks if given files have the same content.
     */
    public static boolean hasEqualContents(File file1, File file2) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));
        String[] fileLines1 = reader1.lines().toArray(String[]::new);
        String[] fileLines2 = reader2.lines().toArray(String[]::new);
        reader1.close();
        reader2.close();
        return Arrays.equals(fileLines1, fileLines2);
    }

}
