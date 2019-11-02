package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ARGUMENT_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;

import java.util.List;

import seedu.address.logic.commands.ViewStatisticsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Date;

/**
 * Parses input arguments and creates a new HistoryCommand object
 */
public class StatisticsCommandParser implements Parser<ViewStatisticsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HistoryCommand
     * and returns a HistoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewStatisticsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERIOD);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStatisticsCommand.MESSAGE_USAGE));
        }

        List<Date> dateToParse = null;

        if (argMultimap.getValue(PREFIX_PERIOD).isPresent()) {
            dateToParse = ParserUtil.parseStartAndEndPeriod(argMultimap.getValue(PREFIX_PERIOD).get());
            if (dateToParse.size() > 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_ARGUMENT_FORMAT,
                        ViewStatisticsCommand.MESSAGE_FAILURE));
            }
        }


        return new ViewStatisticsCommand(dateToParse);
    }

}
