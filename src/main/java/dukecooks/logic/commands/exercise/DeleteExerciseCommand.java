package dukecooks.logic.commands.exercise;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.DeleteCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.workout.exercise.components.Exercise;

/**
 * Deletes an exercise identified using it's displayed index from Duke Cooks.
 */
public class DeleteExerciseCommand extends DeleteCommand {

    public static final String VARIANT_WORD = "exercise";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + VARIANT_WORD
            + ": Deletes the exercise identified by the index number used in the displayed exercise list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " 1";

    public static final String MESSAGE_DELETE_EXERCISE_SUCCESS = "Deleted Exercise: %1$s";

    private final Index targetIndex;

    public DeleteExerciseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteExercise(exerciseToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EXERCISE_SUCCESS, exerciseToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteExerciseCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteExerciseCommand) other).targetIndex)); // state check
    }
}
