package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Finance;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

public class AddFromContactsCommand extends Command {

    public static final String COMMAND_WORD = "addFromContacts";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the person identified by the index number used in the displayed person list to the working project.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Examples: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ADD_EXISTING_SUCCESS = "Added %1$s to %2$s";
    public static final String MESSAGE_DUPLICATE_MEMBER = "Member already exists in the project.";

    private final List<Index> targetIndexList;

    public AddFromContactsCommand(List<Index> targetIndexList) {
        this.targetIndexList = targetIndexList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> personsToAdd = new ArrayList<>();
        List<Index> indexChecker = new ArrayList<>();

        if (containsInvalidIndexes(targetIndexList, lastShownList)) {
            int invalidIndex = getInvalidIndex(targetIndexList, lastShownList);
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + " Invalid index: " + invalidIndex);
        }

        if (containsDuplicateIndexes(targetIndexList)) {
            throw new CommandException(MESSAGE_DUPLICATE_INDEX + " Duplicate index: " + getDuplicateIndex(targetIndexList));
        }


        for (Index targetIndex : targetIndexList) {
            personsToAdd.add(lastShownList.get(targetIndex.getZeroBased()));
        }

        //Finds the person in the address book, and gets the name as a string
        List<String> namesToAdd = getNames(personsToAdd);

        //Find the project to edit, and the title of the project as a string
        Project projectToEdit = model.getWorkingProject().get();
        String projectToEditTitle = projectToEdit.getTitle().toString();

        List<String> memberListToEdit = projectToEdit.getMemberNames();
        for (String name : namesToAdd) {
            int index = targetIndexList.get(namesToAdd.indexOf(name)).getOneBased();
            if (memberListToEdit.contains(name)) {
                throw new CommandException(MESSAGE_DUPLICATE_MEMBER + " Duplicate Member: " + name + " is specified by index " + index);
            }
        }
        List<String> editedMemberList = new ArrayList<>();
        editedMemberList.addAll(memberListToEdit);
        editedMemberList.addAll(namesToAdd);

        Project editedProject = new Project(projectToEdit.getTitle(),
                projectToEdit.getDescription(), editedMemberList, projectToEdit.getTasks(),
                new Finance(projectToEdit.getFinance().getBudgets()), projectToEdit.getGeneratedTimetable());

        editedProject.getListOfMeeting().addAll(projectToEdit.getListOfMeeting());
        model.setProject(projectToEdit, editedProject);
        model.setWorkingProject(editedProject);

        List<Person> editedPersons = addProjToPersons(personsToAdd, editedProject);
        setPersons(personsToAdd, editedPersons, model);

        String names = getAsStringPersons(personsToAdd);

        return new CommandResult(String.format(MESSAGE_ADD_EXISTING_SUCCESS, names, projectToEditTitle), COMMAND_WORD);

    }

    private List<String> getNames(List<Person> personsToAdd) {
        List<String> namesToAdd = new ArrayList<>();

        for (Person person : personsToAdd) {
            namesToAdd.add(person.getName().fullName);
        }

        return namesToAdd;
    }

    private List<Person> addProjToPersons(List<Person> personsToAdd, Project projectToAdd) {
        List<Person> editedPersons = new ArrayList<>();
        for (Person person : personsToAdd) {
            List<String> projectList = person.getProjects();
            projectList.add(projectToAdd.getTitle().title);
            Person editedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(), person.getProfilePicture(),
                    person.getAddress(), person.getTags(), person.getTimetable(), person.getPerformance());
            editedPerson.getProjects().addAll(projectList);
            editedPersons.add(editedPerson);
        }
        return editedPersons;
    }

}
