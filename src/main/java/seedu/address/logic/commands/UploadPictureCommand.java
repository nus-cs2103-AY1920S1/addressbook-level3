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
import seedu.address.model.student.Email;
import seedu.address.model.student.MedicalCondition;
import seedu.address.model.student.Name;
import seedu.address.model.student.ParentPhone;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Uploads picture of student into student card
 * acts like edit command
 */
public class UploadPictureCommand extends Command {
    //TODO save new filepath into storage somehow
    public static final String COMMAND_WORD = "upload";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": activates file chooser to upload "
            + "picture of student, by the index number used in the displayed student list. "
            + "Existing DisplayPicture will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) ";

    public static final String MESSAGE_WRONG_FORMAT = "Please only upload a PNG or JPG file.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the classroom.";

    private final Index index;
    private final String fileName;

    /**
     * Constructor for command, makes sure index not null
     * @param index
     */
    public UploadPictureCommand(Index index, String fileName) {

        requireNonNull(index);
        requireNonNull(fileName);
        this.index = index;
        this.fileName = fileName;
    }

    public Index getIndex() {
        return index;
    }

    /**
     * creates edited student with new picture
     * @param studentToEdit
     * @param fileName
     * @return Student
     */
    private static Student createEditedStudent(Student studentToEdit, String fileName) {
        assert studentToEdit != null;

        Name name = studentToEdit.getName();
        Phone phone = studentToEdit.getPhone();
        Email email = studentToEdit.getEmail();
        ParentPhone parentPhone = studentToEdit.getParentPhone();
        Address address = studentToEdit.getAddress();
        MedicalCondition medicalCondition = studentToEdit.getMedicalCondition();
        Set<Tag> tags = studentToEdit.getTags();

        Student editedStudent = new Student(name, phone, email, parentPhone, address, medicalCondition, tags);
        editedStudent.setDisplayPicture(fileName);

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
        Student editedStudent = createEditedStudent(studentToEdit, fileName);
        if (!studentToEdit.isSameStudent(editedStudent) && model.hasStudent(editedStudent)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }
        String messageSuccess = "Uploaded " + editedStudent.getName() + "'s photo successfully.";
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
        if (!(other instanceof UploadPictureCommand)) {
            return false;
        }

        // state check
        UploadPictureCommand c = (UploadPictureCommand) other;
        return index.equals(c.getIndex());
    }
}
