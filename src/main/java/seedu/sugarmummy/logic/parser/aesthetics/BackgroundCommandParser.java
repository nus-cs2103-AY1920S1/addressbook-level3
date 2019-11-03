package seedu.sugarmummy.logic.parser.aesthetics;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_BACKGROUND_REPEAT;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_BACKGROUND_SIZE;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_USE_ONLY_ONE_FONT_COLOUR_PREFIX;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_BG_REPEAT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_BG_SIZE;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FONT_COLOR;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FONT_COLOUR;

import java.util.Optional;

import seedu.sugarmummy.logic.commands.aesthetics.BackgroundCommand;
import seedu.sugarmummy.logic.commands.aesthetics.FontColourCommand;
import seedu.sugarmummy.logic.parser.ArgumentMultimap;
import seedu.sugarmummy.logic.parser.ArgumentTokenizer;
import seedu.sugarmummy.logic.parser.Parser;
import seedu.sugarmummy.logic.parser.ParserUtil;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.aesthetics.Background;

/**
 * Parses input arguments and creates a new FontColourCommand object
 */
public class BackgroundCommandParser implements Parser<BackgroundCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BackgroundCommandCommand and returns an
     * BackgroundCommandCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BackgroundCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.isEmpty()) {
            return new BackgroundCommand();
        }
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BG_SIZE, PREFIX_BG_REPEAT,
                        PREFIX_FONT_COLOR, PREFIX_FONT_COLOUR);

        FontColourCommand fontColourCommand = null;

        if (argMultimap.getValue(PREFIX_FONT_COLOR).isPresent()) {
            if (argMultimap.getValue(PREFIX_FONT_COLOUR).isPresent()
                    || !argMultimap.isUniquePrefix(PREFIX_FONT_COLOR)) {
                throw new ParseException(MESSAGE_USE_ONLY_ONE_FONT_COLOUR_PREFIX);
            }
            fontColourCommand = (new FontColourCommandParser())
                    .parse(argMultimap.getValue(PREFIX_FONT_COLOR).get());
        }

        if (argMultimap.getValue(PREFIX_FONT_COLOUR).isPresent()) {
            if (argMultimap.getValue(PREFIX_FONT_COLOR).isPresent()
                    || !argMultimap.isUniquePrefix(PREFIX_FONT_COLOUR)) {
                throw new ParseException(MESSAGE_USE_ONLY_ONE_FONT_COLOUR_PREFIX);
            }
            fontColourCommand = (new FontColourCommandParser())
                    .parse(argMultimap.getValue(PREFIX_FONT_COLOUR).get());
        }

        String backgroundArg = argMultimap.getPreamble();

        Background background = ParserUtil.parseBackground(backgroundArg);
        background.setDominantColour();

        if (background.isBackgroundColour() && !argMultimap.isEmpty()) {
            if (!argMultimap.containsOnlyPrefixes(PREFIX_FONT_COLOR, PREFIX_FONT_COLOUR)) {
                throw new ParseException(String.format(MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT,
                    BackgroundCommand.MESSAGE_USAGE));
            }
        }

        Optional<String> bgSize;
        Optional<String> bgRepeat;

        if ((bgSize = argMultimap.getValue(PREFIX_BG_SIZE)).isPresent()) {
            if (Background.isValidBackgroundSize(bgSize.get())) {
                throw new ParseException(String.format(MESSAGE_INVALID_BACKGROUND_SIZE,
                        BackgroundCommand.MESSAGE_USAGE));
            }
            bgSize = bgSize.get().equals("") ? Optional.of("auto") : bgSize;
        }

        if ((bgRepeat = argMultimap.getValue(PREFIX_BG_REPEAT)).isPresent()) {
            if (Background.isValidBackgroundRepeat(bgRepeat.get())) {
                throw new ParseException(String.format(MESSAGE_INVALID_BACKGROUND_REPEAT,
                        BackgroundCommand.MESSAGE_USAGE));
            }
            bgRepeat = bgRepeat.get().equals("") ? Optional.of("repeat") : bgRepeat;
        }

        String bgSizeToString = bgSize.orElse("");
        String bgRepeatToString = bgRepeat.orElse("");

        background.setBgSize(bgSizeToString);
        background.setBgRepeat(bgRepeatToString);

        return fontColourCommand == null
                ? new BackgroundCommand(background)
                : new BackgroundCommand(background, fontColourCommand);
    }

}
