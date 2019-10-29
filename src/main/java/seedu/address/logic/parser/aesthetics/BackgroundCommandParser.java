package seedu.address.logic.parser.aesthetics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BACKGROUND_REPEAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BACKGROUND_SIZE;

import java.util.Optional;

import seedu.address.logic.commands.aesthetics.BackgroundCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.aesthetics.Background;

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
            ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_BG_SIZE, CliSyntax.PREFIX_BG_REPEAT);

        String backgroundArg = argMultimap.getPreamble();

        Background background = ParserUtil.parseBackground(backgroundArg);

        if (background.isBackgroundColour() && !argMultimap.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT,
                BackgroundCommand.MESSAGE_USAGE));
        }

        Optional<String> bgSize;
        Optional<String> bgRepeat;

        if ((bgSize = argMultimap.getValue(CliSyntax.PREFIX_BG_SIZE)).isPresent()) {
            if (!Background.isValidBackgroundSize(bgSize.get())) {
                throw new ParseException(String.format(MESSAGE_INVALID_BACKGROUND_SIZE,
                    BackgroundCommand.MESSAGE_USAGE));
            }
            bgSize = bgSize.get().equals("") ? Optional.of("auto") : bgSize;
        }

        if ((bgRepeat = argMultimap.getValue(CliSyntax.PREFIX_BG_REPEAT)).isPresent()) {
            if (!Background.isValidBackgroundRepeat(bgRepeat.get())) {
                throw new ParseException(String.format(MESSAGE_INVALID_BACKGROUND_REPEAT,
                    BackgroundCommand.MESSAGE_USAGE));
            }
            bgRepeat = bgRepeat.get().equals("") ? Optional.of("repeat") : bgRepeat;
        }

        String bgSizeToString = bgSize.orElse("");
        String bgRepeatToString = bgRepeat.orElse("");

        background.setBgSize(bgSizeToString);
        background.setBgRepeat(bgRepeatToString);

        return new BackgroundCommand(background);
    }

}
