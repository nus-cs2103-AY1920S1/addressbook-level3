package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;
import seedu.address.model.finance.Budget;
import seedu.address.model.project.Project;

/**
 * Lists all persons in the address book to the user.
 */
public class ListBudgetCommand extends Command {

    public static final String COMMAND_WORD = "listBudget";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        Project workingProject = model.getWorkingProject().get();
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (Budget budget : workingProject.getFinance().getBudgets()) {
            index++;
            sb.append(index + ". ");
            sb.append(budget.toString());
            sb.append("\n");
        }
        return new CommandResult(sb.toString());
    }
}
