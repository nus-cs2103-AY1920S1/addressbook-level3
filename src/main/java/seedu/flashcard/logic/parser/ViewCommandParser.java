package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.ViewCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;

/**
 * Parses input argument to generate a {@code ViewCommand}
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given context into a view command
     * @param args the input from the user
     * @return new view command
     */
    @Override
    public ViewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewCommand(index);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), e);
        }
    }
}
