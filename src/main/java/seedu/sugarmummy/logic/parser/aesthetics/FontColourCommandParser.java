package seedu.sugarmummy.logic.parser.aesthetics;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_USE_ONLY_ONE_BACKGROUND_PREFIX;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_BACKGROUND;
import static seedu.sugarmummy.logic.parser.ParserUtil.parseColour;

import seedu.sugarmummy.logic.commands.aesthetics.BackgroundCommand;
import seedu.sugarmummy.logic.commands.aesthetics.FontColourCommand;
import seedu.sugarmummy.logic.parser.ArgumentMultimap;
import seedu.sugarmummy.logic.parser.ArgumentTokenizer;
import seedu.sugarmummy.logic.parser.Parser;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.aesthetics.Colour;

/**
 * Parses input arguments and creates a new FontColourCommand object
 */
public class FontColourCommandParser implements Parser<FontColourCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FontColourCommand and returns an
     * FontColourCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FontColourCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.isEmpty()) {
            return new FontColourCommand();
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BACKGROUND);

        BackgroundCommand backgroundCommand = null;

        if (argMultimap.getValue(PREFIX_BACKGROUND).isPresent()) {
            if (!argMultimap.isUniquePrefix(PREFIX_BACKGROUND)) {
                throw new ParseException(MESSAGE_USE_ONLY_ONE_BACKGROUND_PREFIX);
            }
            backgroundCommand = (new BackgroundCommandParser())
                    .parse(argMultimap.getValue(PREFIX_BACKGROUND).get());
        }

        Colour fontColour = parseColour(argMultimap.getPreamble());
        return backgroundCommand == null
                ? new FontColourCommand(fontColour)
                : new FontColourCommand(fontColour, backgroundCommand);
    }

}
