package seedu.elisa.logic.commands;

/**
 * This class identifies when a command is a theme command.
 */
public class ThemeCommandResult extends CommandResult {

    private String theme;

    public ThemeCommandResult(String feedbackToUser, String theme) {
        super(feedbackToUser);
        this.theme = theme;
    }

    public String getTheme() {
        return this.theme;
    }
}
