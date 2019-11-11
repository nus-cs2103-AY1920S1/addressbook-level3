package seedu.address.logic.commands.arguments;

import java.util.function.Consumer;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CalendarDate;

/**
 * Represents a command argument of type CalendarDay.
 */
public class CalendarDayArgument extends Argument<CalendarDate> {

    private CalendarDayArgument(ArgumentBuilder<CalendarDate> builder) {
        super(builder);
    }

    public static Builder newBuilder(String description, Consumer<CalendarDate> promise) {
        return new Builder(description, promise);
    }

    @Override
    CalendarDate parse(String userInput) throws ParseException {
        return CalendarDate.fromDayMonthYearString(userInput);
    }

    /**
     * Represents an ArgumentBuilder responsible for building {@link CalendarDayArgument}
     */
    private static class Builder extends ArgumentBuilder<CalendarDate> {

        private Builder(String description, Consumer<CalendarDate> promise) {
            super(description, promise);
        }

        @Override
        Argument<CalendarDate> argumentBuild() {
            return new CalendarDayArgument(this);
        }
    }
}
