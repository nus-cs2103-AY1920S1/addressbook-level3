package seedu.address.model.help;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a Help Object's secondary command in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSecondaryCommand(String)}
 */

public class SecondaryCommand {

    public static final String MESSAGE_CONSTRAINTS = "Please use the 'help' command to check the available commands";

    private static ArrayList<String> commandList = new ArrayList<String>(
            Arrays.asList("help",
                    "add_contact",
                    "add_claim",
                    "add_income",
                    "delete_contact",
                    "delete_income",
                    "edit_contact",
                    "edit_income",
                    "exit",
                    "check",
                    "goto",
                    "reject",
                    "budget",
                    "clear",
                    "approve",
                    "sort",
                    "reverse",
                    "delete_shortcut"));

    public final String value;


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
     * Returns the list of commands.
     */

    public static ArrayList<String> getCommandList() {
        return commandList;
    }


    /**
     * Returns if a given string is a valid secondary command.
     */
    public static boolean isValidSecondaryCommand(String test) {

        for (String cmd : commandList) {
            if (test.equals(cmd)) {
                return true;
            }
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
