package seedu.address.logic.parser.diary.entry;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.diary.entry.AppendEntryTextCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link AppendEntryTextCommand}.
 * The leading white space that after the command word of {@link AppendEntryTextCommand} is stripped.
 */
public class AppendEntryTextParser implements Parser<AppendEntryTextCommand> {
    @Override
    public AppendEntryTextCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        return new AppendEntryTextCommand(userInput.stripLeading());
    }
}
