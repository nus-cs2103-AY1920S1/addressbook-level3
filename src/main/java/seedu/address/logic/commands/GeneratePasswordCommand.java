package seedu.address.logic.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.security.SecureRandom;
import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Generates a random password value based on user input argument.
 */
public class GeneratePasswordCommand extends Command {
    public static final String COMMAND_WORD = "generate";
    public static final String MESSAGE_SUCCESS = "Password Generated: %s" + "\n"
                                                            + "Password has been copied to your clipboard!";
    public static final String MESSAGE_REQUIRE_CHECK_AT_LEAST_ONE = "At least one field "
                                                            + "is required to be true to generate password. ";
    public static final String MESSAGE_REQUIRE_INTEGER_LENGTH = "Please enter a whole number for password length";
    public static final String MESSAGE_USAGE = "e.g. generate length/8 lower/true upper/true num/true special/true";
    public static final String MESSAGE_REQUIRE_POSITIVE_LENGTH = "Please enter a valid password length. "
                                                            + "It has to be a non-zero positive integer.";

    private int length;
    private boolean lower;
    private boolean upper;
    private boolean num;
    private boolean special;

    public GeneratePasswordCommand(int length, boolean lower, boolean upper, boolean num, boolean special) {
        this.length = length;
        this.lower = lower;
        this.upper = upper;
        this.num = num;
        this.special = special;
    }

    /**
     * Returns a CommandResult containing a randomly generated password.
     * @param model the manager model
     * @return randomly generated password
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (length < 0) {
            throw new CommandException(MESSAGE_REQUIRE_POSITIVE_LENGTH + "\n"
                                        + MESSAGE_USAGE);
        }
        String password = generateRandomPassword();
        copyToClipboard(password, null);
        return new CommandResult(String.format(MESSAGE_SUCCESS, password));
    }

    /**
     * Generates random password value based on user input.
     * @return a random password value string
     */
    private String generateRandomPassword() {
        // Attributes:
        SecureRandom randomNumGen;
        int passwordLength;
        ArrayList<String[]> characterSet;
        String[] lowAlpha = new String[] {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z"};
        String[] highAlpha = new String[] {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};
        String[] specialChars = new String[] {
            "~", "`", "!", "@", "#", "$", "%", "^", "&", "*",
            "(", ")", "-", "_", "+", "=", "[", "{", "]", "}",
            "|", "\\", "'", "\"", ";", ":", "?", "/", ".", ">",
            "<", ","};
        String[] numbers = new String[] {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        // init
        characterSet = setCharacterSet(lowAlpha, highAlpha, specialChars, numbers);
        passwordLength = length;
        randomNumGen = new SecureRandom();
        StringBuilder password = new StringBuilder();

        //generate random character from characterSet for 8 times.
        for (int k = 0; k < passwordLength; k++) {
            int ranArrayChooser = randomNumGen.nextInt(characterSet.size());
            int randomLetterIndex = randomNumGen.nextInt(characterSet.get(ranArrayChooser).length - 1);
            password.append(characterSet.get(ranArrayChooser)[randomLetterIndex]);
        }

        return password.toString();
    }

    private ArrayList<String[]> setCharacterSet(String[] lowAlpha,
                                                String[] highAlpha, String[] specialChars, String[] numbers) {

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

    private void requireNonNull(Model model) {
    }

    /**
     * Copies generated password to user clipboard
     * @param textToCopy the password to copy
     * @param user the user local
     */
    public void copyToClipboard(String textToCopy, ClipboardOwner user) {
        //Create & get the clipboard from the computer
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        //Make the text selected
        Transferable selectedText = new StringSelection(textToCopy);

        //Copy & Write the selected text to the user's clipboard
        clipboard.setContents(selectedText, user);
    }
    /**
     * Stores the details of password requirements.
     */
    public static class PasswordGeneratorDescriptor {
        private int length;
        private boolean lower;
        private boolean upper;
        private boolean num;
        private boolean special;

        public PasswordGeneratorDescriptor() {}

        /**
         * Returns true if at least one requirement is true.
         */
        public boolean isAnyFieldChecked() {
            return lower == true || upper == true || num == true || special == true;
        }

        public void setLength(int length) {
            this.length = length;
        }
        public void setLower(boolean lower) {
            this.lower = lower;
        }
        public void setUpper(boolean upper) {
            this.upper = upper;
        }
        public void setNum(boolean num) {
            this.num = num;
        }
        public void setSpecial(boolean special) {
            this.special = special;
        }

        public int getLength() {
            return length;
        }
        public boolean getLower() {
            return lower;
        }
        public boolean getUpper() {
            return upper;
        }
        public boolean getNum() {
            return num;
        }
        public boolean getSpecial() {
            return special;
        }
    }
}

