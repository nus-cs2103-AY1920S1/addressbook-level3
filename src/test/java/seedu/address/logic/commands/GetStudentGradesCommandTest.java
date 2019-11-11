package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.logic.commands.GetStudentGradesCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;

/**
 * integration and unit tests for
 * {@code GetStudentGradesComand}
 */
public class GetStudentGradesCommandTest {

    private Model model = new ModelManager(getTypicalNotebook(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        GetStudentGradesCommand  getStudentGradesCommand = new  GetStudentGradesCommand(outOfBoundIndex);
        assertCommandFailure(getStudentGradesCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_OBJECT);

        Index outOfBoundIndex = INDEX_SECOND_OBJECT;
        // ensures that outOfBoundIndex is still in bounds of classroom list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getNotebook().getCurrentClassroom().getStudentList()
                .size());

        GetStudentGradesCommand  getStudentGradesCommand = new  GetStudentGradesCommand(outOfBoundIndex);

        assertCommandFailure(getStudentGradesCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }
    @Test
    public void execute_validIndexFilteredListNoGrades_success() {
        showStudentAtIndex(model, INDEX_FIRST_OBJECT);
        Student student = model.getFilteredStudentList().get(INDEX_FIRST_OBJECT.getZeroBased());
        GetStudentGradesCommand getStudentGradesCommand = new GetStudentGradesCommand(INDEX_FIRST_OBJECT);
        List<Assignment> lastShownAssignmentList = model.getFilteredAssignmentList();

        StringBuilder output = new StringBuilder();
        for (Assignment assignment : lastShownAssignmentList) {
            Map<String, String> currentAssignmentGrades = assignment.getGrades();
            if (currentAssignmentGrades.containsKey(student.getName().toString())) {
                output.append(assignment.getAssignmentName() + ": "
                        + currentAssignmentGrades.get(student.getName().toString()) + "\n");
            }
        }
        String expectedMessage = String.format(MESSAGE_SUCCESS, student.getName().toString(), output.toString());

        Model expectedModel = new ModelManager(model.getNotebook(), new UserPrefs());
        showStudentAtIndex(expectedModel, INDEX_FIRST_OBJECT);
        assertCommandSuccess(getStudentGradesCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_validIndexUnfilteredList_success() {
        model = new ModelManager(getTypicalNotebook(), new UserPrefs());
        Student student = model.getFilteredStudentList().get(INDEX_FIRST_OBJECT.getZeroBased());
        GetStudentGradesCommand getStudentGradesCommand = new GetStudentGradesCommand(INDEX_FIRST_OBJECT);
        List<Assignment> lastShownAssignmentList = model.getFilteredAssignmentList();

        StringBuilder output = new StringBuilder();
        for (Assignment assignment : lastShownAssignmentList) {
            Map<String, String> currentAssignmentGrades = assignment.getGrades();
            if (currentAssignmentGrades.containsKey(student.getName().toString())) {
                output.append(assignment.getAssignmentName() + ": "
                        + currentAssignmentGrades.get(student.getName().toString()) + "\n");
            }
        }
        String expectedMessage = String.format(MESSAGE_SUCCESS, student.getName().toString(), output.toString());

        Model expectedModel = new ModelManager(model.getNotebook(), new UserPrefs());
        assertCommandSuccess(getStudentGradesCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        GetStudentGradesCommand gradesFirstCommand = new GetStudentGradesCommand(INDEX_FIRST_OBJECT);
        GetStudentGradesCommand gradesSecondCommand = new GetStudentGradesCommand(INDEX_SECOND_OBJECT);

        // same object -> returns true
        assertTrue(gradesFirstCommand.equals(gradesFirstCommand));

        // same values -> returns true
        GetStudentGradesCommand gradesFirstCommandCopy = new GetStudentGradesCommand(INDEX_FIRST_OBJECT);
        assertTrue(gradesFirstCommand.equals(gradesFirstCommandCopy));

        // different types -> returns false
        assertFalse(gradesFirstCommand.equals(1));

        // null -> returns false
        assertFalse(gradesFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(gradesFirstCommand.equals(gradesSecondCommand));
    }
}
