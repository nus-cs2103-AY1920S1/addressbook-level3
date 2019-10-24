package seedu.address.logic.commands.arguments;

import java.util.function.Consumer;

import seedu.address.model.events.DateTime;

/**
 * Represents an ArgumentBuilder responsible for building {@link DateTimeArgument}
 */
public class DateTimeArgumentBuilder extends ArgumentBuilder<DateTime> {

    DateTimeArgumentBuilder(String description, Consumer<DateTime> promise) {
        super(description, promise);
    }

    @Override
    Argument<DateTime> argumentBuild() {
        return new DateTimeArgument(this);
    }
}
