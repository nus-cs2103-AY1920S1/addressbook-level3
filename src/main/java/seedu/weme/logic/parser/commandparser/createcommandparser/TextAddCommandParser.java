package seedu.weme.logic.parser.commandparser.createcommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_X_COORDINATE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_Y_COORDINATE;

import seedu.weme.logic.commands.createcommand.TextAddCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ArgumentMultimap;
import seedu.weme.logic.parser.util.ArgumentTokenizer;
import seedu.weme.logic.parser.util.ParserUtil;
import seedu.weme.model.template.Coordinates;
import seedu.weme.model.template.MemeText;

/**
 * Parses input arguments and creates a new TextAddCommand object
 */
public class TextAddCommandParser implements Parser<TextAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TextAddCommand
     * and returns an TextAddCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TextAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_X_COORDINATE, PREFIX_Y_COORDINATE);

        if (!argMultimap.arePrefixesPresent(PREFIX_X_COORDINATE, PREFIX_Y_COORDINATE)
            || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TextAddCommand.MESSAGE_USAGE));
        }

        String text = argMultimap.getPreamble();
        Coordinates coordinates = ParserUtil.parseCoordinates(
            argMultimap.getValue(PREFIX_X_COORDINATE).get(),
            argMultimap.getValue((PREFIX_Y_COORDINATE)).get());

        return new TextAddCommand(new MemeText(text, coordinates));
    }

}

