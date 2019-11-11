package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Review;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private final Eatery eateryToShow;

    private final Review reviewToShow;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /** Statistics information should be shown to the user. */
    private final boolean showStats;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * The application should save to-do eatery to eatery list.
     */
    private final boolean wantToSave;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, Eatery eateryToShow, Review reviewToShow,
                         boolean showHelp, boolean exit, boolean wantToSave, boolean showStats) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.eateryToShow = eateryToShow;
        this.reviewToShow = reviewToShow;
        this.showHelp = showHelp;
        this.exit = exit;
        this.wantToSave = wantToSave;
        this.showStats = showStats;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, null, null, false, false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code eateryToShow},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, Eatery eateryToShow) {
        this(feedbackToUser, eateryToShow, null, false, false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code reviewToShow},
     * and other fields are set to their default value.
     */
    public CommandResult(String feedbackToUser, Review reviewToShow) {
        this(feedbackToUser, null, reviewToShow, false, false, false, false);
    }
    /**
     * Constructs a {@code CommandResult} with the specified fields,
     * and {@code eateryToShow} set to its default value
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean wantToSave, boolean showStats) {
        this(feedbackToUser, null, null, showHelp, exit, wantToSave, showStats);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Eatery getEateryToShow() {
        return eateryToShow;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean wantToSave() {
        return wantToSave;
    }

    public boolean isShowStats() {
        return showStats;
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
                && (Objects.equals(eateryToShow, otherCommandResult.eateryToShow))
                && (Objects.equals(reviewToShow, otherCommandResult.reviewToShow))
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, eateryToShow, reviewToShow, showHelp, exit);
    }

}
