package seedu.address.logic.parser.diary;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.diary.AppendDiaryEntryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link AppendDiaryEntryCommand}.
 */
public class AppendDiaryEntryParser implements Parser<AppendDiaryEntryCommand> {
    @Override
    public AppendDiaryEntryCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        return new AppendDiaryEntryCommand(userInput);
    }
}
