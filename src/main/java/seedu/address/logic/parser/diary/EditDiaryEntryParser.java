package seedu.address.logic.parser.diary;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.diary.EditDiaryEntryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link EditDiaryEntryCommand}.
 */
public class EditDiaryEntryParser implements Parser<EditDiaryEntryCommand> {
    @Override
    public EditDiaryEntryCommand parse(String userInput) throws ParseException {
        return new EditDiaryEntryCommand();
    }
}
