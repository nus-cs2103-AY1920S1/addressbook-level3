package seedu.address.logic.parser.diary;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.EnterDiaryCommand;
import seedu.address.logic.commands.trips.EnterTripCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
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
