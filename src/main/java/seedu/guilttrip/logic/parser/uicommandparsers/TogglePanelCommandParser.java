package seedu.guilttrip.logic.parser.uicommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.guilttrip.logic.commands.uicommands.TogglePanelCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.ui.util.PanelName;

/**
 * Parses input arguments and creates a new TogglePanelCommand object.
 */
public class TogglePanelCommandParser implements Parser<TogglePanelCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TogglePanelCommand
     * and returns a TogglePanelCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TogglePanelCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        PanelName panelName;

        try {
            panelName = ParserUtil.parsePanelName(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TogglePanelCommand.MESSAGE_USAGE), pe);
        }

        return new TogglePanelCommand(panelName);
    }
}
