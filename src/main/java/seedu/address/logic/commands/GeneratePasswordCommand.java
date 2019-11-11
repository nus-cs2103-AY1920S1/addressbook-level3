package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LENGTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UPPER;

import seedu.address.commons.util.ClipboardUtil;
import seedu.address.commons.util.GeneratorUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Generates a random password value based on user input argument.
 */
public class GeneratePasswordCommand extends Command {
    public static final String COMMAND_WORD = "generate";
    public static final String MESSAGE_SUCCESS = "Password Generated: %s" + "\n"
                                                            + "Password has been copied to your clipboard!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a random password. \n"
            + "OPTIONAL Parameters: \n"
            + PREFIX_LENGTH + "LENGTH "
            + PREFIX_LOWER + "FALSE "
            + PREFIX_UPPER + "FALSE "
            + PREFIX_NUM + "FALSE "
            + PREFIX_SPECIAL + "FALSE ";
    public static final String MESSAGE_AT_LEAST_ONE_FIELD_CHECKED = "Input Error. "
            + "At least one character field needs to be true\n";
    public static final String MESSAGE_CONSTRAINTS_LENGTH_MIN =
            "Length of password should be positive and at least length 4.";
    public static final String MESSAGE_CONSTRAINTS_LENGTH_MAX = "Length of password is at most 25.";
    public static final String MESSAGE_CONSTRAINTS_LENGTH = "Invalid length. "
            + "Please input a positive, integer between 4 and 25.";
    public static final String MESSAGE_CONSTRAINTS_BOOLEAN = "All character sets are included by default.\n"
            + "You are only required to input CHARACTER_SET/false for fields that you wish to exclude.";

    private int length;
    private boolean hasLower;
    private boolean hasUpper;
    private boolean hasNum;
    private boolean hasSpecial;
    private PasswordGeneratorDescriptor configuration;

    public GeneratePasswordCommand(PasswordGeneratorDescriptor configuration) {
        super();
        this.configuration = new PasswordGeneratorDescriptor(configuration);
        this.length = configuration.getLength();
        this.hasLower = configuration.getLower();
        this.hasUpper = configuration.getUpper();
        this.hasNum = configuration.getNum();
        this.hasSpecial = configuration.getSpecial();
    }

    /**
     * Returns true if the user input length is valid.
     * @param lengthNum the user input length.
     */
    public static boolean isValidGeneratePasswordLength(int lengthNum) {
        return lengthNum > 3 && lengthNum <= 25;
    }

    /**
     * Returns a CommandResult containing a randomly generated password.
     * @param model the manager model
     * @return randomly generated password
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        isValid(configuration);
        String password = GeneratorUtil.generateRandomPassword(length, hasLower, hasUpper, hasNum, hasSpecial);
        ClipboardUtil.copyToClipboard(password, null);
        return new CommandResult(String.format(MESSAGE_SUCCESS, password));
    }

    /**
     * Returns true if at least one of the character fields are checked true.
     *
     * @param description the user configured {@code PasswordGeneratorDescriptor}.
     * @throws CommandException if the description input does not have any character field checked.
     */
    private void isValid(PasswordGeneratorDescriptor description) throws CommandException {
        if (!(description.getLower() == true || description.getUpper() == true || description.getNum() == true
                || description.getSpecial() == true)) {
            throw new CommandException(MESSAGE_AT_LEAST_ONE_FIELD_CHECKED);
        }
    }

    /**
     * Stores the details of password requirements.
     */
    public static class PasswordGeneratorDescriptor {
        private int length;
        private boolean hasLower;
        private boolean hasUpper;
        private boolean hasNum;
        private boolean hasSpecial;

        public PasswordGeneratorDescriptor() {
            this.length = 10;
            this.hasLower = true;
            this.hasUpper = true;
            this.hasNum = true;
            this.hasSpecial = true;
        }

        /**
         * Copy constructor.
         */
        public PasswordGeneratorDescriptor(PasswordGeneratorDescriptor toCopy) {
            setLength(toCopy.getLength());
            setLower(toCopy.getLower());
            setUpper(toCopy.getUpper());
            setNum(toCopy.getNum());
            setSpecial(toCopy.getSpecial());
        }

        public void setLength(int length) {
            this.length = length;
        }
        public void setLower(boolean hasLower) {
            this.hasLower = hasLower;
        }
        public void setUpper(boolean hasUpper) {
            this.hasUpper = hasUpper;
        }
        public void setNum(boolean hasNum) {
            this.hasNum = hasNum;
        }
        public void setSpecial(boolean hasSpecial) {
            this.hasSpecial = hasSpecial;
        }

        public int getLength() {
            return length;
        }
        public boolean getLower() {
            return hasLower;
        }
        public boolean getUpper() {
            return hasUpper;
        }
        public boolean getNum() {
            return hasNum;
        }
        public boolean getSpecial() {
            return hasSpecial;
        }

        /**
         * Creates and returns a {@code PasswordGeneratorDescriptor} with the default settings.
         */
        public static PasswordGeneratorDescriptor getDefaultConfiguration() {
            return new PasswordGeneratorDescriptor();
        }
    }
}

