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
            + "index of the budget you want to add...\n"
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]...\n"
            + "[" + PREFIX_EXPENSE + "EXPENSE]...\n"
            + "[" + PREFIX_TIME + "TIME]...s\n"
            + "Example: " + COMMAND_WORD
            + "1 s/bought pizza for the team ex/60.00 c/20/10/19";
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
            throw new CommandException(model.checkoutConstrain());
        }

        Project currWorkingProject = model.getWorkingProject().get();
        List<Budget> budgets = currWorkingProject.getFinance().getBudgets();

        if (index.getZeroBased() >= budgets.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
        }

        budgets.get(index.getZeroBased()).addSpending(toAdd);
        Project editedProject = new Project(currWorkingProject.getTitle(),
                currWorkingProject.getDescription(), currWorkingProject.getMemberNames(), currWorkingProject.getTasks(),
                new Finance(budgets));

        model.setWorkingProject(editedProject);
        model.setProject(currWorkingProject, editedProject);
        return new CommandResult(String.format(MESSAGE_SUCCESS), COMMAND_WORD);
    }

}
