package seedu.address.diaryfeature.logic.parser;


import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.logic.commands.DeleteCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        Index index;
        index = ParserUtil.parseIndex(args);
        return new DeleteCommand(index);

    }
}
