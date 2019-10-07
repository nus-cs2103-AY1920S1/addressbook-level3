package seedu.address.logic.commands.arguments;

import seedu.address.model.events.DateTime;

public class DateTimeArgumentBuilder extends ArgumentBuilder<DateTime> {

    DateTimeArgumentBuilder(String description) {
        super(description);
    }

    @Override
    Argument<DateTime> argumentBuild() {
        return new DateTimeArgument(this);
    }
}
