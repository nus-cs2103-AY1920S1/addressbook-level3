package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDICATOR;

import java.util.stream.Stream;

import seedu.address.logic.commands.DisplayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.visual.DisplayFormat;
import seedu.address.model.visual.DisplayIndicator;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DisplayCommandParser implements Parser<DisplayCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayCommand
     * and returns a DisplayCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_INDICATOR, PREFIX_FORMAT);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDICATOR, PREFIX_FORMAT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE));
        }

        DisplayIndicator displayIndicator = null;
        displayIndicator = ParserUtil.parseDisplayIndicator(argMultimap.getValue(PREFIX_INDICATOR).get());

        DisplayFormat displayFormat = null;
        displayFormat = ParserUtil.parseDisplayFormat(argMultimap.getValue(PREFIX_FORMAT).get());

        return new DisplayCommand(displayIndicator, displayFormat);
    }
}
