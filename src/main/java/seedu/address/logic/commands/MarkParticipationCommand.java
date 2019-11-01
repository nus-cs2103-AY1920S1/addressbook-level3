package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Participation;
import seedu.address.model.person.Person;

/**
 * Increases participation of student or to a group of students.
 */
public class MarkParticipationCommand extends Command {

    public static final String COMMAND_WORD = "mark_participation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks participation of the student or students "
            + "by the index number used in the displayed person list. \n"
            + "All values will be increased by one.\n"

            + "Parameters: INDEXES (must be a positive integers) "

            + "Example: " + COMMAND_WORD + " 1,2,3 ";

    public static final String MESSAGE_MARK_SUCCESS = "Participation marked!";

    private final ArrayList<Index> index;

    /**
     * @param index stores an arraylist of indexes of people in the filtered person list to edit
     */
    public MarkParticipationCommand(ArrayList<Index> index) {
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
            Person editedPerson = createEditedPerson(personToEdit, personToEdit.getParticipation());

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
                                             Participation currentParticipation) {
        assert personToEdit != null;

        int incrementedParticipation = Integer.parseInt(currentParticipation.value) + 1;
        Participation updatedParticipation = new Participation(Integer.toString(incrementedParticipation));

        return new Person(personToEdit.getName(), personToEdit.getPicture(),
                personToEdit.getClassId(), personToEdit.getAttendance(),
                personToEdit.getResult(), updatedParticipation);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkParticipationCommand)) {
            return false;
        }

        // state check
        MarkParticipationCommand e = (MarkParticipationCommand) other;
        return index.equals(e.index);
    }

}
