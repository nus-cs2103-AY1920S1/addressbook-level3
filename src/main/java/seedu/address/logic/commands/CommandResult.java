package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.visual.Indicator;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * Application should display list of policies
     */
    private boolean listPolicy;

    /**
     * Application should display list of people
     */
    private boolean listPeople;

    /**
     * Application should display report
     */
    private boolean report;

    /**
     * Application should display specified indicator
     */
    private boolean display;

    private Indicator indicator;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser,
                         boolean showHelp,
                         boolean exit,
                         boolean listPolicy,
                         boolean listPeople,
                         boolean report,
                         boolean display) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.listPolicy = listPolicy;
        this.listPeople = listPeople;
        this.report = report;
        this.display = display;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, false, false);
    }

    public CommandResult(String feedbackToUser, Indicator indicator) {
        this(feedbackToUser, false, false, false, false, false,
            true);
        this.indicator = indicator;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Indicator getIndicator() { return this.indicator; }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isListPolicy() {
        return listPolicy;
    }

    public boolean isListPeople() {
        return listPeople;
    }

    public boolean isReport() {
        return report;
    }

    public boolean isDisplay() {
        return display;
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
            && listPolicy == otherCommandResult.listPolicy
            && listPeople == otherCommandResult.listPeople
            && report == otherCommandResult.report
            && display == otherCommandResult.display;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, listPolicy, listPeople, report, display);
    }

}
