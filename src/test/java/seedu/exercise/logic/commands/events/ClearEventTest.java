package seedu.exercise.logic.commands.events;

import static seedu.exercise.logic.commands.events.EventTestUtil.CLEAR_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertRedoEventSuccess;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertUndoEventSuccess;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;

public class ClearEventTest {
    private Model actualModel;
    private Model expectedModel;
    private ClearEvent clearEvent;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager();
        actualModel = new ModelManager();
        clearEvent = new ClearEvent(CLEAR_EVENT_PAYLOAD);
    }

    @Test
    public void undo_modelAfterClearing_modelWithExerciseRestored() {
        expectedModel.setExerciseBook(getTypicalExerciseBook());
        assertUndoEventSuccess(clearEvent, expectedModel, actualModel);
    }

    @Test
    public void redo_modelAfterRedoingClear_modelWithExerciseCleared() {
        clearEvent.undo(actualModel);
        assertRedoEventSuccess(clearEvent, expectedModel, actualModel);
    }
}
