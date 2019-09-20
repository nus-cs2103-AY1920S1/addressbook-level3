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

    @Override
    public SetReminderCommand parse(String args) throws ParseException {
        System.out.println(args);
        requireNonNull(args);
        Index index;

        try {
            index = ParserUtil.parseIndex(args.split("|")[0]);
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetReminderCommand.MESSAGE_USAGE), e);
        }

        int threshold = Integer.parseInt(args.split("|")[1]);

        return new SetReminderCommand(index, threshold);
    }
}
