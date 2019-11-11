package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_DEADLINE_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_MATH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAssignmentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAssignmentCommand.EditAssignmentDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Notebook;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditAssignmentCommand.
 */
public class EditAssignmentCommandTest {

    private Model model = new ModelManager(getTypicalNotebook(), new UserPrefs());


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Assignment editedAssignment = new AssignmentBuilder().build();
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder(editedAssignment).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_OBJECT, descriptor);
        editedAssignment.initialiseGrades(model.getCurrentClassroom().getStudentNameList());

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment);

        Model expectedModel = new ModelManager(new Notebook(model.getNotebook()), new UserPrefs());
        expectedModel.setAssignment(model.getFilteredAssignmentList().get(0), editedAssignment);

        assertCommandSuccess(editAssignmentCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAssignment = Index.fromOneBased(model.getFilteredAssignmentList().size());
        Assignment lastAssignment = model.getFilteredAssignmentList().get(indexLastAssignment.getZeroBased());

        AssignmentBuilder assignmentInList = new AssignmentBuilder(lastAssignment);
        Assignment editedAssignment = assignmentInList.withAssignmentName(VALID_ASSIGNMENT_NAME_MATH)
                .withAssignmentDeadline(VALID_ASSIGNMENT_DEADLINE_MATH).build();

        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder()
                .withAssignmentName(VALID_ASSIGNMENT_NAME_MATH).withAssignmentDeadline(VALID_ASSIGNMENT_DEADLINE_MATH)
                .build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(indexLastAssignment, descriptor);
        editedAssignment.initialiseGrades(model.getCurrentClassroom().getStudentNameList());

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment);

        Model expectedModel = new ModelManager(new Notebook(model.getNotebook()), new UserPrefs());
        expectedModel.setAssignment(lastAssignment, editedAssignment);

        assertCommandSuccess(editAssignmentCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_OBJECT,
                new EditAssignmentDescriptor());
        Assignment editedAssignment = model.getFilteredAssignmentList().get(INDEX_FIRST_OBJECT.getZeroBased());

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment);

        Model expectedModel = new ModelManager(new Notebook(model.getNotebook()), new UserPrefs());

        assertCommandSuccess(editAssignmentCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_filteredList_success() {
        showAssignmentAtIndex(model, INDEX_FIRST_OBJECT);

        Assignment assignmentInFilteredList = model.getFilteredAssignmentList().get(INDEX_FIRST_OBJECT.getZeroBased());
        Assignment editedAssignment = new AssignmentBuilder(assignmentInFilteredList)
                .withAssignmentName(VALID_ASSIGNMENT_NAME_ENGLISH).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_OBJECT,
                new EditAssignmentDescriptorBuilder().withAssignmentName(VALID_ASSIGNMENT_NAME_ENGLISH).build());

        editedAssignment.initialiseGrades(model.getCurrentClassroom().getStudentNameList());
        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment);

        Model expectedModel = new ModelManager(new Notebook(model.getNotebook()), new UserPrefs());
        expectedModel.setAssignment(model.getFilteredAssignmentList().get(0), editedAssignment);

        assertCommandSuccess(editAssignmentCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_duplicateAssignmentUnfilteredList_failure() {
        Assignment firstAssignment = model.getFilteredAssignmentList().get(INDEX_FIRST_OBJECT.getZeroBased());
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder(firstAssignment).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_SECOND_OBJECT, descriptor);

        assertCommandFailure(editAssignmentCommand, model, EditAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
    }


    @Test
    public void execute_duplicateAssignmentFilteredList_failure() {
        showAssignmentAtIndex(model, INDEX_FIRST_OBJECT);

        // edit assignment in filtered list into a duplicate in classroom
        Assignment assignmentInList = model.getCurrentClassroom().getAssignmentList()
                .get(INDEX_SECOND_OBJECT.getZeroBased());
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_OBJECT,
                new EditAssignmentDescriptorBuilder(assignmentInList).build());

        assertCommandFailure(editAssignmentCommand, model, EditAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
    }

    @Test
    public void execute_invalidAssignmentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAssignmentList().size() + 1);
        EditAssignmentCommand.EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder()
                .withAssignmentName(VALID_ASSIGNMENT_NAME_MATH).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editAssignmentCommand, model, Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of classroom
     */
    @Test
    public void execute_invalidAssignmentIndexFilteredList_failure() {
        showAssignmentAtIndex(model, INDEX_FIRST_OBJECT);
        Index outOfBoundIndex = INDEX_SECOND_OBJECT;
        // ensures that outOfBoundIndex is still in bounds of classroom list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCurrentClassroom().getAssignmentList().size());

        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(outOfBoundIndex,
                new EditAssignmentDescriptorBuilder().withAssignmentName(VALID_ASSIGNMENT_NAME_MATH).build());

        assertCommandFailure(editAssignmentCommand, model, Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final EditAssignmentCommand standardCommand = new EditAssignmentCommand(INDEX_FIRST_OBJECT, DESC_ENGLISH);

        // same values -> returns true
        EditAssignmentCommand.EditAssignmentDescriptor copyDescriptor = new EditAssignmentCommand
                .EditAssignmentDescriptor(DESC_ENGLISH);
        EditAssignmentCommand commandWithSameValues = new EditAssignmentCommand(INDEX_FIRST_OBJECT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditAssignmentCommand(INDEX_SECOND_OBJECT, DESC_ENGLISH)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAssignmentCommand(INDEX_FIRST_OBJECT, DESC_MATH)));
    }

}
