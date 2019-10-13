package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.project.Project;
import seedu.address.model.project.UniqueProjectList;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class AddMemberCommand extends Command {

    public static final String COMMAND_WORD = "addMember";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the project specified by the "
            + "index as a member.\n"
            + "Parameters INDEX (must be a positive integer) "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "Co-Team Leader ";

    public static final String MESSAGE_SUCCESS = "New member added to %2$s project and address book: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the project";
    public static final String MESSAGE_WRONG_ADD_COMMAND = "This person already exists in the address book, "
            + "please use addExistingMember instead.";

    private final Person toAdd;

    /**
     * Creates an AddMemberCommand to add the specified {@code Person}
     */
    public AddMemberCommand(Person person) {
        requireNonNull(person);

        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Project projectToEdit = model.getWorkingProject().get();
        Person personToAdd = this.toAdd;
        UniquePersonList personListToEdit = projectToEdit.getPersonList();

        if (model.hasPerson(personToAdd)) {
            throw new CommandException(MESSAGE_WRONG_ADD_COMMAND);
        }

        //Adding person to address book and project
        model.addPerson(personToAdd);
        personListToEdit.add(personToAdd);

        //Adding project to person
        UniqueProjectList projectList = personToAdd.getProjectList();
        projectList.add(projectToEdit);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToAdd, projectToEdit));
    }


}
