package seedu.address.logic.parser.diary;

import seedu.address.logic.commands.diary.DoneEditDiaryEntryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link DoneEditDiaryEntryCommand}.
 * It has no parsing logic, and simply returns an instance of {@link DoneEditDiaryEntryCommand}.
 */
public class DoneEditDiaryEntryParser implements Parser<DoneEditDiaryEntryCommand> {
    @Override
    public DoneEditDiaryEntryCommand parse(String userInput) throws ParseException {
        return new DoneEditDiaryEntryCommand();
    }
}
