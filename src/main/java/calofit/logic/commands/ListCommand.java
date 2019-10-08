package calofit.logic.commands;

import calofit.model.Model;

import static calofit.model.Model.PREDICATE_SHOW_ALL_DISHES;
import static java.util.Objects.requireNonNull;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all dishes";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDishList(PREDICATE_SHOW_ALL_DISHES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
