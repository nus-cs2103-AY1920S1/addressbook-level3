package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_GROUPING;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_INTERVAL;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.logic.commands.DisplayStatsCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.statistics.formats.ExpenseGrouping;
import seedu.billboard.model.statistics.formats.StatisticsFormat;
import seedu.billboard.model.statistics.formats.StatisticsFormatOptions;

/**
 * Parses input arguments and creates a new DisplayStatsCommand object
 */
public class DisplayStatsCommandParser implements Parser<DisplayStatsCommand> {

    /**
     * Parses the user input of arguments in the context of the DisplayStatsCommand and returns a DisplayStatsCommand
     * object for execution. Valid parameters depend on the type of statistics chosen to be displayed
     * @param userInput User input string
     * @return DisplayStatsCommand object representing the chosen statistics together with specific options.
     * @throws ParseException if the statistic chosen does not exist.
     */
    @Override
    public DisplayStatsCommand parse(String userInput) throws ParseException {
        String formatString = userInput.trim().split("\\s")[0];

        StatisticsFormat formatChosen = StatisticsFormat.formatFromName(formatString)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayStatsCommand.MESSAGE_USAGE)));

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, formatChosen.getOptionsPrefixes());

        DateInterval newInterval = argMultimap.getValue(PREFIX_INTERVAL)
                .flatMap(DateInterval::intervalFromName)
                .orElse(null);

        ExpenseGrouping expenseGrouping = argMultimap.getValue(PREFIX_GROUPING)
                .flatMap(ExpenseGrouping::groupingFromName)
                .orElse(null);

        StatisticsFormatOptions newOptions = StatisticsFormatOptions.withOptions(newInterval, expenseGrouping);
        return new DisplayStatsCommand(formatChosen, newOptions);
    }
}
