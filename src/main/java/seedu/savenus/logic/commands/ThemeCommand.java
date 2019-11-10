package seedu.savenus.logic.commands;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;

//@@author robytanama
/**
 * Allows user to change the themes using the Command Line.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String EXAMPLE_USAGE =
            "There are only two themes available: light or dark.\n"
            + "Example usage: " + COMMAND_WORD + " dark";
    public static final String MESSAGE_SUCCESS_DARK = "You have successfully changed the theme to dark theme!";
    public static final String MESSAGE_SUCCESS_LIGHT = "You have successfully changed the theme to light theme!";
    public static final String MULTIPLE_THEMES_ERROR = "You can only enter one theme at a time!";
    public static final String NO_FIELDS_ERROR = "You did not choose any theme.";
    public static final String UNIDENTIFIED_THEME_ERROR = "No such theme exists!";
    public static final String DARK_THEME = "dark";
    public static final String LIGHT_THEME = "light";

    private String theme;

    /**
     * Creates a {@code ThemeCommand}.
     * @param theme The desired theme by the user.
     */
    public ThemeCommand(String theme) {
        this.theme = theme;
    }

    /**
     * Used to obtain the theme in String.
     * @return The theme.
     */
    public String getTheme() {
        return theme;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // To make the theme not case sensitive
        theme = theme.toLowerCase();

        switch(theme) {
        case DARK_THEME:
            return new CommandResult(MESSAGE_SUCCESS_DARK);
        case LIGHT_THEME:
            return new CommandResult(MESSAGE_SUCCESS_LIGHT);
        default:
            throw new CommandException(UNIDENTIFIED_THEME_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && getTheme().equals(((ThemeCommand) other).getTheme()));
    }
}
