package seedu.address.logic.parser.diary;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.CreateDiaryEntryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link CreateDiaryEntryCommand}.
 * The input must contain a valid integer index of the day to create the diary entry for.
 */
public class CreateDiaryEntryParser implements Parser<CreateDiaryEntryCommand> {
    @Override
    public CreateDiaryEntryCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        Index index;

        try {
            index = ParserUtil.parseIndex(userInput.trim());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateDiaryEntryCommand.MESSAGE_USAGE), pe);
        }

        return new CreateDiaryEntryCommand(index);
    }
}
