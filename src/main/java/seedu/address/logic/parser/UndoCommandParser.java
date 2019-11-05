package seedu.address.logic.parser;

import seedu.address.commons.core.step.Step;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoCommand parse(String args) throws ParseException {
        try {
            Step step = ParserUtil.parseStep(args);
            return new UndoCommand(step);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE), pe);
        }
    }

}
