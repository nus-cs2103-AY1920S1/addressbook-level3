package seedu.eatme.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.SaveTodoCommand;
import seedu.eatme.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SaveTodoCommand object
 */
public class SaveTodoCommandParser implements Parser<SaveTodoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveTodoCommand
     * and returns a SaveTodoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SaveTodoCommand parse(String args) throws ParseException {
        try {
            requireNonNull(args);
            Index index = ParserUtil.parseIndex(args);
            return new SaveTodoCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveTodoCommand.MESSAGE_USAGE), pe);
        }
    }

}
