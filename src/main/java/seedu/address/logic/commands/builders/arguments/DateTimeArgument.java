package seedu.address.logic.commands.builders.arguments;

import java.util.function.Consumer;

import seedu.address.logic.parser.DateTimeParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.events.DateTime;

/**
 * Represents a command argument of type DateTime.
 */
public class DateTimeArgument extends Argument<DateTime> {

    public DateTimeArgument(String description, Consumer<DateTime> builder) {
        super(description, builder);
    }

    @Override
    DateTime parse(String userInput) throws ParseException {
        return new DateTimeParser().parse(userInput);
    }
}
