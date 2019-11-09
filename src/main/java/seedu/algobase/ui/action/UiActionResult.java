package seedu.algobase.ui.action;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents the result of an action.
 */
public class UiActionResult {

    private final Optional<String> feedbackToUser;

    /** Is the UI Action successfully executed*/
    private final boolean isSuccessful;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code UiActionResult} with the specified fields.
     */
    public UiActionResult(boolean isSuccessful, Optional<String> feedbackToUser, boolean showHelp, boolean exit) {
        requireNonNull(feedbackToUser);

        this.isSuccessful = isSuccessful;
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public UiActionResult(boolean isSuccessful, Optional<String> feedbackToUser) {
        this(isSuccessful, feedbackToUser, false, false);
    }

    public Optional<String> getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isSuccessfullyExecuted() {
        return isSuccessful;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UiActionResult)) {
            return false;
        }

        UiActionResult otherUiActionResult = (UiActionResult) other;
        return feedbackToUser.equals(otherUiActionResult.feedbackToUser)
            && showHelp == otherUiActionResult.showHelp
            && exit == otherUiActionResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return "[UiActionResult]: " + feedbackToUser;
    }
}
