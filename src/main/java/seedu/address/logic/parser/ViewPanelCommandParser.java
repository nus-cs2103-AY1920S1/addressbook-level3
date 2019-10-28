package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ui.ViewPanelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.panel.PanelName;

/**
 * Parses input arguments to create a new ViewPanelCommand.
 */
public class ViewPanelCommandParser implements Parser<ViewPanelCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewPanelCommand
     * and returns an ViewPanelCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public ViewPanelCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        if (userInput.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewPanelCommand.MESSAGE_USAGE));
        }

        if (!PanelName.isValidPanelName(userInput.trim())) {
            throw new ParseException(String.format(PanelName.MESSAGE_NAME_FORMAT, userInput));
        }

        return new ViewPanelCommand(new PanelName(userInput.trim()));
    }
}
