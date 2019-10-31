package seedu.address.logic.commands.arguments;

import java.util.function.Consumer;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CalendarDate;

/**
 * Represents a command argument of type DateTime.
 */
public class DayMonthYearArgument extends Argument<CalendarDate> {

    DayMonthYearArgument(ArgumentBuilder<CalendarDate> builder) {
        super(builder);
    }

    public static DayMonthYearArgumentBuilder newBuilder(String description, Consumer<CalendarDate> promise) {
        return new DayMonthYearArgumentBuilder(description, promise);
    }

    @Override
    CalendarDate parse(String userInput) throws ParseException {
        return CalendarDate.fromDayMonthYearString(userInput);
    }
}
