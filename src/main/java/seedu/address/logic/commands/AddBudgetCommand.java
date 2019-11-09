package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Finance;
import seedu.address.model.project.Project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_CHECKED_OUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

/**
 * Adds a budget type to the project.
 */

public class AddBudgetCommand extends Command {

    private final Budget budget;
    public static final String COMMAND_WORD = "addBudget";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a budget type to the project"
            + "[" + PREFIX_BUDGET + "BUDGET]...\n"
            + "[" + PREFIX_EXPENSE + "EXPENSE]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_BUDGET + "equipment"
            + PREFIX_EXPENSE + "3000.00";

    public static final String MESSAGE_SUCCESS = "New budgets added";

    public AddBudgetCommand(Budget buget) {
        this.budget = buget;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);

        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }

        Project currWorkingProject = model.getWorkingProject().get();

        currWorkingProject.getFinance().addBudget(budget);

        Project editedProject = new Project(currWorkingProject.getTitle(), //title
                currWorkingProject.getDescription(), currWorkingProject.getMemberNames(), //description + members
                currWorkingProject.getTasks(), new Finance(currWorkingProject.getFinance().getBudgets()), currWorkingProject.getGeneratedTimetable()); //tasks and budget
        editedProject.getListOfMeeting().addAll(currWorkingProject.getListOfMeeting());

        model.setWorkingProject(editedProject);
        model.setProject(currWorkingProject, editedProject);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);

        return new CommandResult(String.format(MESSAGE_SUCCESS), COMMAND_WORD);
    }

}
