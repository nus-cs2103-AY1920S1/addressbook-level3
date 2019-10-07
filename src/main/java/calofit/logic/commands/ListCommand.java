package calofit.logic.commands;

import calofit.model.Model;

import static calofit.model.Model.PREDICATE_SHOW_ALL_MEALS;
import static java.util.Objects.requireNonNull;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all meals";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMealList(PREDICATE_SHOW_ALL_MEALS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
