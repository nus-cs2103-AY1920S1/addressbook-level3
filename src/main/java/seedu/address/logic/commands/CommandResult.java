package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.model.statistics.PieChartStatistics;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.TableEntry;
import seedu.address.model.statistics.TabularStatistics;
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

    /** The panel to show the in the application. */
    private PanelName panelName;

    private Statistics statistics;

    private List<String> names;

    private List<Double> percentages;

    private String title;

    private List<TableEntry> differenceTable;

    public CommandResult(String feedbackToUser, Statistics statistics, boolean showHelp, boolean exit,
                         PanelName panelName) {

        requireNonNull(feedbackToUser);

        this.feedbackToUser = feedbackToUser;
        this.statistics = statistics;
        this.showHelp = showHelp;
        this.exit = exit;
        this.panelName = panelName;

        if (statistics == null) {
            return;
        } else if (statistics instanceof PieChartStatistics) {
            PieChartStatistics pieChart = (PieChartStatistics) statistics;
            this.names = pieChart.getFormattedCategories();
            this.percentages = pieChart.getFormattedPercentages();
        } else if (statistics instanceof TabularStatistics) {
            TabularStatistics table = (TabularStatistics) statistics;
            this.differenceTable = table.getDifferenceTable();
        }
        this.title = statistics.getTitle();


    }


    /**
     * Constructs a {@code CommandResult} with the specified fields. Meant for statsCommand
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         PanelName panelName) {
        requireNonNull(feedbackToUser);

        this.feedbackToUser = feedbackToUser;
        this.showHelp = showHelp;
        this.exit = exit;
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
        this(feedbackToUser, false, false, panelName);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value. Meant for most MooLah commands
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, PanelName.CURRENT);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value. Meant for Help and Bye commands.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, PanelName.CURRENT);
    }

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
        return statistics != null;
    }

    public Statistics getStatistics() {
        return statistics;
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

    public List<TableEntry> getDifferenceTable() {
        return differenceTable;
    }

    public PanelName viewrequest() {
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
