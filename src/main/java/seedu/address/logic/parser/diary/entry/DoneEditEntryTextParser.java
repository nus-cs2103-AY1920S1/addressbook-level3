package seedu.address.logic.parser.diary.entry;

import seedu.address.logic.commands.diary.entry.DoneEditEntryTextCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link DoneEditEntryTextCommand}.
 * It has no parsing logic, and simply returns an instance of {@link DoneEditEntryTextCommand}.
 */
public class DoneEditEntryTextParser implements Parser<DoneEditEntryTextCommand> {
    @Override
    public DoneEditEntryTextCommand parse(String userInput) throws ParseException {
        return new DoneEditEntryTextCommand();
    }
}
