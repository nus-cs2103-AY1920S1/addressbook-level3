package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Uploads picture of student into student card
 * acts like edit command
 */
public class UploadPictureCommand extends Command {
    public static final String COMMAND_WORD = "upload";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": activates file chooser to upload "
            + "picture of student, by the index number used in the displayed student list. "
            + "Existing DisplayPicture will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) ";

    public static final String MESSAGE_UPLOAD_SUCCESS = "Uploaded photo successfully.";
    public static final String MESSAGE_WRONG_FORMAT = "Please only upload a PNG file.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the classroom.";

    private final Index index;

    /**
     * Constructor for command, makes sure index not null
     * @param index
     */
    public UploadPictureCommand(Index index) {

        requireNonNull(index);
        this.index = index;
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

        studentToEdit.setDisplayPicture(fileName);
        return studentToEdit;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());

        Student editedStudent = createEditedStudent(studentToEdit,"/images/test.png");
        if (!studentToEdit.isSameStudent(editedStudent) && model.hasStudent(editedStudent)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.saveState();
        return new CommandResult(String.format(MESSAGE_UPLOAD_SUCCESS, editedStudent));
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
