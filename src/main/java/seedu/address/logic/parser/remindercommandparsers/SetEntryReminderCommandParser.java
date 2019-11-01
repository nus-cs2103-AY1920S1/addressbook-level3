package seedu.address.logic.parser.remindercommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.remindercommands.SetEntryReminderCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Description;
/**
 * Parses input argument and creates a new SetEntryReminderCommand object.
 */
public class SetEntryReminderCommandParser implements Parser<SetEntryReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditExpenseCommand
     * and returns an EditExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetEntryReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESC, PREFIX_TYPE, PREFIX_INDEX, PREFIX_AMOUNT, PREFIX_PARAM);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESC, PREFIX_TYPE, PREFIX_INDEX, PREFIX_AMOUNT, PREFIX_PARAM))
        {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        Description desc = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get());
        String type = argMultimap.getValue(PREFIX_TYPE).get().toLowerCase();
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        double amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()).value;
        TemporalUnit temporalUnit = getTemporalUnit(argMultimap.getValue(PREFIX_PARAM).get());
        return new SetEntryReminderCommand(desc, type, index, (int) amount, temporalUnit);
    }
    private TemporalUnit getTemporalUnit(String unit) throws ParseException {
        if (unit.toLowerCase().contains("day")) {
            return ChronoUnit.valueOf("DAYS");
        } else if (unit.toLowerCase().contains("week")) {
            return ChronoUnit.valueOf("WEEKS");
        } else if (unit.toLowerCase().contains("month")) {
            return ChronoUnit.valueOf("MONTHS");
        } else if (unit.toLowerCase().contains("year")) {
            return ChronoUnit.valueOf("YEARS");
        } else {
            throw new ParseException("Unit time not supported");
        }
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
