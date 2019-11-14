package seedu.address.logic.parser.diary;

import seedu.address.logic.commands.diary.EnterDiaryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Abstraction for parsing the given arguments in to a {@link seedu.address.logic.commands.diary.EnterDiaryCommand}.
 */
public class EnterDiaryParser implements Parser<EnterDiaryCommand> {
    @Override
    public EnterDiaryCommand parse(String args) throws ParseException {
        return new EnterDiaryCommand();
    }
}
