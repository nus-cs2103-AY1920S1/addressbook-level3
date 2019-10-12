package seedu.address.logic.parser;

import seedu.address.logic.commands.AddPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.password.Description;
import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;

/**
 * Parses input arguments and creates a new AddCommandPassword object
 */
public class AddPasswordCommandParser implements Parser<AddPasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPasswordCommand
     * and returns an AddPasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPasswordCommand parse(String userInput) throws ParseException {
        Description description = new Description("123");
        PasswordValue passwordValue = new PasswordValue("456");
        Username username = new Username("789");
        Password password = new Password(description, username, passwordValue);
        return new AddPasswordCommand(password);
    }
}
