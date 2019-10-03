package io.xpire.logic.parser;

import io.xpire.logic.commands.CheckCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.ExpiringSoonPredicate;
import io.xpire.model.item.ReminderThresholdExceededPredicate;

/**
 * Parses input arguments and creates a new CheckCommand object
 */
public class CheckCommandParser implements Parser<CheckCommand> {

    /**
     * Parses the given {@code String} of argument in the context of the CheckCommand
     * and returns a CheckCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CheckCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new CheckCommand(new ReminderThresholdExceededPredicate());
        } else {
            return new CheckCommand(new ExpiringSoonPredicate(trimmedArgs));
        }
    }
}
