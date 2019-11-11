package seedu.weme.logic.parser.commandparser.createcommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_X_COORDINATE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_Y_COORDINATE;

import java.util.Optional;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.createcommand.TextMoveCommand;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ArgumentMultimap;
import seedu.weme.logic.parser.util.ArgumentTokenizer;
import seedu.weme.logic.parser.util.ParserUtil;

/**
 * Parses input arguments and creates a new TextMoveCommand object
 */
public class TextMoveCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the TextMoveCommand
     * and returns an TextMoveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TextMoveCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args,
                PREFIX_X_COORDINATE, PREFIX_Y_COORDINATE);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TextMoveCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TextMoveCommand.MESSAGE_USAGE), pe);
        }

        Optional<Float> changeInX = Optional.empty();
        Optional<Float> changeInY = Optional.empty();
        Optional<String> xSupplied = argMultimap.getValue(PREFIX_X_COORDINATE);
        Optional<String> ySupplied = argMultimap.getValue(PREFIX_Y_COORDINATE);
        if (xSupplied.isPresent()) {
            changeInX = Optional.of(ParserUtil.parseCoordinateChange(xSupplied.get()));
        }
        if (ySupplied.isPresent()) {
            changeInY = Optional.of(ParserUtil.parseCoordinateChange(ySupplied.get()));
        }

        return new TextMoveCommand(index, changeInX, changeInY);
    }

}
