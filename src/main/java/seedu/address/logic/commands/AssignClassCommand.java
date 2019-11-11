package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.EditPersonDescriptor;
import seedu.address.model.Model;
import seedu.address.model.classid.ClassId;
import seedu.address.model.person.Person;

/**
 * Assigns a class to a student or to a group of students.
 */
public class AssignClassCommand extends Command {

    public static final String COMMAND_WORD = "assign_class";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits/Updates the class of the user or users "
            + "by the index number used in the displayed person list. \n"
            + "Existing values will be overwritten by the input values.\n"

            + "Parameters: INDEXES (must be a positive integers) "
            + PREFIX_CLASSID + "NEW_CLASS "

            + "Example: " + COMMAND_WORD + " 1,2,3 "
            + PREFIX_CLASSID + "CS2030";

    public static final String MESSAGE_ASSIGN_SUCCESS = "Class successfully assigned.";
    public static final String MESSAGE_NOT_EDITED = "You must specify a class name.";
    public static final String MESSAGE_DUPLICATE_PERSON = "A student already has that class assigned!";

    private final ArrayList<Index> index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index stores an arraylist of indexes of people in the filtered person list to edit
     * @param editPersonDescriptor details to edit the people with
     */
    public AssignClassCommand(ArrayList<Index> index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        HashMap<Person, Person> editedPeople = new HashMap<>();

        //Check through all indexes are valid before executing
        for (Index index : this.index) {
            System.out.println(lastShownList.size());
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PEOPLE_DISPLAYED_INDEX);
            }
        }

        for (Index index : this.index) {
            Person personToEdit = lastShownList.get(index.getZeroBased());
            Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

            if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            //to solve problem of lastShownList changing after setting a person causing IndexOutOfBound
            editedPeople.put(personToEdit, editedPerson);

        }

        Iterator hmIterator = editedPeople.entrySet().iterator();

        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            Person toEdit = (Person) mapElement.getKey();
            Person editedPerson = (Person) mapElement.getValue();
            model.setPerson(toEdit, editedPerson);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitTutorAid();
        return new CommandResult(String.format(MESSAGE_ASSIGN_SUCCESS),
                false, false, false, false,
                false, true, false, false);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit,
                                                 EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        ClassId updatedClassId = editPersonDescriptor.getClassId().get();

        return new Person(personToEdit.getName(), personToEdit.getPicture(),
                updatedClassId, personToEdit.getAttendance(),
                personToEdit.getResult(), personToEdit.getParticipation());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignClassCommand)) {
            return false;
        }

        // state check
        AssignClassCommand e = (AssignClassCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

}
