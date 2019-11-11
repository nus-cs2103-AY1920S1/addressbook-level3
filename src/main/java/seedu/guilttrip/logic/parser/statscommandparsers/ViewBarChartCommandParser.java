package seedu.guilttrip.logic.parser.statscommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_PERIOD;

import seedu.guilttrip.logic.commands.statisticscommands.ViewBarChartCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewBarChartCommand.MESSAGE_USAGE));
        }

        Date dateToParse = null;

        if (argMultimap.getValue(PREFIX_PERIOD).isPresent()) {
            String[] checkSize = argMultimap.getValue(PREFIX_PERIOD).get().split(",");
            if (checkSize.length >= 2) {
                throw new ParseException(ViewBarChartCommand.MESSAGE_FAILURE);
            }
            dateToParse = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_PERIOD).get());
        }


        return new ViewBarChartCommand(dateToParse);
    }
}
