package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Address;
import seedu.address.model.student.DisplayPicture;
import seedu.address.model.student.Email;
import seedu.address.model.student.MedicalCondition;
import seedu.address.model.student.Name;
import seedu.address.model.student.ParentPhone;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * resets display picture of student to a very handsome man.
 */
public class ResetDisplayPictureCommand extends Command {
    public static final String COMMAND_WORD = "resetdisplaypic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " resets display pic to default\n"
            + "Parameters: INDEX (must be a positive integer) ";

    public static final String MESSAGE_PICTURE_ALREADY_DEFAULT = "Display Picture already default!";
    private final Index index;

    /**
     * Constructor for command, makes sure index not null
     * @param index
     */
    public ResetDisplayPictureCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    public Index getIndex() {
        return index;
    }

    /**
     * creates edited student with new picture
     * @param studentToEdit
     * @return Student
     */
    private static Student createEditedStudent(Student studentToEdit) {
        assert studentToEdit != null;

        Name name = studentToEdit.getName();
        Phone phone = studentToEdit.getPhone();
        Email email = studentToEdit.getEmail();
        ParentPhone parentPhone = studentToEdit.getParentPhone();
        Address address = studentToEdit.getAddress();
        MedicalCondition medicalCondition = studentToEdit.getMedicalCondition();
        Set<Tag> tags = studentToEdit.getTags();
        DisplayPicture displayPicture = new DisplayPicture();

        Student editedStudent = new Student(name, phone, email, parentPhone, address,
                displayPicture, medicalCondition, tags);

        return editedStudent;
    }

    /**
     * creates edited student with changed display photo
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult
     * @throws CommandException
     */
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        Student editedStudent = createEditedStudent(studentToEdit);
        if (studentToEdit.isSameStudent(editedStudent) && model.hasStudent(editedStudent)) {
            throw new CommandException(MESSAGE_PICTURE_ALREADY_DEFAULT);
        }
        String messageSuccess = "Reset " + editedStudent.getName() + "'s display picture.";
        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.saveState();
        return new CommandResult(String.format(messageSuccess, editedStudent));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ResetDisplayPictureCommand)) {
            return false;
        }

        // state check
        ResetDisplayPictureCommand c = (ResetDisplayPictureCommand) other;
        return index.equals(c.getIndex());
    }

}
