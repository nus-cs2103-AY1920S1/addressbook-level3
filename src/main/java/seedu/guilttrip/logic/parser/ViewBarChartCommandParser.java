package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_PERIOD;

import seedu.guilttrip.logic.commands.statisticscommands.ViewBarChartCommand;
import seedu.guilttrip.logic.commands.statisticscommands.ViewTableCommand;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Date;

/**
 * Parses input arguments and creates a new ViewBarChartCommand object
 */
public class ViewBarChartCommandParser implements Parser<ViewBarChartCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewBarChartCommand
     * and returns a ViewBarChartCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewBarChartCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERIOD);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTableCommand.MESSAGE_USAGE));
        }

        Date dateToParse = null;

        if (argMultimap.getValue(PREFIX_PERIOD).isPresent()) {
            dateToParse = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_PERIOD).get());
        }


        return new ViewBarChartCommand(dateToParse);
    }
}
