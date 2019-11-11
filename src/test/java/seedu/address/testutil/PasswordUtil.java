package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORDVALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.AddPasswordCommand;
import seedu.address.model.password.Password;


/**
 * A utility class for Password.
 */
public class PasswordUtil {
    /**
     * Returns an add command string for adding the {@code password}.
     */
    public static String getAddCommand(Password password) {
        return AddPasswordCommand.COMMAND_WORD + " " + getPasswordDetails(password);
    }

    /**
     * Returns the part of command string for the given {@code password}'s details.
     */
    public static String getPasswordDetails(Password password) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_USERNAME + password.getUsername().value + " ");
        sb.append(PREFIX_DESCRIPTION + password.getPasswordDescription().value + " ");
        sb.append(PREFIX_PASSWORDVALUE + password.getNonEncryptedPasswordValue() + " ");
        return sb.toString();
    }
}
