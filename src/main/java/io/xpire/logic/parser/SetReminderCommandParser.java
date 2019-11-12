package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;

import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.SetReminderCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.ReminderThreshold;

/**
 * Parses input arguments and creates a new SetReminderCommand object.
 */
public class SetReminderCommandParser implements Parser<SetReminderCommand> {
    public static final String MESSAGE_INVALID_REMINDER_THRESHOLD = "%s is not a valid reminder threshold.";

    private static final int ITEM_INDEX = 0;
    private static final int THRESHOLD_INDEX = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the SetReminderCommand
     * and returns a SetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SetReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        ReminderThreshold threshold;

        if (args.split(SEPARATOR).length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetReminderCommand.MESSAGE_USAGE));
        }
        index = ParserUtil.parseIndex(args.split(SEPARATOR)[ITEM_INDEX]);
        threshold = ParserUtil.parseReminderThreshold(args.split(SEPARATOR)[THRESHOLD_INDEX]);

        return new SetReminderCommand(index, threshold);
    }
}
