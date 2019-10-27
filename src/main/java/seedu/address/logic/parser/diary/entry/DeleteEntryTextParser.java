package seedu.address.logic.parser.diary.entry;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.entry.DeleteEntryTextCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link DeleteEntryTextCommand}.
 */
public class DeleteEntryTextParser implements Parser<DeleteEntryTextCommand> {
    @Override
    public DeleteEntryTextCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new DeleteEntryTextCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEntryTextCommand.MESSAGE_USAGE), pe);
        }
    }
}
