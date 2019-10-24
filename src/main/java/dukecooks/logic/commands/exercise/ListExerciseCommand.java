package dukecooks.logic.commands.exercise;

import static java.util.Objects.requireNonNull;

import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ListCommand;
import dukecooks.model.Model;

/**
 * Lists all persons in the Duke Cooks to the user.
 */
public class ListExerciseCommand extends ListCommand {

    public static final String VARIANT_WORD = "exercise";

    public static final String MESSAGE_SUCCESS = "Listed all exercises";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExerciseList(Model.PREDICATE_SHOW_ALL_EXERCISE);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
