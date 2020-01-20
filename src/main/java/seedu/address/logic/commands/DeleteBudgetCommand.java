package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Finance;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;
import seedu.address.model.timetable.Timetable;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_CHECKED_OUT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

public class DeleteBudgetCommand extends Command {

    public static final String COMMAND_WORD = "deleteBudget";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": delete a budget from the list of budgets in the project "
            + "by the index number used in the displayed project budget list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String MESSAGE_DELETE_BUDGET_SUCCESS = "Budget deleted :\n%1$s";

    private final Index index;

    public DeleteBudgetCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);

        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }

        Project projectToEdit = model.getWorkingProject().get();
        List<String> members = projectToEdit.getMemberNames();
        List<Task> tasks = projectToEdit.getTasks();
        Finance finance = projectToEdit.getFinance();
        List<Meeting> meetings = projectToEdit.getListOfMeeting();
        Timetable timetable = projectToEdit.getGeneratedTimetable();

        if (index.getZeroBased() >= finance.getBudgets().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Budget budgetToDelete = finance.removeBudget(index);
        Project editedProject = new Project(projectToEdit.getTitle(), projectToEdit.getDescription(),
                members, tasks, finance, timetable);
        editedProject.setListOfMeeting(meetings);

        model.setProject(projectToEdit, editedProject);
        model.setWorkingProject(editedProject);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete), COMMAND_WORD);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteBudgetCommand)) {
            return false;
        }

        // state check
        DeleteBudgetCommand e = (DeleteBudgetCommand) other;
        return index.equals(e.index);
    }
}
