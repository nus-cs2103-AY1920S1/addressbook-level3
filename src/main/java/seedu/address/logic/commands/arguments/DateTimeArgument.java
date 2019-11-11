package seedu.address.logic.commands.arguments;

import java.util.function.Consumer;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DateTime;

/**
 * Represents a command argument of type DateTime.
 */
public class DateTimeArgument extends Argument<DateTime> {

    DateTimeArgument(ArgumentBuilder<DateTime> builder) {
        super(builder);
    }

    public static DateTimeArgumentBuilder newBuilder(String description, Consumer<DateTime> promise) {
        return new DateTimeArgumentBuilder(description, promise);
    }

    @Override
    DateTime parse(String userInput) throws ParseException {
        return DateTime.fromUserString(userInput);
    }
}
