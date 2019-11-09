package seedu.guilttrip.logic.parser.uicommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.guilttrip.logic.commands.uicommands.ChangeFontCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.ui.util.FontName;

/**
 * Parses input argument and creates a new ChangeFontCommand object.
 */
public class ChangeFontCommandParser implements Parser<ChangeFontCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of ChangeFontCommand and
     * returns a ChangeFontCommand object for execution.
     * @throws ParseException if the user does not conform to the expected format
     */
    public ChangeFontCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        FontName fontName;

        try {
            fontName = ParserUtil.parseFontName(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeFontCommand.MESSAGE_USAGE), pe);
        }

        return new ChangeFontCommand(fontName);
    }

}
