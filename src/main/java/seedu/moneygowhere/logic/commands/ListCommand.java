package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.model.Model.PREDICATE_SHOW_ALL_SPENDINGS;

import seedu.moneygowhere.logic.sorting.SpendingComparator;
import seedu.moneygowhere.model.Model;

/**
 * Lists all persons in the MoneyGoWhere list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all spending entries in default order";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSpendingList(PREDICATE_SHOW_ALL_SPENDINGS);

        // Reset to the default comparator
        model.updateSortedSpendingList(new SpendingComparator());

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
