package seedu.address.logic.commands.arguments;

import java.util.function.Consumer;

import seedu.address.model.CalendarDate;

/**
 * Represents an ArgumentBuilder responsible for building {@link MonthYearArgument}
 */
public class MonthYearArgumentBuilder extends ArgumentBuilder<CalendarDate> {

    MonthYearArgumentBuilder(String description, Consumer<CalendarDate> promise) {
        super(description, promise);
    }

    @Override
    Argument<CalendarDate> argumentBuild() {
        return new MonthYearArgument(this);
    }
}
