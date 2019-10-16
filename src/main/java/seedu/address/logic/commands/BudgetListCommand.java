package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUDGETS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class BudgetListCommand extends Command {

    public static final String COMMAND_WORD = "budgetList";

    public static final String MESSAGE_SUCCESS = "Listed all budgets";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBudgets(PREDICATE_SHOW_ALL_BUDGETS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
