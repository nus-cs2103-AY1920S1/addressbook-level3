package seedu.address.model.help;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class SecondaryCommand {


/**
 * Represents a Help Object's secondary command in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSecondaryCommand(String)}
 */

    public static final String MESSAGE_CONSTRAINTS = "Please use the 'help' command to check the available commands";

    public final String value;

    private static ArrayList<String> commandList = new ArrayList<String>(
            Arrays.asList("help",
            "add",
            "clear",
            "delete",
            "edit",
            "exit",
            "find",
            "list"));

    /**
     * Constructs an {@code SecondaryCommand}.
     *
     * @param secondaryCommand A valid command.
     */

    public SecondaryCommand(String secondaryCommand) {
        requireNonNull(secondaryCommand);
        checkArgument(isValidSecondaryCommand(secondaryCommand), MESSAGE_CONSTRAINTS);
        value = secondaryCommand;
    }

    /**
     * Returns if a given string is a valid secondary command.
     */
    public static boolean isValidSecondaryCommand(String test) {

        for (String cmd : commandList)
            if (test.equals(cmd)) {
                return true;
            }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SecondaryCommand // instanceof handles nulls
                && value.equals(((SecondaryCommand) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}