package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.TabCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

public class TabCommandParser implements Parser<TabCommand> {

    public static final String MESSAGE_INVALID_INDEX = "Index is neither 1, 2 nor 3.";

    public TabCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE), pe);
        }

        if (index.getOneBased() < 0 || index.getOneBased() > 0) {
            ParseException pe = new ParseException(MESSAGE_INVALID_INDEX);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE), pe);
        }

        return new TabCommand(index);
    }
}
