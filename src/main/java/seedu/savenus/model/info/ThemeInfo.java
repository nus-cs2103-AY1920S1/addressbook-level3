package seedu.savenus.model.info;

//@@author robytanama

import seedu.savenus.logic.commands.ThemeCommand;

/**
 * Contains information on Theme command.
 */
public class ThemeInfo {

    public static final String COMMAND_WORD = ThemeCommand.COMMAND_WORD;

    public static final String INFORMATION = "Theme command allows you to change application's theme.\n\n"
            + "The theme information will depend on the following factor:\n"
            + "Light or Dark theme\n\n"
            + "There are only two themes available: light and dark\n\n";

    public static final String USAGE = "theme dark";

    public static final String OUTPUT = "The app's theme will change to Dark theme";
}
