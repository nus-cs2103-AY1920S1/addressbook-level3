package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINUTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECONDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEARS;

import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import seedu.address.logic.commands.BinItemExpiryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BinItemExpiryCommand
 */
public class BinItemExpiryCommandParser implements Parser<BinItemExpiryCommand> {

    private static Prefix[] prefixes = { PREFIX_SECONDS, PREFIX_MINUTES, PREFIX_HOURS, PREFIX_DAYS, PREFIX_MONTHS,
        PREFIX_YEARS };

    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean onlyOnePrefixValue(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).filter(prefix -> argumentMultimap.getValue(prefix).isPresent()).count() == 1;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RestoreCommand
     * and returns a RestoreCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BinItemExpiryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);

        if (!areAnyPrefixesPresent(argMultimap, prefixes) || !onlyOnePrefixValue(argMultimap, prefixes)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BinItemExpiryCommand.MESSAGE_USAGE));
        }

        int timeToLiveAmount;
        ChronoUnit timeToLiveUnit;
        if (argMultimap.getValue(PREFIX_SECONDS).isPresent()) {
            timeToLiveAmount = ParserUtil.parsePositiveInt(argMultimap.getValue(PREFIX_SECONDS).get());
            timeToLiveUnit = ChronoUnit.SECONDS;
        } else if (argMultimap.getValue(PREFIX_MINUTES).isPresent()) {
            timeToLiveAmount = ParserUtil.parsePositiveInt(argMultimap.getValue(PREFIX_MINUTES).get());
            timeToLiveUnit = ChronoUnit.MINUTES;
        } else if (argMultimap.getValue(PREFIX_HOURS).isPresent()) {
            timeToLiveAmount = ParserUtil.parsePositiveInt(argMultimap.getValue(PREFIX_HOURS).get());
            timeToLiveUnit = ChronoUnit.HOURS;
        } else if (argMultimap.getValue(PREFIX_DAYS).isPresent()) {
            timeToLiveAmount = ParserUtil.parsePositiveInt(argMultimap.getValue(PREFIX_DAYS).get());
            timeToLiveUnit = ChronoUnit.DAYS;
        } else if (argMultimap.getValue(PREFIX_MONTHS).isPresent()) {
            timeToLiveAmount = ParserUtil.parsePositiveInt(argMultimap.getValue(PREFIX_MONTHS).get());
            timeToLiveUnit = ChronoUnit.MONTHS;
        } else if (argMultimap.getValue(PREFIX_YEARS).isPresent()) {
            timeToLiveAmount = ParserUtil.parsePositiveInt(argMultimap.getValue(PREFIX_YEARS).get());
            timeToLiveUnit = ChronoUnit.YEARS;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BinItemExpiryCommand.MESSAGE_USAGE));
        }

        return new BinItemExpiryCommand(timeToLiveAmount, timeToLiveUnit);
    }
}
