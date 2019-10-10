package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import seedu.billboard.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all expenses";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
