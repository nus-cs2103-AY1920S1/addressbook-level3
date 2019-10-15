package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.WorkoutPlanner;

/**
 * Clears Duke Cooks.
 */
public class ClearExerciseCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Planner has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setDukeCooks(new WorkoutPlanner());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
