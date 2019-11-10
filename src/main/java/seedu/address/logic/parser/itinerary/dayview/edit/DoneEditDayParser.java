package seedu.address.logic.parser.itinerary.dayview.edit;

import seedu.address.logic.commands.itinerary.days.edit.DoneEditDayCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for {@link DoneEditDayCommand}.
 */
public class DoneEditDayParser implements Parser<DoneEditDayCommand> {
    @Override
    public DoneEditDayCommand parse(String userInput) throws ParseException {
        return new DoneEditDayCommand();
    }
}
