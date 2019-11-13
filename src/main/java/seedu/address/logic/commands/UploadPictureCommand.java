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

//@@author SebastianLie
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

    public static final String MESSAGE_WRONG_FORMAT = "Please only upload a PNG or JPG file.";
    public static final String MESSAGE_SAME_PICTURE = "This student's display picture is the same as the current one.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found! Please upload another file.";

    private final Index index;
    private final String fileName;

    /**
     * Constructor for command, makes sure index not null
     * @param index
     */
    public UploadPictureCommand(Index index, String fileName) {

        requireNonNull(index);
        requireNonNull(fileName);

        assert fileName.length() > 4 : "Filename should be at least 4 letters long to be valid!";
        int start = fileName.length() - 3;

        boolean isPng = fileName.substring(start).equals("png");
        boolean isJpg = fileName.substring(start).equals("jpg");

        assert isPng || isJpg : "File should be a JPG or PNG file!";

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
        DisplayPicture displayPicture = new DisplayPicture(fileName);

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
        String currentFileName = studentToEdit.getDisplayPictureFilePath();
        if (currentFileName.equals(fileName)) {
            throw new CommandException(MESSAGE_SAME_PICTURE);
        }
        Student editedStudent = createEditedStudent(studentToEdit, fileName);

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
