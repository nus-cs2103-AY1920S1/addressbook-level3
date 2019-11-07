package dukecooks.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import dukecooks.model.workout.Workout;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Reward that a user gets if 5 new tasks are completed.
     */
    private final boolean showReward;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final boolean isRunWorkout;

    private final boolean isViewWorkout;

    private Workout workoutToRun;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showReward, boolean showHelp, boolean exit,
                         boolean isRunWorkout) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showReward = showReward;
        this.showHelp = showHelp;
        this.exit = exit;
        this.isRunWorkout = isRunWorkout;
        isViewWorkout = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showReward, boolean showHelp, boolean exit,
                         boolean isRunWorkout, Workout workoutToRun, boolean isViewWorkout) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showReward = showReward;
        this.showHelp = showHelp;
        this.exit = exit;
        this.isRunWorkout = isRunWorkout;
        this.workoutToRun = workoutToRun;
        this.isViewWorkout = isViewWorkout;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowReward() {
        return showReward;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isRunWorkout() {
        return isRunWorkout;
    }

    public Workout getWorkoutToRun() {
        return workoutToRun;
    }

    public boolean isViewWorkout() {
        return isViewWorkout;
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
                && showReward == otherCommandResult.showReward
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && isViewWorkout == otherCommandResult.isViewWorkout;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showReward, showHelp, exit, isRunWorkout);
    }

}
