package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Objects;

//@@author JermyTan
/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /** Feedback message to be shown to the user. */
    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** QR code of the current list should be shown to the user. */
    private final boolean showQr;

    /** QR code data */
    private final byte[] pngData;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    private CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean showQr, byte[] pngData) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showQr = showQr;
        this.pngData = pngData;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields,
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, false, new byte[0]);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, new byte[0]);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields,
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showQr, byte[] pngData) {
        this(feedbackToUser, false, false, showQr, pngData);
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

    public byte[] getPngData() {
        return this.pngData;
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
                    && this.showQr == other.showQr
                    && Arrays.equals(this.pngData, other.pngData);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.feedbackToUser, this.showHelp, this.exit, this.showQr, Arrays.hashCode(this.pngData));
    }

}
