package dukecooks.logic.commands.exercise;

import static java.util.Objects.requireNonNull;

import dukecooks.logic.commands.ClearCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.model.Model;
import dukecooks.model.workout.exercise.ExerciseCatalogue;

/**
 * Clears Duke Cooks.
 */
public class ClearExerciseCommand extends ClearCommand {

    public static final String VARIANT_WORD = "exercise";
    public static final String MESSAGE_SUCCESS = "Planner has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setExerciseCatalogue(new ExerciseCatalogue());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
