package seedu.ichifund.logic.parser.repeater;

import java.util.Arrays;
import java.util.Collections;

import seedu.ichifund.logic.commands.repeater.FindRepeaterCommand;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.repeater.RepeaterDescriptionPredicate;

/**
 * Parses input arguments and creates a new FindRepeaterCommand object
 */
public class FindRepeaterCommandParser implements Parser<FindRepeaterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindRepeaterCommand
     * and returns a FindRepeaterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindRepeaterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new FindRepeaterCommand(new RepeaterDescriptionPredicate(Collections.emptyList()));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindRepeaterCommand(new RepeaterDescriptionPredicate(Arrays.asList(nameKeywords)));
    }

}
