package seedu.address.logic.commands.commandresults;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.logic.FunctionMode;
import seedu.address.logic.commands.CommandResult;

/**
 * Represents the result of a command execution.
 */
public class GlobalCommandResult extends CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The application should toggle to a different function. */
    private final boolean toggle;

    /** Targeted function. */
    private final Optional<FunctionMode> targetMode;

    /**
     * Constructs a {@code GlobalCommandResult} with the specified fields.
     */
    public GlobalCommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         boolean toggle, Optional<FunctionMode> targetMode) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.toggle = toggle;
        this.targetMode = targetMode;
    }

    /**
     * Constructs a {@code GlobalCommandResult} with the specified fields.
     */
    public GlobalCommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, false, Optional.empty());
    }

    /**
     * Constructs a {@code GlobalCommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public GlobalCommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, Optional.empty());
    }

    @Override
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isToggle() {
        return toggle;
    }

    public Optional<FunctionMode> getTargetMode() {
        return targetMode;
    }

    @Override
    public boolean isGlobalCommandResult() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GlobalCommandResult)) {
            return false;
        }

        GlobalCommandResult otherGlobalCommandResult = (GlobalCommandResult) other;
        return feedbackToUser.equals(otherGlobalCommandResult.feedbackToUser)
                && showHelp == otherGlobalCommandResult.showHelp
                && exit == otherGlobalCommandResult.exit
                && toggle == otherGlobalCommandResult.toggle
                && targetMode == otherGlobalCommandResult.targetMode;
    }
    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, toggle, targetMode);
    }

}
