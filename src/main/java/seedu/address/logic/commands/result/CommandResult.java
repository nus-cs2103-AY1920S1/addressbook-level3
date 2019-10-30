package seedu.address.logic.commands.result;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final UiFocus[] uiFocus;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, UiFocus ...uiFocus) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.uiFocus = uiFocus;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields,
     * but with uiFocus in its default value of null.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, (UiFocus)null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default values.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, (UiFocus)null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}
     * and {@code uiFocus, while other fields are set to their default values.
     */
    public CommandResult(String feedbackToUser, UiFocus ...uiFocus) {
        this(feedbackToUser, false, false, requireUiFocusNonNullAndReturn(uiFocus));
    }

    private static UiFocus[] requireUiFocusNonNullAndReturn(UiFocus ...uiFocus) {
        UiFocus[] nonNulls = new UiFocus[uiFocus.length];
        int i = 0;
        for (UiFocus u : uiFocus) {
            nonNulls[i] = requireNonNull(u);
            i++;
        }
        return nonNulls;
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

    public Optional<UiFocus[]> getUiFocus() {
        if (uiFocus[0] == null) {
            return Optional.empty();
        } else {
            return Optional.of(uiFocus);
        }
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
