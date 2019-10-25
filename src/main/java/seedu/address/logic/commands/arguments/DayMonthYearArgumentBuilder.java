package seedu.address.logic.commands.arguments;

import java.util.function.Consumer;

import seedu.address.model.CalendarDate;

/**
 * Represents an ArgumentBuilder responsible for building {@link DateTimeArgument}
 */
public class DayMonthYearArgumentBuilder extends ArgumentBuilder<CalendarDate> {

    DayMonthYearArgumentBuilder(String description, Consumer<CalendarDate> promise) {
        super(description, promise);
    }

    @Override
    Argument<CalendarDate> argumentBuild() {
        return new DayMonthYearArgument(this);
    }
}
