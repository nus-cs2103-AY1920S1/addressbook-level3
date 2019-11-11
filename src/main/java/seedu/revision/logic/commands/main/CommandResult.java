package seedu.revision.logic.commands.main;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.revision.model.Model;
import seedu.revision.model.quiz.Mode;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /** Feedback that will be shown to the user**/
    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean isShowHelp;

    /** The application should exit. */
    private final boolean isExit;

    /** The quiz will start. */
    private final boolean isStart;

    /** The restore window will open. */
    private final boolean isRestore;

    /** The history window will open. */
    private final boolean isShowHistory;

    /** The stats window will open. */
    private final boolean isShowStats;

    /** The answer is correct. */
    private final boolean isCorrect;

    /** The mode of the quiz in session **/
    private final Mode mode;

    /** To pass the Model. */
    private final Model model;

    /**
     * Constructs a {@code CommandResult} using a {@code CommandResultBuilder}.
     * Defensive programming: By taking in a builder, the developer cannot type
     * in the parameters manually. The builder also assigns default values when initiated.
     */
    public CommandResult(CommandResultBuilder builder) {
        requireNonNull(builder);
        this.feedbackToUser = builder.getFeedbackToUser();
        this.isShowHelp = builder.isShowHelp();
        this.isExit = builder.isExit();
        this.isStart = builder.isStart();
        this.isRestore = builder.isShowRestore();
        this.isShowHistory = builder.isShowHistory();
        this.isShowStats = builder.isShowStats();
        this.isCorrect = builder.isCorrect();
        this.mode = builder.getMode();
        this.model = builder.getModel();
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return isShowHelp;
    }

    public boolean isExit() {
        return isExit;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isShowRestore() {
        return isRestore;
    }

    public boolean isShowHistory() {
        return isShowHistory;
    }

    public boolean isShowStats() {
        return isShowStats;
    }

    public boolean getIsCorrect() {
        return this.isCorrect;
    }

    public Model getModel() {
        requireNonNull(this.model);
        return model;
    }

    public Mode getMode() {
        requireNonNull(this.mode);
        return mode;
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
                && isShowHelp == otherCommandResult.isShowHelp
                && isExit == otherCommandResult.isExit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, isShowHelp, isExit);
    }

}
