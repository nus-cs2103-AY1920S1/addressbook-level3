package seedu.guilttrip.logic.parser.statscommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_ARGUMENT_FORMAT;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_PERIOD;

import java.util.List;

import seedu.guilttrip.logic.commands.statisticscommands.ViewTableCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Date;

/**
 * Parses input arguments and creates a new ViewTableCommand object.
 */
public class ViewTableCommandParser implements Parser<ViewTableCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewTableCommand
     * and returns a ViewTableCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewTableCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERIOD);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTableCommand.MESSAGE_USAGE));
        }

        List<Date> dateToParse = null;

        if (argMultimap.getValue(PREFIX_PERIOD).isPresent()) {
            dateToParse = ParserUtil.parseStartAndEndPeriod(argMultimap.getValue(PREFIX_PERIOD).get());
            if (dateToParse.size() > 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_ARGUMENT_FORMAT,
                        ViewTableCommand.MESSAGE_FAILURE));
            }
        }


        return new ViewTableCommand(dateToParse);
    }

}
