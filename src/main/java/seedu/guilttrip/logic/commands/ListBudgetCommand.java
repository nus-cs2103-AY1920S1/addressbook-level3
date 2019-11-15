package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.model.Model.PREDICATE_SHOW_ALL_ENTRIES;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.model.Model;

/**
 * Lists all budgets in the guiltTrip main panel to the user.
 */
public class ListBudgetCommand extends Command {

    public static final String COMMAND_WORD = "listBudget";
    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Lists all budgets in the guiltTrip to the user.";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC;


    public static final String MESSAGE_SUCCESS = "Listed all budgets";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredBudgets(PREDICATE_SHOW_ALL_ENTRIES);
        return new CommandResult(MESSAGE_SUCCESS, true, "budget");
    }
}
