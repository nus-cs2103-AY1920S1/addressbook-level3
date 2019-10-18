package dream.fcard.util.code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Reads in JS code from a .js file and returns a BufferedReader if the file is valid or null
 * if the file is invalid.
 */
public class FileImporter {
    /**
     * Stores JS code from a file into a BufferedReader
     * @param filepath the destination of the .js file
     * @return a BufferedReader containing the code or null if file could not be read.
     */
    static BufferedReader readJsFile(String filepath) {
        try {
            if (filepath.endsWith("js")) {
                return new BufferedReader(new FileReader(filepath));
            } else {
                System.err.println("I can only read JS files :(");
                return null;
            }
        } catch (FileNotFoundException e) {
            System.err.println("The file you are trying to read from could not be found :(");
            return null;
        }
    }
}
