package seedu.address.logic.parser.flashcard;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.flashcard.DeleteFlashcardCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteFlashcardCommand object
 */
public class DeleteFlashcardCommandParser implements Parser<DeleteFlashcardCommand> {

    private static final Logger logger = LogsCenter.getLogger(DeleteFlashcardCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFlashcardCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            logger.info("Parsing delete command of args: " + args);
            return new DeleteFlashcardCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFlashcardCommand.MESSAGE_USAGE), pe);
        }
    }

}
