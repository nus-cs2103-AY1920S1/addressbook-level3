package seedu.address.logic.parser;

import seedu.address.logic.commands.SendReminderCommand;

import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class SendReminderCommandParser implements Parser<SendReminderCommand> {
    @Override
    public SendReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, SendReminderCommand.PREFIX_DURATION);

        if (!arePrefixesPresent(argMultimap, SendReminderCommand.PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SendReminderCommand.MESSAGE_USAGE));
        }

        int duration = Integer.parseInt(argMultimap.getValue(SendReminderCommand.PREFIX_DURATION).get());

        return new SendReminderCommand(duration);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
