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
     *
     * @return the random password value string
     */
    public static String generateRandomPassword(int length, boolean hasLower,
                                                boolean hasUpper, boolean hasNum, boolean hasSpecial) {
        ArrayList<String[]> characterSetsToUse = setCharacterSet(hasLower, hasUpper, hasSpecial, hasNum);
        SecureRandom rand = new SecureRandom();
        StringBuilder password = new StringBuilder();

        while (!meetUserRequirement(password.toString(), hasLower, hasUpper, hasNum, hasSpecial)) {
            password.setLength(0);
            for (int k = 0; k < length; k++) {
                int ranArrayChooser = rand.nextInt(characterSetsToUse.size());
                int randomLetterIndex = rand.nextInt(characterSetsToUse.get(ranArrayChooser).length - 1);
                password.append(characterSetsToUse.get(ranArrayChooser)[randomLetterIndex]);
            }
        }

        return password.toString();
    }

    /**
     * Returns true if the randomly generated password string meets the user's configuration requirement.
     *
     * @param password the current randomly generated password string.
     * @param hasLower the option to include lower case alphabets.
     * @param hasUpper the option to include upper case alphabets.
     * @param hasNum the option to include numbers.
     * @param hasSpecial the option to include lower case alphabets.
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
        assert hasLower == true || hasUpper == true || hasSpecial == true || hasNum == true; //Precondition
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
