package seedu.address.logic.finance.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.finance.Model.PREDICATE_SHOW_ALL_BUDGETS;

import seedu.address.model.finance.Model;

/**
 * Lists all budgets in the finance log to the user.
 */
public class ListBudgetCommand extends Command {

    public static final String COMMAND_WORD = "listb";

    public static final String MESSAGE_SUCCESS = "Listed all budgets";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
        return new CommandResult(MESSAGE_SUCCESS, true);
    }
}
