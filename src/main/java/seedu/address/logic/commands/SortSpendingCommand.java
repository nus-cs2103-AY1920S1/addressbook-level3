package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.Spending;
import seedu.address.model.project.Project;
import seedu.address.model.util.SortingOrder;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_CHECKED_OUT;

/**
 * Sorts the tasks in the current project.
 */
public class SortSpendingCommand extends Command {
    public static final String COMMAND_WORD = "sortSpending";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the spendings for each expense in the list of budgets according to given index.\n"
            + "1 - Sorts by alphabetical order.\n"
            + "2 - Sorts by increasing date/time.\n"
            + "5 - Sorts by increasing spending.\n"
            + "Parameters: INDEX (must be a positive integer between 1-2 or 5)\n"
            + "Example: " + COMMAND_WORD + " 1";


    public static final String MESSAGE_SORT_SPENDING_SUCCESS = "Spendings sorted by%1$s";
    public static final String MESSAGE_SAME_INDEX = "Spending already sorted in this order! Select a different ordering.";


    public final Index index;

    public SortSpendingCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }

        int num = index.getOneBased();
        if (num == SortingOrder.getSpendingCurrentIndex()) {
            throw new CommandException(MESSAGE_SAME_INDEX);
        }

        Project projectToEdit = model.getWorkingProject().get();
        List<String> members = projectToEdit.getMemberNames();
        String sortType = "";

        switch (num) {

        case 1:
            sortType = " alphabetical order.";
            SortingOrder.setCurrentSpendingSortingOrderByAlphabeticalOrder();
            break;

        case 2:
            sortType = " increasing date/time.";
            SortingOrder.setCurrentSpendingSortingOrderByDate();
            break;

        case 5:
            sortType = " increase expenses.";
            SortingOrder.setCurrentSpendingSortingOrderByExpense();
            break;

        default:
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        List<Budget> budgetListToEdit = projectToEdit.getFinance().getBudgets();
        for (Budget budget : budgetListToEdit) {
            sortSpending(budget.getSpendings(), SortingOrder.getCurrentSortingOrderForSpending());
        }

        Finance finance = projectToEdit.getFinance();

        Project editedProject = new Project(projectToEdit.getTitle(), projectToEdit.getDescription(), new ArrayList<>(), projectToEdit.getTasks(), finance, projectToEdit.getGeneratedTimetable());
        editedProject.getMemberNames().addAll(members);


        model.setProject(projectToEdit, editedProject);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_SORT_SPENDING_SUCCESS, sortType), COMMAND_WORD);
    }

    public void sortSpending(List<Spending> list, Comparator<Spending> spendingComparator) {
        Collections.sort(list, spendingComparator);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortSpendingCommand)) {
            return false;
        }

        // state check
        SortSpendingCommand e = (SortSpendingCommand) other;
        return index.equals(e.index);
    }
}
