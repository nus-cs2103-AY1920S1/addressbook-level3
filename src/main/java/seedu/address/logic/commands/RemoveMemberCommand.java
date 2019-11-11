package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

public class RemoveMemberCommand extends Command {
    public static final String COMMAND_WORD = "removeMember";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the member from the working project.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_MEMBER_SUCCESS = "Removed %1$s from %2$s";

    private Index targetIndex;

    public RemoveMemberCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        requireNonNull(model);

        Project projectToEdit = model.getWorkingProject().get();

        if (targetIndex.getZeroBased() >= projectToEdit.getMemberNames().size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        String personToRemoveName = projectToEdit.getMemberNames().get(targetIndex.getZeroBased());
        //Creates a predicate to find the person in the contacts
        Predicate<Person> predicate = new NameContainsKeywordsPredicate(Arrays.asList(personToRemoveName.split("\\s+")));
        //Finding the person and removing the project from the person's list of projects
        model.updateFilteredPersonList(predicate);
        Person targetPerson = model.getFilteredPersonList().get(0);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        //Creating the new member list
        List<String> memberListToEdit = projectToEdit.getMemberNames();
        memberListToEdit.remove(targetPerson.getName().toString());
        List<String> editedMemberList = new ArrayList<>();
        editedMemberList.addAll(memberListToEdit);

        Project editedProject = new Project(projectToEdit.getTitle(), projectToEdit.getDescription(),
                editedMemberList, projectToEdit.getTasks(), projectToEdit.getFinance(), projectToEdit.getGeneratedTimetable());
        editedProject.setListOfMeeting(projectToEdit.getListOfMeeting());

        model.setPerson(targetPerson, removeFromPerson(projectToEdit, targetPerson));
        model.setProject(projectToEdit, editedProject);
        model.setWorkingProject(editedProject);

        return new CommandResult(String.format(MESSAGE_REMOVE_MEMBER_SUCCESS, targetPerson.getName().fullName,
                editedProject.getTitle().toString()), COMMAND_WORD);
    }

    private Person removeFromPerson(Project project, Person person) {
        String projectTitle = project.getTitle().title;
        person.getProjects().remove(projectTitle);
        Person editedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(), person.getProfilePicture(),
                person.getAddress(), person.getTags(), person.getTimetable(), person.getPerformance());
        editedPerson.getProjects().addAll(person.getProjects());

        return editedPerson;
    }
}
