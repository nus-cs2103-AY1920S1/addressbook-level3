package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.DeleteCacheCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCacheCommand object
 */
public class DeleteCacheCommandParser implements Parser<DeleteCacheCommand> {

    @Override
    public DeleteCacheCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCacheCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCacheCommand.MESSAGE_USAGE), pe);
        }
    }
}
