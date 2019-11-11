package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.Spending;
import seedu.address.model.project.Project;
import seedu.address.model.util.SortingOrder;

import static seedu.address.commons.core.Messages.MESSAGE_NOT_CHECKED_OUT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;


import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Adds a budget type to the project.
 */

public class AddSpendingCommand extends Command {

    private final Spending toAdd;
    private final Index index;

    public static final String COMMAND_WORD = "addExpense";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an expense to the specific budget"
            + " index of the budget you want to add...\n"
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]...\n"
            + "[" + PREFIX_EXPENSE + "EXPENSE]...\n"
            + "[" + PREFIX_TIME + "TIME]...\n"
            + "Example: " + COMMAND_WORD
            + " 1 s/bought pizza for the team ex/60.00 c/20/10/2019 1600";
    public static final String MESSAGE_SUCCESS = "New expense added";

    public AddSpendingCommand(Index index, Spending toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);
        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }

        Project currWorkingProject = model.getWorkingProject().get();
        List<Budget> budgets = currWorkingProject.getFinance().getBudgets();

        if (index.getZeroBased() >= budgets.size() || index.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
        }

        budgets.get(index.getZeroBased()).addSpending(toAdd);
        Collections.sort(budgets.get(index.getZeroBased()).getSpendings(), SortingOrder.getCurrentSortingOrderForSpending());
        Project editedProject = new Project(currWorkingProject.getTitle(),
                currWorkingProject.getDescription(), currWorkingProject.getMemberNames(), currWorkingProject.getTasks(),
                new Finance(budgets), currWorkingProject.getGeneratedTimetable());

        editedProject.setListOfMeeting(currWorkingProject.getListOfMeeting());
        model.setWorkingProject(editedProject);
        model.setProject(currWorkingProject, editedProject);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS), COMMAND_WORD);
    }

}
