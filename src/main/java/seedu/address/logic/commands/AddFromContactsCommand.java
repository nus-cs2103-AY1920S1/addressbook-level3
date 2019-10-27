package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Finance;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class AddFromContactsCommand extends Command {

    public static final String COMMAND_WORD = "addFromContacts";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the person identified by the index number used in the displayed person list to the working project.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Examples: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ADD_EXISTING_SUCCESS = "Added %1$s to %2$s";

    private final Index targetIndex;

    public AddFromContactsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        //Finds the person in the address book, and gets the name as a string
        Person personToAdd = lastShownList.get(targetIndex.getZeroBased());
        String personToAddName = personToAdd.getName().toString();

        //Find the project to edit, and the title of the project as a string
        Project projectToEdit = model.getWorkingProject().get();
        String projectToEditTitle = projectToEdit.getTitle().toString();

        List<String> memberListToEdit = projectToEdit.getMemberNames();
        List<String> editedMemberList = new ArrayList<>();
        editedMemberList.addAll(memberListToEdit);
        editedMemberList.add(personToAddName);

        Project editedProject = new Project(projectToEdit.getTitle(),
                projectToEdit.getDescription(), editedMemberList, projectToEdit.getTasks(),
                new Finance(projectToEdit.getFinance().getBudgets()));

        model.setProject(projectToEdit, editedProject);
        model.setWorkingProject(editedProject);

        personToAdd.getProjects().add(projectToEditTitle);

        return new CommandResult(String.format(MESSAGE_ADD_EXISTING_SUCCESS, personToAddName, projectToEditTitle), COMMAND_WORD);

    }
}
