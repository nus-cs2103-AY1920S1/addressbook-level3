package seedu.address.logic.parser.questionparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.questioncommands.DeleteQuestionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteQuestionCommand object
 */
public class DeleteQuestionCommandParser implements Parser<DeleteQuestionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteQuestionCommand
     * and returns a DeleteQuestionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteQuestionCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteQuestionCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteQuestionCommand.MESSAGE_USAGE), pe);
        }
    }

}
