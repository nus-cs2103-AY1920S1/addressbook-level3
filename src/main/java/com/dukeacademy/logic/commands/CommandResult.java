package com.dukeacademy.logic.commands;

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

    private final boolean home;

    private final boolean view;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     *
     * @param feedbackToUser the feedback to user
     * @param showHelp       the show help
     * @param exit           the exit
     * @param home           the home
     * @param view           the view
     */
    public CommandResult(String feedbackToUser, boolean showHelp,
                         boolean exit, boolean home, boolean view) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.home = home;
        this.view = view;
    }

    /**
     * Constructs a {@code CommandResult} with the specified
     * {@code feedbackToUser}, and other fields set to their default value.
     *
     * @param feedbackToUser the feedback to user
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false);
    }

    /**
     * Gets feedback to user.
     *
     * @return the feedback to user
     */
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    /**
     * Is show help boolean.
     *
     * @return the boolean
     */
    public boolean isShowHelp() {
        return showHelp;
    }

    /**
     * Is exit boolean.
     *
     * @return the boolean
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * Is home boolean.
     *
     * @return the boolean
     */
    public boolean isHome() {
        return home;
    }

    /**
     * Is home boolean.
     *
     * @return the boolean
     */
    public boolean isView() {
        return view;
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
