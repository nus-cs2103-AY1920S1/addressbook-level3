package seedu.address.diaryfeature.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.logic.commands.DeleteCommand;
import seedu.address.diaryfeature.logic.commands.ErrorCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.IndexException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<Command> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
            if(index.getOneBased() < 1) {
                throw new IndexException("You can't delete something less than 0!!!");
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        } catch (IndexException ex) {
            return new ErrorCommand(ex);
        }
        return new DeleteCommand(index);
    }

}
