package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.FullViewCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;

/**
 * Parses input argument to generate a {@code FullViewCommand}
 */
public class FullViewCommandParser implements Parser<FullViewCommand> {

    /**
     * Parses the given context into a full view command
     * @param args the input from the user
     * @return new full view command
     */
    @Override
    public FullViewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FullViewCommand(index);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FullViewCommand.MESSAGE_USAGE), e);
        }
    }
}
