package seedu.exercise.logic.commands.events;

import static seedu.exercise.logic.commands.events.EventTestUtil.DELETE_REGIME_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.LEVEL_ONE_REGIME;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertRedoEventSuccess;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertUndoEventSuccess;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;

public class DeleteRegimeEventTest {
    private Model actualModel;
    private Model expectedModel;
    private DeleteRegimeEvent deleteRegimeEvent;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager();
        actualModel = new ModelManager();
        deleteRegimeEvent = new DeleteRegimeEvent(DELETE_REGIME_EVENT_PAYLOAD);
        expectedModel.setExerciseBook(getTypicalExerciseBook());
        actualModel.setExerciseBook(getTypicalExerciseBook());
        expectedModel.addRegime(LEVEL_ONE_REGIME);
        actualModel.addRegime(LEVEL_ONE_REGIME);
    }

    @Test
    public void undo_modelAfterDeleting_modelWithRegimeAdded() {
        actualModel.deleteRegime(LEVEL_ONE_REGIME);
        assertUndoEventSuccess(deleteRegimeEvent, expectedModel, actualModel);
    }

    @Test
    public void redo_modelAfterUndoingDeleteRegime_modelWithRegimeDeleted() {
        expectedModel.deleteRegime(LEVEL_ONE_REGIME);
        actualModel.deleteRegime(LEVEL_ONE_REGIME); // Simulating delete regime event
        deleteRegimeEvent.undo(actualModel);
        assertRedoEventSuccess(deleteRegimeEvent, expectedModel, actualModel);
    }
}
