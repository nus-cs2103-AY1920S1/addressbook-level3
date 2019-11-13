package calofit.logic.commands;

import static calofit.model.Model.PREDICATE_SHOW_DEFAULT;
import static java.util.Objects.requireNonNull;

import calofit.model.Model;

/**
 * Lists all persons in the dish database to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all dishes";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setDishFilterPredicate(PREDICATE_SHOW_DEFAULT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
