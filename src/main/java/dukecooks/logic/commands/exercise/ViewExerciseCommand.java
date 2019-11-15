package dukecooks.logic.commands.exercise;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ViewCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.workout.exercise.components.Exercise;


/**
 * Views an Exercise in DukeCooks.
 */
public class ViewExerciseCommand extends ViewCommand {

    public static final String VARIANT_WORD = "exercise";

    public static final String MESSAGE_USAGE = COMMAND_WORD + VARIANT_WORD
            + ": Views an exercise in the shown list with"
            + "the specified index Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "You are viewing Exercise : %1$s";

    private final Index targetIndex;

    /**
     * Creates an ViewExerciseCommand to view the specified Diary
     */
    public ViewExerciseCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        int index = targetIndex.getZeroBased();

        if (index >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToShow = lastShownList.get(index);

        return new CommandResult(String.format(MESSAGE_SUCCESS, exerciseToShow),
                false, false, false, false, exerciseToShow, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewExerciseCommand)
                && targetIndex.equals(((ViewExerciseCommand) other).targetIndex);
    }
}
