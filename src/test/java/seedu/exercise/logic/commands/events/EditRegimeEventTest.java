package seedu.exercise.logic.commands.events;

import static seedu.exercise.logic.commands.events.EventTestUtil.EDIT_REGIME_BY_ADD_EVENT_TWO_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.EDIT_REGIME_BY_DELETE_EVENT_TWO_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.LEVEL_ONE_REGIME;
import static seedu.exercise.logic.commands.events.EventTestUtil.LEVEL_ONE_REGIME_WITH_TWO_ADDITIONAL;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertRedoEventSuccess;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertUndoEventSuccess;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;

public class EditRegimeEventTest {
    private Model actualModel;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager();
        actualModel = new ModelManager();
        expectedModel.setExerciseBook(getTypicalExerciseBook());
        actualModel.setExerciseBook(getTypicalExerciseBook());
    }

    @Test
    public void undo_modelAfterEditing_modelWithRegimeExerciseNotAdded() {
        EditRegimeEvent editRegimeEvent = new EditRegimeEvent(EDIT_REGIME_BY_ADD_EVENT_TWO_PAYLOAD);
        expectedModel.addRegime(LEVEL_ONE_REGIME);
        actualModel.addRegime(LEVEL_ONE_REGIME);
        actualModel.setRegime(LEVEL_ONE_REGIME, LEVEL_ONE_REGIME_WITH_TWO_ADDITIONAL);
        assertUndoEventSuccess(editRegimeEvent, expectedModel, actualModel);
    }

    @Test
    public void redo_modelAfterUndoingEditRegime_modelWithRegimeExerciseAdded() {
        EditRegimeEvent editRegimeEvent = new EditRegimeEvent(EDIT_REGIME_BY_ADD_EVENT_TWO_PAYLOAD);
        expectedModel.addRegime(LEVEL_ONE_REGIME_WITH_TWO_ADDITIONAL);
        actualModel.addRegime(LEVEL_ONE_REGIME);
        actualModel.setRegime(LEVEL_ONE_REGIME, LEVEL_ONE_REGIME_WITH_TWO_ADDITIONAL); // Simulating edit regime event
        editRegimeEvent.undo(actualModel);
        assertRedoEventSuccess(editRegimeEvent, expectedModel, actualModel);
    }

    @Test
    public void undo_modelAfterEditing_modelWithRegimeExerciseNotDeleted() {
        EditRegimeEvent editRegimeEvent = new EditRegimeEvent(EDIT_REGIME_BY_DELETE_EVENT_TWO_PAYLOAD);
        expectedModel.addRegime(LEVEL_ONE_REGIME_WITH_TWO_ADDITIONAL);
        actualModel.addRegime(LEVEL_ONE_REGIME_WITH_TWO_ADDITIONAL);
        actualModel.setRegime(LEVEL_ONE_REGIME_WITH_TWO_ADDITIONAL, LEVEL_ONE_REGIME);
        assertUndoEventSuccess(editRegimeEvent, expectedModel, actualModel);
    }

    @Test
    public void redo_modelAfterUndoingEditRegime_modelWithRegimeExerciseDeleted() {
        EditRegimeEvent editRegimeEvent = new EditRegimeEvent(EDIT_REGIME_BY_DELETE_EVENT_TWO_PAYLOAD);
        expectedModel.addRegime(LEVEL_ONE_REGIME);
        actualModel.addRegime(LEVEL_ONE_REGIME_WITH_TWO_ADDITIONAL);
        actualModel.setRegime(LEVEL_ONE_REGIME_WITH_TWO_ADDITIONAL, LEVEL_ONE_REGIME); // Simulating edit regime event
        editRegimeEvent.undo(actualModel);
        assertRedoEventSuccess(editRegimeEvent, expectedModel, actualModel);
    }
}
