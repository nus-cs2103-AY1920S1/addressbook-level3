package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CHART;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.exercise.logic.commands.statistic.StatsCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.property.Date;

/**
 * Parses input arguments and creates a new StatsCommand object.
 */
public class StatsCommandParser implements Parser<StatsCommand> {

    public static final String MESSAGE_INVALID_DATE_RANGE = "Start date and end date are too far apart. "
            + "Maximum range is 31 days.";
    public static final String MESSAGE_INVALID_COMMAND = "Both start date and end date must be present";

    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * and returns a StatsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_CHART,
                PREFIX_START_DATE, PREFIX_END_DATE);

        if (!argMultimap.arePrefixesPresent(PREFIX_CATEGORY, PREFIX_CHART) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        }

        String category = ParserUtil.parseStatisticCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        String chart = ParserUtil.parseChart(argMultimap.getValue(PREFIX_CHART).get());
        Date startDate = null;
        Date endDate = null;

        //date provided
        if (argMultimap.arePrefixesPresent(PREFIX_START_DATE, PREFIX_END_DATE)) { //both dates present

            startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
            endDate = ParserUtil.parseEndDate(startDate, argMultimap.getValue(PREFIX_END_DATE).get());

            int numberOfDaysApart = Date.numberOfDaysBetween(startDate, endDate);

            if (numberOfDaysApart > 31) {
                throw new ParseException(MESSAGE_INVALID_DATE_RANGE);
            }

        } else if (argMultimap.arePrefixesPresent(PREFIX_START_DATE)
                && !argMultimap.arePrefixesPresent(PREFIX_END_DATE)) { //only start date present

            throw new ParseException(MESSAGE_INVALID_COMMAND);

        } else if (argMultimap.arePrefixesPresent(PREFIX_END_DATE)
                && !argMultimap.arePrefixesPresent(PREFIX_START_DATE)) { //only end date present

            throw new ParseException(MESSAGE_INVALID_COMMAND);

        }

        return new StatsCommand(chart, category, startDate, endDate);
    }
}
