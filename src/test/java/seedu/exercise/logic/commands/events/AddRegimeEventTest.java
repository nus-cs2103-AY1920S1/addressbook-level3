package seedu.exercise.logic.commands.events;

import static seedu.exercise.logic.commands.events.EventTestUtil.ADD_REGIME_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.LEVEL_ONE_REGIME;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertRedoEventSuccess;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertUndoEventSuccess;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;

public class AddRegimeEventTest {
    private Model actualModel;
    private Model expectedModel;
    private AddRegimeEvent addRegimeEvent;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager();
        actualModel = new ModelManager();
        addRegimeEvent = new AddRegimeEvent(ADD_REGIME_EVENT_PAYLOAD);
        expectedModel.setExerciseBook(getTypicalExerciseBook());
        actualModel.setExerciseBook(getTypicalExerciseBook());
    }

    @Test
    public void undo_modelAfterAdding_modelWithRegimeDeleted() {
        actualModel.addRegime(LEVEL_ONE_REGIME);
        assertUndoEventSuccess(addRegimeEvent, expectedModel, actualModel);
    }

    @Test
    public void redo_modelAfterUndoingAddRegime_modelWithRegimeAdded() {
        expectedModel.addRegime(LEVEL_ONE_REGIME);
        actualModel.addRegime(LEVEL_ONE_REGIME); // Simulating add regime event
        addRegimeEvent.undo(actualModel);
        assertRedoEventSuccess(addRegimeEvent, expectedModel, actualModel);
    }
}
