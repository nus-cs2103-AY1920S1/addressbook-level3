package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class RemoveMemberCommand extends Command {
    public static final String COMMAND_WORD = "removeMember";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the member from the working project.\n"
            + "Parameters: n/NAME"
            + "Example: " + COMMAND_WORD + " n/John doe";

    public static final String MESSAGE_REMOVE_MEMBER_SUCCESS = "Removed %1$s from %2$s";

    private final String targetMember;
    private final NameContainsKeywordsPredicate predicate;

    public RemoveMemberCommand(String targetMember, NameContainsKeywordsPredicate predicate) {
        this.targetMember = targetMember;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        requireNonNull(model);

        Project projectToEdit = model.getWorkingProject().get();

        //Finding the person and removing the project from the person's list of projects
        model.updateFilteredPersonList(predicate);
        Person targetPerson = model.getFilteredPersonList().get(0);
        List<String> memberProjectList = targetPerson.getProjects();
        memberProjectList.remove(
                memberProjectList.indexOf(
                        projectToEdit.getTitle().toString()));

        //Creating the new member list
        List<String> memberListToEdit = projectToEdit.getMemberNames();
        memberListToEdit.remove(targetPerson.getName().toString());
        List<String> editedMemberList = new ArrayList<>();
        editedMemberList.addAll(memberListToEdit);

        Project editedProject = new Project(projectToEdit.getTitle(), projectToEdit.getDescription(),
                editedMemberList, projectToEdit.getTasks(), projectToEdit.getFinance());
        editedProject.setListOfMeeting(projectToEdit.getListOfMeeting());

        model.setProject(projectToEdit, editedProject);
        model.setWorkingProject(editedProject);

        return new CommandResult(String.format(MESSAGE_REMOVE_MEMBER_SUCCESS, targetMember,
                editedProject.getTitle().toString()), COMMAND_WORD);
    }
}
