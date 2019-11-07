package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Finance;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;
import seedu.address.model.timetable.Timetable;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_CHECKED_OUT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

public class DeleteProjectMeetingCommand extends Command {

    public static final String COMMAND_WORD = "deleteMeeting";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": delete a meeting from the list of meetings in the project "
            + "by the index number used in the displayed project meetings list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String MESSAGE_DELETE_PROJECT_MEETING_SUCCESS = "Meetings deleted :\n%1$s";

    private final Index index;

    public DeleteProjectMeetingCommand(Index index) {
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
        Set<Meeting> meetings = projectToEdit.getListOfMeeting();
        ArrayList<Meeting> meetingsToEdit = new ArrayList<>(meetings);
        Timetable timetable = projectToEdit.getGeneratedTimetable();

        if (index.getZeroBased() >= meetingsToEdit.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        meetingsToEdit.sort(Comparator.comparing(m -> m.getTime().getDate()));
        Meeting meetingToDelete = meetingsToEdit.remove(index.getOneBased() - 1);
        model.deleteMeetingInAllPersons(meetingToDelete, projectToEdit);
        Set<Meeting> newMeeting = new HashSet<Meeting>(meetingsToEdit);

        Project editedProject = new Project(projectToEdit.getTitle(), projectToEdit.getDescription(),
                members, tasks, finance, timetable);
        editedProject.setListOfMeeting(newMeeting);

        model.setProject(projectToEdit, editedProject);
        model.setWorkingProject(editedProject);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_DELETE_PROJECT_MEETING_SUCCESS, meetingToDelete), COMMAND_WORD);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteProjectMeetingCommand)) {
            return false;
        }

        // state check
        DeleteProjectMeetingCommand e = (DeleteProjectMeetingCommand) other;
        return index.equals(e.index);
    }
}
