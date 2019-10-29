package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.TogglePanelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PanelName;

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
