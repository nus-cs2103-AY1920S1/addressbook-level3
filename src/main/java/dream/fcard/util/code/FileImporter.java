package dream.fcard.util.code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Reads in code from a file and returns a BufferedReader if the file is valid or null
 * if the file is invalid.
 */
public class FileImporter {
    /**
     * Stores code from a file into a BufferedReader
     *
     * @param filepath the destination of the file
     * @return a BufferedReader containing the code or null if file could not be read.
     */
    public static BufferedReader readFile(String filepath) {
        try {
            return new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            System.err.println("The file you are trying to read from could not be found :(");
            return null;
        }
    }
}

