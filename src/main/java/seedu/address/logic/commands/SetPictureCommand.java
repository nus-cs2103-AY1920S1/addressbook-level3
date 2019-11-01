package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PICTURE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.EditPersonDescriptor;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Picture;


/**
 * Assigns a picture to a student.
 */
public class SetPictureCommand extends Command {

    public static final String COMMAND_WORD = "set_pic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a picture to a student "
            + "by the index number used in the displayed person list. "
            + "Please ensure the picture is in .jpg/.png/.gif/.bmp format and is in the same directory as TutorAid "
            + "Existing picture will be overwritten by the incoming picture.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PICTURE + "FILENAME "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PICTURE
            + "new.jpg";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Picture assigned: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Please specify a picture!";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public SetPictureCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Picture updatedPicture = editPersonDescriptor.getPicture().orElse(personToEdit.getPicture());

        return new Person(personToEdit.getName(), updatedPicture, personToEdit.getClassId(),
                personToEdit.getAttendance(), personToEdit.getResult(), personToEdit.getParticipation());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetPictureCommand)) {
            return false;
        }

        // state check
        SetPictureCommand e = (SetPictureCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

}
