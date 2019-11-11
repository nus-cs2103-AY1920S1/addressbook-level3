package seedu.address.transaction.logic.parser;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.logic.commands.Command;
import seedu.address.transaction.logic.commands.exception.NotANumberException;
import seedu.address.transaction.logic.parser.exception.ParseException;
import seedu.address.transaction.model.exception.NoSuchPersonException;

/**
 * Parses input arguments and creates a new Command object
 */
public interface CommandParserWithPersonModel {

    /**
     * Parses the given {@code String} of arguments in the context of commands that require the person package's Model
     * and returns a Command object for execution.
     * @throws ParseException If the user input does not conform the expected format
     * @throws NoSuchPersonException If the user inputs a person's name that is not in the AddressBook
     * @throws NotANumberException If the user inputs something that is not a number when a number is expected
     */
    Command parse(String userInput, CheckAndGetPersonByNameModel personModel) throws ParseException,
            NoSuchPersonException, NotANumberException;
}
