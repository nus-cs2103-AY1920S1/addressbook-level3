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
    public static final String MESSAGE_AT_LEAST_ONE_FIELD_CHECKED = "Input Error. At least one character field needs to be true\n";
    public static final String MESSAGE_CONSTRAINTS_LENGTH = "Length of password should be positive "
            + "and at least of length 4 to 25";
    public static final String MESSAGE_CONSTRAINTS_BOOLEAN = "All characters sets are included by default.\n" +
            "You are only required to input CHARACTER_SET/\"false\" for fields that you wish to customise.";

    private int length;
    private boolean lower;
    private boolean upper;
    private boolean num;
    private boolean special;
    private PasswordGeneratorDescriptor configuration;

    public GeneratePasswordCommand(PasswordGeneratorDescriptor configuration) {
        super();
        this.configuration = configuration;
        this.length = configuration.getLength();
        this.lower = configuration.getLower();
        this.upper = configuration.getUpper();
        this.num = configuration.getNum();
        this.special = configuration.getSpecial();
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
        String password = GeneratorUtil.generateRandomPassword(length, lower, upper, num, special);
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
        private boolean lower;
        private boolean upper;
        private boolean num;
        private boolean special;

        public PasswordGeneratorDescriptor(int length, boolean lower, boolean upper, boolean num, boolean special) {
            this.length = length;
            this.lower = lower;
            this.upper = upper;
            this.num = num;
            this.special = special;
        }

        public PasswordGeneratorDescriptor() {
            this.length = 10;
            this.lower = true;
            this.upper = true;
            this.num = true;
            this.special = true;
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

        public static PasswordGeneratorDescriptor getDefaultConfiguration() {
            return new PasswordGeneratorDescriptor();
        }
    }
}

