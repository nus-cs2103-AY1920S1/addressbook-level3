package seedu.moolah.logic.parser.statistics;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.logic.commands.statistics.StatsTrendCommand;
import seedu.moolah.logic.commands.statistics.StatsTrendDescriptor;
import seedu.moolah.logic.parser.ArgumentMultimap;
import seedu.moolah.logic.parser.ArgumentTokenizer;
import seedu.moolah.logic.parser.Parser;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.Prefix;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.general.Timestamp;
import seedu.moolah.model.statistics.Mode;

/**
 * Parses input arguments and creates a new StatsTrendCommand object
 */
public class StatsTrendCommandParser implements Parser<StatsTrendCommand> {

    public static final List<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableList(List.of(PREFIX_MODE));

    public static final List<Prefix> OPTIONAL_PREFIXES = Collections.unmodifiableList(List.of(
            PREFIX_START_DATE, PREFIX_END_DATE));


    /**
     * Parses the given {@code String} of arguments in the context of the StatsTrendCommand
     * and returns an StatsTrendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatsTrendCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_MODE);

        if (!argMultimap.arePrefixesPresent(PREFIX_MODE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsTrendCommand.MESSAGE_USAGE));
        }

        if (argMultimap.hasRepeatedPrefixes(PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_MODE)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        StatsTrendDescriptor statsTrendDescriptor = new StatsTrendDescriptor();

        Timestamp startDate = null;
        Timestamp endDate = null;


        Optional<String> startDateString = argMultimap.getValue(PREFIX_START_DATE);
        Optional<String> endDateString = argMultimap.getValue(PREFIX_END_DATE);

        boolean isStartPresent = startDateString.isPresent();
        boolean isEndPresent = endDateString.isPresent();
        Mode mode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_MODE).get());

        if (isStartPresent && isEndPresent) {
            startDate = ParserUtil.parseTimestamp(startDateString.get());
            endDate = ParserUtil.parseTimestamp(endDateString.get());
            statsTrendDescriptor.setStartDate(startDate);
            statsTrendDescriptor.setEndDate(endDate);
            if (statsTrendDescriptor.isEndBeforeStart()) {
                throw new ParseException(Messages.MESSAGE_CONSTRAINTS_END_DATE);
            }
        } else if (isStartPresent) {
            startDate = ParserUtil.parseTimestamp(startDateString.get());
            statsTrendDescriptor.setStartDate(startDate);
        } else if (isEndPresent) {
            endDate = ParserUtil.parseTimestamp(endDateString.get());
            statsTrendDescriptor.setEndDate(endDate);
        }
        statsTrendDescriptor.setMode(mode.isBudgetMode());
        return new StatsTrendCommand(statsTrendDescriptor);
    }

}



