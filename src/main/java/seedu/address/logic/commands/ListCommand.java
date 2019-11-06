package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import seedu.address.model.Model;
import seedu.address.model.ViewState;

/**
 * Lists all expenses in the expense list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all expenses";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
        model.setViewState(ViewState.DEFAULT_EXPENSELIST);
        return new CommandResult(model.getFilteredExpenseList(), null,
            null, MESSAGE_SUCCESS);
    }
}
