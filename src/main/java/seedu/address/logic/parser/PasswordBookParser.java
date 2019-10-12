package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class PasswordBookParser {

    public Command parseCommand(String userInput) throws ParseException {
        return new AddPasswordCommandParser().parse(userInput);
    }
}
