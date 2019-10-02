package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SetReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetReminderCommand object.
 */
public class SetReminderCommandParser implements Parser<SetReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetReminderCommand
     * and returns a SetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SetReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;

        try {
            index = ParserUtil.parseIndex(args.split("\\|")[0]);

        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetReminderCommand.MESSAGE_USAGE), e);
        }

        String threshold = args.split("\\|")[1];

        return new SetReminderCommand(index, threshold);
    }
}
