package seedu.address.logic.parser.itinerary.dayview;

import seedu.address.logic.commands.itinerary.days.EnterCreateDayCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class EnterCreateDayParser implements Parser<EnterCreateDayCommand> {
    @Override
    public EnterCreateDayCommand parse(String userInput) throws ParseException {
        return new EnterCreateDayCommand();
    }
}
