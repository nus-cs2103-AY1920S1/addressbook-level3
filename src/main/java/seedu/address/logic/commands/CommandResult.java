package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.core.Mode;
import seedu.address.commons.core.index.Index;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final boolean isGoTo;
    private final Mode modeToGoTo;
    private final boolean read;
    private final Object object;
    private final Index index;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean goTo,
                         Mode modeToGoTo) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.isGoTo = goTo;
        this.modeToGoTo = modeToGoTo;
        this.read = false;
        this.object = null;
        this.index = null;
    }

    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean goTo,
                         Mode modeToGoTo, boolean read, Object object, Index index) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.isGoTo = goTo;
        this.modeToGoTo = modeToGoTo;
        this.read = read;
        this.object = object;
        this.index = index;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false,
                null);
    }

    public static Builder builder(String feedbackToUser) {
        return new Builder(feedbackToUser);
    }

    /**
     * Represents a CommandResult constructor builder.
     */
    public static class Builder {

        private String feedbackToUser;

        /** Help information should be shown to the user. */
        private boolean showHelp;

        /** The application should exit. */
        private boolean exit;
        private boolean isGoTo;
        private boolean read;
        private Mode modeToGoTo;
        private Object object;
        private Index index;

        public Builder (String feedbackToUser) {
            this.feedbackToUser = feedbackToUser;
        }

        /**
         * Toggles showHelp boolean to true.
         * @return builder with showHelp as true.
         */
        public Builder showHelp() {
            this.showHelp = true;
            return this;
        }

        /**
         * Toggles exit boolean to true.
         * @return builder with exit as true.
         */
        public Builder exit() {
            this.exit = true;
            return this;
        }

        /**
         * Toggles isGoTo boolean to true.
         * @return builder with isGoTo as true.
         */
        public Builder isGoTo() {
            this.isGoTo = true;
            return this;
        }

        /**
         * Toggles read boolean to true.
         * @return builder with read as true.
         */
        public Builder read() {
            this.read = true;
            return this;
        }

        public Builder setMode(Mode mode) {
            this.modeToGoTo = mode;
            return this;
        }

        public Builder setObject(Object object) {
            this.object = object;
            return this;
        }

        public Builder setIndex(Index index) {
            this.index = index;
            return this;
        }

        public CommandResult build() {
            return new CommandResult(feedbackToUser, showHelp, exit, isGoTo, modeToGoTo, read, object, index);
        }
    }

    public Object getObject() {
        return object;
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

    public boolean isGoTo() {
        return isGoTo;
    }

    public Mode getModeToGoTo() {
        return modeToGoTo;
    }

    public boolean isRead() {
        return read;
    }

    public Index getIndex() {
        return index;
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
                && isGoTo == otherCommandResult.isGoTo
                && modeToGoTo == otherCommandResult.modeToGoTo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, isGoTo, modeToGoTo);
    }

}
