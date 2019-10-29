//@@author shutingy
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_THEME;

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
            color = "black";
            styleSheet = "view/LightTheme.css";
        } else if (trimedInput.equals("dark")) {
            color = "white";
            styleSheet = "view/DarkTheme.css";
        }
        if (styleSheet.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_THEME));
        }

        return new SetThemeCommand(styleSheet, color);
    }
}
