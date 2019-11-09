package dukecooks.logic.commands.exercise;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.GotoCommand;
import dukecooks.model.Model;

/**
 * Directs to Health in DukeCooks to the user.
 */
public class GotoExerciseCommand extends GotoCommand {

    public static final String VARIANT_WORD = "exercise";

    public static final String MESSAGE_SUCCESS = "You are now at exercise";

    private static Event event;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredWorkoutList(Model.PREDICATE_SHOW_ALL_WORKOUT);
        event = Event.getInstance();
        event.set("workout", "all");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
