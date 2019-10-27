package seedu.address.logic.parser.diary;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.DeleteDiaryEntryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link DeleteDiaryEntryCommand}.
 */
public class DeleteDiaryEntryParser implements Parser<DeleteDiaryEntryCommand> {
    @Override
    public DeleteDiaryEntryCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new DeleteDiaryEntryCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDiaryEntryCommand.MESSAGE_USAGE), pe);
        }
    }
}
