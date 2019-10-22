package seedu.address.logic.commands.exercise;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.exercise.WorkoutPlanner;

/**
 * Clears Duke Cooks.
 */
public class ClearExerciseCommand extends ClearCommand {

    public static final String VARIANT_WORD = "exercise";
    public static final String MESSAGE_SUCCESS = "Planner has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setWorkoutPlanner(new WorkoutPlanner());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
