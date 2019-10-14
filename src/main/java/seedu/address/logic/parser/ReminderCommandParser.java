package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.food.NameContainsCloseExpiryDatePredicate;

import java.util.stream.Stream;

public class ReminderCommandParser {

    public ReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        }

        int r = Integer.parseInt(argMultimap.getValue(PREFIX_REMINDER).get().trim());

        return new ReminderCommand(new NameContainsCloseExpiryDatePredicate(r));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
