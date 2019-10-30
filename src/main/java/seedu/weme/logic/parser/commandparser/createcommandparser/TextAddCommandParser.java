package seedu.weme.logic.parser.commandparser.createcommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_COLOR;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_SIZE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_STYLE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_X_COORDINATE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_Y_COORDINATE;
import static seedu.weme.model.template.MemeTextColor.DEFAULT_MEME_TEXT_COLOR;
import static seedu.weme.model.template.MemeTextSize.DEFAULT_MEME_TEXT_SIZE;
import static seedu.weme.model.template.MemeTextStyle.DEFAULT_MEME_TEXT_STYLE;

import java.util.Set;

import seedu.weme.logic.commands.createcommand.TextAddCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ArgumentMultimap;
import seedu.weme.logic.parser.util.ArgumentTokenizer;
import seedu.weme.logic.parser.util.ParserUtil;
import seedu.weme.model.template.Coordinates;
import seedu.weme.model.template.MemeText;
import seedu.weme.model.template.MemeTextColor;
import seedu.weme.model.template.MemeTextSize;
import seedu.weme.model.template.MemeTextStyle;

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
            ArgumentTokenizer.tokenize(args,
                PREFIX_X_COORDINATE, PREFIX_Y_COORDINATE, PREFIX_COLOR, PREFIX_STYLE, PREFIX_SIZE);

        if (!argMultimap.arePrefixesPresent(PREFIX_X_COORDINATE, PREFIX_Y_COORDINATE)
            || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TextAddCommand.MESSAGE_USAGE));
        }

        String text = argMultimap.getPreamble();
        Coordinates coordinates = ParserUtil.parseCoordinates(
            argMultimap.getValue(PREFIX_X_COORDINATE).get(),
            argMultimap.getValue((PREFIX_Y_COORDINATE)).get());

        MemeTextColor color;
        if (argMultimap.getValue(PREFIX_COLOR).isEmpty()) {
            color = DEFAULT_MEME_TEXT_COLOR;
        } else {
            color = ParserUtil.parseMemeTextColor(argMultimap.getValue(PREFIX_COLOR).get());
        }

        Set<MemeTextStyle> styles = ParserUtil.parseMemeTextStyles(argMultimap.getAllValues(PREFIX_STYLE));
        if (styles.isEmpty()) {
            styles.add(DEFAULT_MEME_TEXT_STYLE);
        }

        MemeTextSize size;
        if (argMultimap.getValue(PREFIX_SIZE).isEmpty()) {
            size = DEFAULT_MEME_TEXT_SIZE;
        } else {
            size = ParserUtil.parseMemeTextSize(argMultimap.getValue(PREFIX_SIZE).get());
        }

        return new TextAddCommand(new MemeText(text, coordinates, color, styles, size));
    }

}

