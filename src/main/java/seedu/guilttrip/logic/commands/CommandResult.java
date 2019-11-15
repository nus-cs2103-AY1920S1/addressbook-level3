package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Objects;

import seedu.guilttrip.model.reminders.messages.Message;
import seedu.guilttrip.ui.util.FontName;
import seedu.guilttrip.ui.util.PanelName;
import seedu.guilttrip.ui.util.Theme;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** For toggling the panels. */
    private final PanelName panelName;
    private final boolean togglePanel;
    private final boolean toggleStats;
    private final boolean togglePieChart;
    private final boolean toggleBarChart;
    private final boolean toggleEntryPanel;

    /** For changing the font. */
    private final FontName fontName;
    private final boolean changeFont;

    /** For changing the theme. */
    private final boolean changeTheme;
    private final Theme newTheme;

    private boolean toShowConditionPanel = false;

    /** For listing any entry list. */
    private final String toList;
    private final boolean isList;

    /** For displaying generalReminder popup */
    private final boolean displayPopUp;
    private final Message message;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.panelName = null;
        this.togglePanel = false;
        this.toggleStats = false;
        this.togglePieChart = false;
        this.toggleBarChart = false;
        this.toggleEntryPanel = false;
        this.fontName = null;
        this.changeFont = false;
        this.isList = false;
        this.toList = null;
        this.changeTheme = false;
        this.newTheme = null;
        this.displayPopUp = false;
        this.message = null;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public CommandResult(String feedbackToUser, ArrayList<Boolean> toggleBooleans) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.togglePanel = false;
        this.panelName = null;
        this.toggleStats = toggleBooleans.get(0);
        this.togglePieChart = toggleBooleans.get(1);
        this.toggleBarChart = toggleBooleans.get(2);
        this.toggleEntryPanel = toggleBooleans.get(3);
        this.fontName = null;
        this.changeFont = false;
        this.toList = null;
        this.isList = false;
        this.changeTheme = false;
        this.newTheme = null;
        this.displayPopUp = false;
        this.message = null;
    }


    /**
     * Constructs a {@code CommandResult} with the specified fields, and other fields are set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean isList, String toList) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.panelName = null;
        this.togglePanel = false;
        this.toggleStats = false;
        this.fontName = null;
        this.changeFont = false;
        this.togglePieChart = false;
        this.toggleBarChart = false;
        this.toggleEntryPanel = false;
        this.changeTheme = false;
        this.newTheme = null;
        this.displayPopUp = false;
        this.message = null;
        this.isList = isList;
        this.toList = toList;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields, and other fields are set to their default value.
     */
    public CommandResult(String feedbackToUser, PanelName panelName, boolean togglePanel) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.panelName = panelName;
        this.togglePanel = togglePanel;
        this.toggleStats = false;
        this.togglePieChart = false;
        this.toggleBarChart = false;
        this.toggleEntryPanel = false;
        this.fontName = null;
        this.changeFont = false;
        this.toList = null;
        this.isList = false;
        this.changeTheme = false;
        this.newTheme = null;
        this.displayPopUp = false;
        this.message = null;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields, and other fields are set to their default value.
     */
    public CommandResult(String feedbackToUser, FontName fontName, boolean changeFont) {
        this.feedbackToUser = feedbackToUser;
        this.showHelp = false;
        this.exit = false;
        this.panelName = null;
        this.togglePanel = false;
        this.fontName = fontName;
        this.changeFont = changeFont;
        this.toggleStats = false;
        this.changeTheme = false;
        this.newTheme = null;
        this.togglePieChart = false;
        this.toggleBarChart = false;
        this.toggleEntryPanel = false;
        this.displayPopUp = false;
        this.message = null;
        this.toList = null;
        this.isList = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields, and other fields are set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean changeTheme, Theme theme) {
        this.feedbackToUser = feedbackToUser;
        this.changeTheme = changeTheme;
        this.newTheme = theme;
        this.showHelp = false;
        this.exit = false;
        this.panelName = null;
        this.togglePanel = false;
        this.toggleStats = false;
        this.fontName = null;
        this.changeFont = false;
        this.togglePieChart = false;
        this.toggleBarChart = false;
        this.toggleEntryPanel = false;
        this.displayPopUp = false;
        this.message = null;
        this.toList = null;
        this.isList = false;
    }

    public CommandResult(String feedbackToUser, Message message) {
        this.feedbackToUser = feedbackToUser;
        this.changeTheme = false;
        this.newTheme = null;
        this.showHelp = false;
        this.exit = false;
        this.panelName = null;
        this.togglePanel = false;
        this.toggleStats = false;
        this.fontName = null;
        this.changeFont = false;
        this.togglePieChart = false;
        this.toggleBarChart = false;
        this.toggleEntryPanel = false;
        this.displayPopUp = true;
        this.message = message;
        this.toList = null;
        this.isList = false;
    }

    public void showConditionPanel() {
        this.toShowConditionPanel = true;
    }

    public boolean toShowConditionPanel() {
        return toShowConditionPanel;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public PanelName getPanelName() {
        return this.panelName;
    }

    public FontName getFontName() {
        return fontName;
    }

    public Theme getNewTheme() {
        return newTheme;
    }

    public String getToList() {
        return toList;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isTogglePanel() {
        return togglePanel;
    }

    public boolean isToggleEntryPanel() {
        return toggleEntryPanel;
    }

    public boolean isToggleStats() {
        return toggleStats;
    }

    public boolean isTogglePieChart() {
        return togglePieChart;
    }

    public boolean isToggleBarChart() {
        return toggleBarChart;
    }

    public boolean isChangeFont() {
        return changeFont;
    }

    public boolean isChangeTheme() {
        return changeTheme;
    }

    public boolean isList() {
        return isList;
    }

    public boolean toDisplayPopUp() {
        return displayPopUp;
    }

    public Message getMessage() {
        return this.message;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        System.out.println(toList);
        System.out.println(((CommandResult) other).toList + "ASD");
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && panelName == otherCommandResult.panelName
                && togglePanel == otherCommandResult.togglePanel
                && fontName == otherCommandResult.fontName
                && changeFont == otherCommandResult.changeFont
                && changeTheme == otherCommandResult.changeTheme
                && newTheme == (otherCommandResult.newTheme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, panelName, togglePanel, fontName, changeFont, newTheme,
                changeTheme, toList);
    }

}
