package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import io.xpire.commons.util.StringUtil;
import io.xpire.logic.commands.CheckCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.ExpiringSoonPredicate;
import io.xpire.model.item.ReminderThresholdExceededPredicate;

/**
 * Parses input arguments and creates a new CheckCommand object
 */
public class CheckCommandParser implements Parser<CheckCommand> {

    public static final int MAX_VALUE = 36500;

    /**
     * Parses the given {@code String} of argument in the context of the CheckCommand
     * and returns a CheckCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public CheckCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new CheckCommand(new ReminderThresholdExceededPredicate());
        }

        if (!StringUtil.isNonNegativeInteger(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));
        }

        if (StringUtil.isExceedingMaxValue(trimmedArgs, MAX_VALUE)) {
            throw new ParseException(CheckCommand.MESSAGE_EXCEEDED_MAX);
        }
        int days = Integer.parseInt(trimmedArgs);
        return new CheckCommand(new ExpiringSoonPredicate(days), days);
    }
}
