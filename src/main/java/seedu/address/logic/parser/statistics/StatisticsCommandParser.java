package seedu.address.logic.parser.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_SAVE_STATS_FILE_ERROR;
import static seedu.address.commons.util.FileUtil.isValidFileName;

import java.util.HashMap;
import java.util.stream.Stream;

import seedu.address.logic.commands.statistics.StatisticsAddCommand;
import seedu.address.logic.commands.statistics.StatisticsCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.statistics.Statistics;

/**
 * Parses input arguments and creates a new StatisticsCommand object
 */
public class StatisticsCommandParser implements Parser<StatisticsCommand> {

    private DataParser dataParser = new ExcelParser();

    /**
     * Parses the given {@code String} of arguments and returns a
     * StatisticsAddCommand object for execution if the command is valid.
     *
     * @param args the statistics command entered by the user.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public StatisticsCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, CliSyntax.PREFIX_FILEPATH, CliSyntax.PREFIX_PRINT);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_FILEPATH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsAddCommand.MESSAGE_USAGE));
        }

        String filePath = argMultimap.getValue(CliSyntax.PREFIX_FILEPATH).orElse("");
        String printableName = argMultimap.getValue(CliSyntax.PREFIX_PRINT).orElse("");
        if ((arePrefixesPresent(argMultimap, CliSyntax.PREFIX_PRINT) && !isValidFileName(printableName))) {
            throw new ParseException(MESSAGE_SAVE_STATS_FILE_ERROR);
        }
        HashMap<String, HashMap<String, Double>> data = dataParser.parseFile(filePath);

        return new StatisticsAddCommand(new Statistics(data), printableName);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
