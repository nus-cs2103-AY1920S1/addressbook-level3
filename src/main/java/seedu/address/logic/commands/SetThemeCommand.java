//@@author shutingy
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Change the theme of the GUI
 */
public class SetThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String THEME_FORMAT = "view/%s.css";

    public static final String DARKTHEME = "DarkTheme";
    public static final String LIGHTTHEME = "LightTheme";
    public static final String PINKTHEME = "PinkTheme";
    public static final String BLUETHEME = "BlueTheme";
    public static final String HACKERTHEME = "HackerTheme";
    public static final String NUSTHEME = "NUSTheme";

    public static final String MESSAGE_SUCCESS = "Theme changed to %s";
    public static final String MESSAGE_SAME_STYLESHEET = "The current theme is already %s";

    private String styleSheet;
    /**
     * Creates a SetThemeCommand to change the theme of the gui
     */
    public SetThemeCommand(String styleSheet) {
        this.styleSheet = styleSheet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String stylesheet = String.format(THEME_FORMAT, styleSheet);
        String previousStylesheet = model.getStyleSheet();
        if (isSameStyleSheet(stylesheet, previousStylesheet)) {
            throw new CommandException(String.format(MESSAGE_SAME_STYLESHEET, styleSheet));
        }

        MainWindow.setStylesheet(stylesheet);
        model.setStyleSheet(stylesheet);
        return new CommandResult(String.format(MESSAGE_SUCCESS, styleSheet));
    }

    private boolean isSameStyleSheet(String styleSheet, String preStyleSheet) {
        requireNonNull(styleSheet);
        requireNonNull(preStyleSheet);
        return styleSheet.equals(preStyleSheet);
    }
}
