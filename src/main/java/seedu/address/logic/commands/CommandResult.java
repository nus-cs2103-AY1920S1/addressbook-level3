package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.ui.panel.PanelName;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The application should be forced to show a panel. */
    private final boolean forcePanelChange;

    /** The panel to show the in the application. */
    private PanelName panelName;

    private final boolean statistic;

    private final List<String> names;

    private final List<Double> percentages;

    private final String title;

    //currently used only by basicStats, to be merged later on
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean statistic,
                         boolean forcePanelChange, PanelName panelName,
                         List<String> names, List<Double> percentages, String title) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.statistic = statistic;
        this.names = names;
        this.percentages = percentages;
        this.title = title;
        this.forcePanelChange = forcePanelChange;
        this.panelName = panelName;

    }

    /**
     * Constructs a {@code CommandResult} with the specified fields. Meant for statsCommand
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean statistic,
                         boolean forcePanelChange, PanelName panelName) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.statistic = statistic;
        this.forcePanelChange = forcePanelChange;
        this.panelName = panelName;
        this.names = null;
        this.percentages = null;
        this.title = null;

    }


    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, {@code forcePanelChange},
     * {@code panelName}, and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, PanelName panelName) {
        this(feedbackToUser, false, false, false, true, panelName);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value. Meant for most MooLah commands
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, PanelName.CURRENT);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value. Meant for Help and Bye commands.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean statistic) {
        this(feedbackToUser, showHelp, exit, statistic, false, PanelName.CURRENT, null, null, null);
    }

    //idk if this works


    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isStatistic() {
        return statistic;
    }


    public List<String> getNames() {
        return names;
    }

    public List<Double> getPercentages() {
        return percentages;
    }

    public String getTitle() {
        return title;
    }

    public boolean isViewRequest() {
        return forcePanelChange;
    }

    public PanelName viewRequest() {
        return panelName;
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
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}
