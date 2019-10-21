package seedu.address.transaction.logic;

import seedu.address.person.model.Model;
import seedu.address.transaction.commands.Command;

/**
 * Parses input arguments and creates a new Command object
 */
public interface CommandParserWithPersonModel {

    /**
     * Parses the given {@code String} of arguments in the context of commands that require the person package's Model
     * and returns a Command object for execution.
     * @throws Exception If the user input does not conform the expected format
     */
    Command parse(String userInput, Model personModel) throws Exception;
}
