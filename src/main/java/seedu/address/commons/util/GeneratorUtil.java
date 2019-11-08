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

        while (!meetUserRequirement(password.toString(), lower, upper, num, special)) {
            password.setLength(0);
            //generate random character from characterSet for 8 times.
            for (int k = 0; k < length; k++) {
                int ranArrayChooser = randomNumGen.nextInt(characterSet.size());
                int randomLetterIndex = randomNumGen.nextInt(characterSet.get(ranArrayChooser).length - 1);
                password.append(characterSet.get(ranArrayChooser)[randomLetterIndex]);
            }
        }

        return password.toString();
    }

    /**
     * Checks if the randomly generated password string meets the user's configuration.
     *
     * @param password the current randomly generated password string.
     * @param hasLower the option to include lower case alphabets.
     * @param hasUpper the option to include upper case alphabets.
     * @param hasNum the option to include numbers.
     * @param hasSpecial the option to include lower case alphabets.
     * @return if the user configuration requirement is met.
     */
    private static boolean meetUserRequirement(String password, boolean hasLower, boolean hasUpper,
                                                      boolean hasNum, boolean hasSpecial) {
        if (password.length() == 0) {
            return false;
        }
        if (hasNum && !password.matches("(?=.*[0-9]).*")) {
            return false;
        }
        if (hasLower && !password.matches("(?=.*[a-z]).*")) {
            return false;
        }
        if (hasUpper && !password.matches("(?=.*[A-Z]).*")) {
            return false;
        }
        if (hasSpecial && !password.matches("(?=.*[^a-z A-Z0-9]).*")) {
            return false;
        }
        return true;
    }

    /**
     * Appends the character sets based on user configuration.
     *
     * @param hasLower the option to include lower case alphabets.
     * @param hasUpper the option to include upper case alphabets.
     * @param hasSpecial the option to include lower case alphabets.
     * @param hasNum the option to include numbers.
     * @return the character set based on user configuration.
     */
    private static ArrayList<String[]> setCharacterSet(boolean hasLower,
                                                       boolean hasUpper, boolean hasSpecial, boolean hasNum) {

        ArrayList<String[]> characterSet = new ArrayList<>();

        if (hasLower) {
            characterSet.add(lowAlpha);
        }
        if (hasUpper) {
            characterSet.add(highAlpha);
        }
        if (hasSpecial) {
            characterSet.add(specialChars);
        }
        if (hasNum) {
            characterSet.add(numbers);
        }

        return characterSet;

    }
}
