package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

public class AddProjectMeetingCommand extends Command {
    public static final String COMMAND_WORD = "addProjectMeeting";

    public static final String MESSAGE_SUCCESS = "Meeting added: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting to the project. "
            + " Paremeters: "
            + PREFIX_TIME + " TIME "
            + PREFIX_DESCRIPTION + "DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TIME + "29/09/2019 1900"
            + PREFIX_DESCRIPTION + "milestone discussion";

    public static final String MESSAGE_DUPLICATE_PROJECT = "Project list contains duplicate project(s).";

    private final Meeting toAdd;

    /*
    Constructor
     */
    public AddProjectMeetingCommand(Meeting meeting) {
        requireNonNull(meeting);
        toAdd = meeting;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isCheckedOut()) {
            throw new CommandException(model.checkoutConstrain());
        }

        Project projectToEdit = model.getWorkingProject().get();
        List<String> members = projectToEdit.getMemberNames();
        List<Task> taskList = projectToEdit.getTasks();
        Set<Meeting> meetingList = projectToEdit.getListOfMeeting();
        Set<Meeting> newMeetingList = new HashSet<>();
        newMeetingList.addAll(meetingList);
        newMeetingList.add(toAdd);
        Project editedProject = new Project(projectToEdit.getTitle(), projectToEdit.getDescription(), members, taskList, projectToEdit.getFinance());
        editedProject.getMemberNames().addAll(members);
        editedProject.setListOfMeeting(newMeetingList);

        model.setProject(projectToEdit, editedProject);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), COMMAND_WORD);
    }
}
