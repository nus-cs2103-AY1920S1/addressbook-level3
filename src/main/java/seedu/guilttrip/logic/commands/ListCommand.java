package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.guilttrip.model.Model.PREDICATE_SHOW_ALL_INCOMES;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.model.Model;

/**
 * Lists all expenses and incomes in guiltTrip to the user. Removes any other list of entries from the main panel.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Lists all entries in the guiltTrip to the user.";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC;
    public static final String MESSAGE_SUCCESS = "Listed all entries";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);
        model.updateFilteredIncomes(PREDICATE_SHOW_ALL_INCOMES);
        return new CommandResult(MESSAGE_SUCCESS, true, "main");
    }
}
