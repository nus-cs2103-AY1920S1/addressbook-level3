//@@author shutingy
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_THEME;
import static seedu.address.logic.commands.SetThemeCommand.DARKTHEME;
import static seedu.address.logic.commands.SetThemeCommand.LIGHTTHEME;
import static seedu.address.logic.commands.SetThemeCommand.PINKTHEME;

import seedu.address.logic.commands.SetThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetThemeCommand object
 */
public class SetThemeCommandParser implements Parser<SetThemeCommand> {

    @Override
    public SetThemeCommand parse(String userInput) throws ParseException {
        String styleSheet = null;
        String color = null;
        String trimedInput = userInput.trim();
        if (trimedInput.equals("light")) {
            styleSheet = LIGHTTHEME;
        } else if (trimedInput.equals("dark")) {
            styleSheet = DARKTHEME;
        } else if (trimedInput.equals("pink")) {
            styleSheet = PINKTHEME;
        }
        if (styleSheet == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_THEME));
        }

        return new SetThemeCommand(styleSheet);
    }
}
