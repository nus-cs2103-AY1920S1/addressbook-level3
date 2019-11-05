package seedu.address.transaction.logic.parser;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.logic.commands.Command;

/**
 * Parses input arguments and creates a new Command object
 */
public interface CommandParserWithPersonModel {

    /**
     * Parses the given {@code String} of arguments in the context of commands that require the person package's Model
     * and returns a Command object for execution.
     * @throws Exception If the user input does not conform the expected format
     */
    Command parse(String userInput, CheckAndGetPersonByNameModel personModel) throws Exception;
}
