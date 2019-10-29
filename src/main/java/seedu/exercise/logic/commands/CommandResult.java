package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.exercise.ui.ListResourceType;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private boolean showHelp;

    /**
     * The application should exit.
     */
    private boolean exit;

    /**
     * Show the resolve window to user due to scheduling conflict
     */
    private boolean showResolve;

    /**
     * The type of resource to be shown in the GUI.
     */
    private ListResourceType showListResourceType;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp,
                         boolean isExit, boolean showResolve, ListResourceType listResourceType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = isExit;
        this.showResolve = showResolve;
        this.showListResourceType = listResourceType;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean showResolve) {
        this(feedbackToUser, showHelp, exit, showResolve, ListResourceType.NULL);
    }

    public CommandResult(String feedbackToUser, ListResourceType listResourceType) {
        this(feedbackToUser);
        this.showListResourceType = listResourceType;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, ListResourceType.NULL);
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

    public boolean isShowResolve() {
        return showResolve;
    }

    public ListResourceType getShowListResourceType() {
        return showListResourceType;
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
            && showResolve == otherCommandResult.showResolve
            && showListResourceType.equals(otherCommandResult.showListResourceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, showResolve, showListResourceType);
    }

}
