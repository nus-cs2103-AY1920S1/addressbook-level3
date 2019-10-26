package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.ui.ResultViewType;

/**
 * Represents the result of a command execution.
 */
public class CommandResult<T> {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean changesActiveStudyPlan;

    /**
     * The application should exit.
     */
    private final boolean exit;

    private ResultViewType resultViewType = null;

    private ObservableList<T> resultContent = null;

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, ResultViewType resultViewType, ObservableList<T> resultContent) {
        this(feedbackToUser, false, false);
        this.resultViewType = resultViewType;
        this.resultContent = resultContent;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean changesActiveStudyPlan, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.changesActiveStudyPlan = changesActiveStudyPlan;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isChangesActiveStudyPlan() {
        return changesActiveStudyPlan;
    }

    public boolean isExit() {
        return exit;
    }

    public ResultViewType getResultViewType() {
        return resultViewType;
    }

    public ObservableList<T> getResultContent() {
        return resultContent;
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
                && changesActiveStudyPlan == otherCommandResult.changesActiveStudyPlan
                && exit == otherCommandResult.exit
                && ((resultViewType == null) ? (otherCommandResult.resultViewType == null)
                        : resultViewType.equals(otherCommandResult.resultViewType)
                                && (resultContent != null) && resultContent.equals(otherCommandResult.resultContent));
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, changesActiveStudyPlan, exit, resultViewType, resultContent);
    }

}
