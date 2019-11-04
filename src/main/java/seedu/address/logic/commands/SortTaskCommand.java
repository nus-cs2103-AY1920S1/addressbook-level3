package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Finance;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;
import seedu.address.model.util.SortingOrder;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_CHECKED_OUT;

/**
 * Sorts the tasks in the current project.
 */
public class SortTaskCommand extends Command {
    public static final String COMMAND_WORD = "sortTask";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the task from the current project according to given index.\n "
            + "1 - Sorts by alphabetical order.\n"
            + "2 - Sorts by increasing date/time.\n"
            + "3 - Sorts by whether tasks are done.\n"
            + "4 - Sorts by whether tasks are done and then by increasing date/time.\n"
            + "Parameters: INDEX (must be a positive integer between 1-4)\n"
            + "Example: " + COMMAND_WORD + " 1";


    public static final String MESSAGE_SORT_TASK_SUCCESS = "Tasks sorted by%1$s";


    public final Index index;

    public SortTaskCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }

        Project projectToEdit = model.getWorkingProject().get();
        List<String> members = projectToEdit.getMemberNames();
        List<Task> taskToEdit = projectToEdit.getTasks();
        String sortType = "";

        switch (index.getOneBased()) {

        case 1:
            sortType = " alphabetical order.";
            SortingOrder.setCurrentTaskSortingOrderByAlphabeticalOrder();
            break;

        case 2:
            sortType = " increasing date/time.";
            SortingOrder.setCurrentTaskSortingOrderByDate();
            break;

        case 3:
            sortType = "  whether tasks are done.";
            SortingOrder.setCurrentTaskSortingOrderByDone();
            break;

        case 4:
            sortType = "  whether tasks are done and then by increasing date/time.";
            SortingOrder.setCurrentTaskSortingOrderByDoneThenDate();
            break;

        default:
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ArrayList<Task> taskList = new ArrayList<>();
        taskList.addAll(taskToEdit);
        Collections.sort(taskList, SortingOrder.getCurrentSortingOrderForTask());
        Finance finance = projectToEdit.getFinance();

        Project editedProject = new Project(projectToEdit.getTitle(), projectToEdit.getDescription(), new ArrayList<String>(), taskList, finance, projectToEdit.getGeneratedTimetable());
        editedProject.getMemberNames().addAll(members);


        model.setProject(projectToEdit, editedProject);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_SORT_TASK_SUCCESS, sortType), COMMAND_WORD);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortTaskCommand)) {
            return false;
        }

        // state check
        SortTaskCommand e = (SortTaskCommand) other;
        return index.equals(e.index);
    }
}
