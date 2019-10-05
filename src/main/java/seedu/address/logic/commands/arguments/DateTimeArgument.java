package seedu.address.logic.commands.arguments;

import seedu.address.logic.parser.DateTimeParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.events.DateTime;

/**
 * Represents a command argument of type DateTime.
 */
public class DateTimeArgument extends CommandArgument<DateTime> {

    public DateTimeArgument(String description, boolean required) {
        super(description, required);
    }

    @Override
    public DateTime parse(String userInput) throws ParseException {
        return new DateTimeParser().parse(userInput);
    }
}
