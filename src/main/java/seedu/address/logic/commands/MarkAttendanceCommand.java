package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;

/**
 * Increases attendance of student or to a group of students.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "mark_attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance of the student or students "
            + "by the index number used in the displayed person list. \n"
            + "All values will be increased by one.\n"

            + "Parameters: INDEXES (must be a positive integers) "

            + "Example: " + COMMAND_WORD + " 1,2,3 ";

    public static final String MESSAGE_MARK_SUCCESS = "Attendance marked!";

    private final ArrayList<Index> index;

    /**
     * @param index stores an arraylist of indexes of people in the filtered person list to edit
     */
    public MarkAttendanceCommand(ArrayList<Index> index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index index : this.index) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToEdit = lastShownList.get(index.getZeroBased());
            Person editedPerson = createEditedPerson(personToEdit, personToEdit.getAttendance());

            model.setPerson(personToEdit, editedPerson);

        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARK_SUCCESS));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit,
                                             Attendance currentAttendance) {
        assert personToEdit != null;

        int incrementedAttendance = Integer.parseInt(currentAttendance.value) + 1;
        Attendance updatedAttendance = new Attendance(Integer.toString(incrementedAttendance));

        return new Person(personToEdit.getName(), personToEdit.getPicture(),
                personToEdit.getClassId(), updatedAttendance,
                personToEdit.getResult(), personToEdit.getParticipation());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAttendanceCommand)) {
            return false;
        }

        // state check
        MarkAttendanceCommand e = (MarkAttendanceCommand) other;
        return index.equals(e.index);
    }

}
