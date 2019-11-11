package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_1;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Notebook;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class UploadCommandTest {

    private Model model = new ModelManager(getTypicalNotebook(), new UserPrefs());

    @Test
    public void execute_uploadCommand_success() {
        showStudentAtIndex(model, INDEX_FIRST_OBJECT);

        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_OBJECT.getZeroBased());
        Student editedStudent = new StudentBuilder(studentInFilteredList).withDisplayPicture(VALID_FILE_1).build();
        UploadPictureCommand uploadCommand = new UploadPictureCommand(INDEX_FIRST_OBJECT, VALID_FILE_1);

        String expectedMessage = "Uploaded " + editedStudent.getName() + "'s photo successfully.";

        Model expectedModel = new ModelManager(new Notebook(model.getNotebook()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent);

        assertCommandSuccess(uploadCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_duplicateStudentUnfilteredList_failure() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_OBJECT.getZeroBased());
        UploadPictureCommand uploadPictureCommand = new UploadPictureCommand(INDEX_FIRST_OBJECT,
                new StudentBuilder(firstStudent).build().getDisplayPictureFilePath());
        assertCommandFailure(uploadPictureCommand, model, UploadPictureCommand.MESSAGE_SAME_PICTURE);
    }
    @Test
    public void execute_duplicateStudentFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_OBJECT);

        // edit student in filtered list into a duplicate in classroom
        Student studentInList = model.getCurrentClassroom().getStudentList().get(INDEX_FIRST_OBJECT.getZeroBased());
        UploadPictureCommand uploadPictureCommand = new UploadPictureCommand(INDEX_FIRST_OBJECT,
                new StudentBuilder(studentInList).build().getDisplayPictureFilePath());
        assertCommandFailure(uploadPictureCommand, model, UploadPictureCommand.MESSAGE_SAME_PICTURE);
    }
    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        UploadPictureCommand uploadCommand = new UploadPictureCommand(outOfBoundIndex, VALID_FILE_1);
        assertCommandFailure(uploadCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }
    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_OBJECT);
        Index outOfBoundIndex = INDEX_SECOND_OBJECT;
        // ensures that outOfBoundIndex is still in bounds of classroom list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCurrentClassroom().getStudentList().size());

        UploadPictureCommand uploadCommand = new UploadPictureCommand(outOfBoundIndex, VALID_FILE_1);
        assertCommandFailure(uploadCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }
}
