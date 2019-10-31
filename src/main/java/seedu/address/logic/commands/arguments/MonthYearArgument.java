package seedu.address.logic.commands.arguments;

import java.util.function.Consumer;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CalendarDate;

/**
 * Represents a command argument of type CalendarDate with Month and Year only.
 */
public class MonthYearArgument extends Argument<CalendarDate> {

    MonthYearArgument(ArgumentBuilder<CalendarDate> builder) {
        super(builder);
    }

    public static MonthYearArgumentBuilder newBuilder(String description, Consumer<CalendarDate> promise) {
        return new MonthYearArgumentBuilder(description, promise);
    }

    @Override
    CalendarDate parse(String userInput) throws ParseException {
        return CalendarDate.fromMonthYearString(userInput);
    }
}
