package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Budget;
import seedu.address.model.project.Project;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_CHECKED_OUT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListBudgetCommand extends Command {

    public static final String COMMAND_WORD = "listBudget";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Project workingProject = model.getWorkingProject().get();
        StringBuilder sb = new StringBuilder();
        List<Budget> budgetArrayList = workingProject.getFinance().getBudgets();
        Collections.sort(budgetArrayList, Comparator.comparing(budget -> budget.getRemainingMoney().getAmount()));
        int index = 0;
        for (Budget budget : budgetArrayList) {
            index++;
            sb.append(index + ". ");
            sb.append(budget.toString());
            sb.append("\n");
        }
        return new CommandResult(sb.toString(), COMMAND_WORD);
    }
}
