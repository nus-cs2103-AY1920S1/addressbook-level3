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
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The quiz will start. */
    private final boolean start;

    /** The restore window will open. */
    private final boolean showRestore;

    /** The history window will open. */
    private final boolean showHistory;

    /** The stats window will open. */
    private final boolean showStats;

    /** The answer is correct. */
    private final boolean isCorrect;

    /** The mode of the quiz in session **/
    private final Mode mode;

    /** To pass the Model. */
    private final Model model;

    /**
     * Constructs a {@code CommandResult} using a {@code CommandResultBuilder}.
     * and other fields set to their default value. Defensive programming: By taking in a builder, the developer
     * cannot type in the parameters manually. The builder also assigns default values when initiated.
     */
    public CommandResult(CommandResultBuilder builder) {
        requireNonNull(builder);
        this.feedbackToUser = builder.getFeedbackToUser();
        this.showHelp = builder.isShowHelp();
        this.exit = builder.isExit();
        this.start = builder.isStart();
        this.showRestore = builder.isShowRestore();
        this.showHistory = builder.isShowHistory();
        this.showStats = builder.isShowStats();
        this.isCorrect = builder.isCorrect();
        this.mode = builder.getMode();
        this.model = builder.getModel();
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

    public boolean isStart() {
        return start;
    }

    public boolean isShowRestore() {
        return showRestore;
    }

    public boolean isShowHistory() {
        return showHistory;
    }

    public boolean isShowStats() {
        return showStats;
    }

    public boolean isCorrect() {
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
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}
