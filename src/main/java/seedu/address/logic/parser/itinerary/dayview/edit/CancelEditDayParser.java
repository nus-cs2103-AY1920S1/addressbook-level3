package seedu.address.logic.parser.itinerary.dayview.edit;

import seedu.address.logic.commands.itinerary.days.edit.CancelEditDayCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class CancelEditDayParser implements Parser<CancelEditDayCommand> {
    @Override
    public CancelEditDayCommand parse(String userInput) throws ParseException {
        return new CancelEditDayCommand();
    }
}
