package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.model.person.PanelName;
import seedu.address.ui.FontName;

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
    private final boolean toggleGraphics;

    /** For changing the font. */
    private final FontName fontName;
    private final boolean listFonts;
    private final boolean changeFont;

    /** For changing the theme */
    private final boolean changeTheme;
    private final String newTheme;

    private boolean toShowConditionPanel = false;


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
        this.toggleGraphics = false;
        this.fontName = null;
        this.listFonts = false;
        this.changeFont = false;
        this.changeTheme = false;
        this.newTheme = null;
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
        this.toggleGraphics = toggleBooleans.get(1);
        this.fontName = null;
        this.listFonts = false;
        this.changeFont = false;
        this.changeTheme = false;
        this.newTheme = null;
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
        this.toggleGraphics = false;
        this.fontName = null;
        this.listFonts = false;
        this.changeFont = false;
        this.changeTheme = false;
        this.newTheme = null;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields, and other fields are set to their default value.
     */
    public CommandResult(String feedbackToUser, FontName fontName, boolean listFonts, boolean changeFont) {
        this.feedbackToUser = feedbackToUser;
        this.showHelp = false;
        this.exit = false;
        this.panelName = null;
        this.togglePanel = false;
        this.fontName = fontName;
        this.listFonts = listFonts;
        this.changeFont = changeFont;
        this.toggleStats = false;
        this.toggleGraphics = false;
        this.changeTheme = false;
        this.newTheme = null;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields, and other fields are set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean changeTheme, String theme) {
        this.feedbackToUser = feedbackToUser;
        this.changeTheme = changeTheme;
        this.newTheme = theme;
        this.showHelp = false;
        this.exit = false;
        this.panelName = null;
        this.togglePanel = false;
        this.toggleStats = false;
        this.toggleGraphics = false;
        this.fontName = null;
        this.listFonts = false;
        this.changeFont = false;
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

    public String getNewTheme() {
        return newTheme;
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

    public boolean isToggleStats() {
        return toggleStats;
    }

    public boolean isToggleGraphics() {
        return toggleGraphics;
    }

    public boolean isChangeFont() {
        return changeFont;
    }

    public boolean isListFonts() {
        return listFonts;
    }

    public boolean isChangeTheme() {
        return changeTheme;
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
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && panelName == otherCommandResult.panelName
                && togglePanel == otherCommandResult.togglePanel
                && fontName == otherCommandResult.fontName
                && listFonts == otherCommandResult.listFonts
                && changeFont == otherCommandResult.changeFont
                && changeTheme == otherCommandResult.changeTheme
                && newTheme.equals(otherCommandResult.newTheme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, panelName, togglePanel, fontName, listFonts, changeFont,
                newTheme, changeTheme);
    }

}
