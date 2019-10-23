package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.ArrayList;

import seedu.address.logic.commands.StatisticsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Date;

/**
 * Parses input arguments and creates a new HistoryCommand object
 */
public class StatisticsCommandParser implements Parser<StatisticsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HistoryCommand
     * and returns a HistoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatisticsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_PERIOD);

        if (!argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
        }

        String type = argMultimap.getValue(PREFIX_TYPE).get().toLowerCase();
        if (!argMultimap.getValue(PREFIX_PERIOD).isPresent()) {
            ArrayList<Date> period = ParserUtil.parsePeriod(argMultimap.getValue(PREFIX_PERIOD).get());
            return new StatisticsCommand(type, period);
        }

        return new StatisticsCommand(type);
    }
}
