package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;

public class DeleteLessonCommandTest {
    private Model model = new ModelManager(getTypicalNotebook(), new UserPrefs());


    @Test
    public void execute_validIndexUnfilteredList_success() {
        model = new ModelManager(getTypicalNotebook(), new UserPrefs());
        Lesson lessonToDelete = model.getFilteredLessonWeekList().get(INDEX_FIRST_OBJECT.getZeroBased())
                .asUnmodifiableObservableList().get(INDEX_FIRST_OBJECT.getZeroBased());
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(INDEX_FIRST_OBJECT, INDEX_FIRST_OBJECT);

        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete);

        ModelManager expectedModel = new ModelManager(model.getNotebook(), new UserPrefs());
        // emulates delete command behaviour
        expectedModel.deleteLesson(lessonToDelete);
        assertCommandSuccess(deleteLessonCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonWeekList()
            .get(0).asUnmodifiableObservableList().size() + 1);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(outOfBoundIndex, INDEX_FIRST_OBJECT);

        assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteLessonCommand deleteFirstCommand = new DeleteLessonCommand(INDEX_FIRST_OBJECT, INDEX_FIRST_OBJECT);
        DeleteLessonCommand deleteSecondCommand = new DeleteLessonCommand(INDEX_SECOND_OBJECT, INDEX_FIRST_OBJECT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteLessonCommand deleteFirstCommandCopy = new DeleteLessonCommand(INDEX_FIRST_OBJECT, INDEX_FIRST_OBJECT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoLesson(Model model) {
        model.updateFilteredLessonList(p -> false);

        assertTrue(model.getFilteredLessonList().isEmpty());
    }
}
