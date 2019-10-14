package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.DeleteCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments to generate a {@Code DeleteCommand}
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given context into a delete command
     * @param args the input from the user
     * @return new delete command
     */
    @Override
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), e);
        }
    }
}
