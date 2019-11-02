package seedu.address.commons.util;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * A class for generating random passwords.
 */
public class GeneratorUtil {

    private static String[] lowAlpha = new String[] {
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
        "u", "v", "w", "x", "y", "z"};
    private static String[] highAlpha = new String[] {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
        "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
        "U", "V", "W", "X", "Y", "Z"};
    private static String[] specialChars = new String[] {
        "~", "`", "!", "@", "#", "$", "%", "^", "&", "*",
        "(", ")", "-", "_", "+", "=", "[", "{", "]", "}",
        "|", "\\", "'", "\"", ";", ":", "?", "/", ".", ">",
        "<", ","};
    private static String[] numbers = new String[] {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    /**
     * Generates random password value based on user input.
     * @return a random password value string
     */
    public static String generateRandomPassword(int length, boolean lower,
                                                boolean upper, boolean num, boolean special) {
        ArrayList<String[]> characterSet = setCharacterSet(lower, upper, special, num);
        SecureRandom randomNumGen = new SecureRandom();
        StringBuilder password = new StringBuilder();

        //generate random character from characterSet for 8 times.
        for (int k = 0; k < length; k++) {
            int ranArrayChooser = randomNumGen.nextInt(characterSet.size());
            int randomLetterIndex = randomNumGen.nextInt(characterSet.get(ranArrayChooser).length - 1);
            password.append(characterSet.get(ranArrayChooser)[randomLetterIndex]);
        }

        return password.toString();
    }

    private static ArrayList<String[]> setCharacterSet(boolean lower,
                                                       boolean upper, boolean special, boolean num) {

        ArrayList<String[]> characterSet = new ArrayList<>();

        if (lower) {
            characterSet.add(lowAlpha);
        }
        if (upper) {
            characterSet.add(highAlpha);
        }
        if (special) {
            characterSet.add(specialChars);
        }
        if (num) {
            characterSet.add(numbers);
        }

        return characterSet;

    }
}
