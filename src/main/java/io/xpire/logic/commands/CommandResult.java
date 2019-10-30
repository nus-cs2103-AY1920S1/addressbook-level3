package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** QR code of the current list should be shown to the user. */
    private final boolean showQr;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    private CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean showQr) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showQr = showQr;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields,
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields,
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showQr) {
        this(feedbackToUser, false, false, true);
    }

    public String getFeedbackToUser() {
        return this.feedbackToUser;
    }

    public boolean isShowHelp() {
        return this.showHelp;
    }

    public boolean isExit() {
        return this.exit;
    }

    public boolean isShowQr() {
        return this.showQr;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof CommandResult)) {
            return false;
        } else {
            CommandResult other = (CommandResult) obj;
            return this.feedbackToUser.equals(other.feedbackToUser)
                    && this.showHelp == other.showHelp
                    && this.exit == other.exit
                    && this.showQr == other.showQr;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.feedbackToUser, this.showHelp, this.exit, this.showQr);
    }

}
