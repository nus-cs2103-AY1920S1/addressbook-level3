package seedu.exercise.logic.commands.events;

import static seedu.exercise.logic.commands.events.EventTestUtil.EDIT_EXERCISE_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.WALK_EXERCISE;
import static seedu.exercise.logic.commands.events.EventTestUtil.WALK_EXERCISE_EDITED;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertRedoEventSuccess;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertUndoEventSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;

public class EditExerciseEventTest {

    private Model actualModel;
    private Model expectedModel;
    private EditExerciseEvent editExerciseEvent;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager();
        actualModel = new ModelManager();
        editExerciseEvent = new EditExerciseEvent(EDIT_EXERCISE_EVENT_PAYLOAD);
    }

    @Test
    public void undo_modelAfterEditing_modelWithExerciseNotEdited() {
        expectedModel.addExercise(WALK_EXERCISE);
        actualModel.addExercise(WALK_EXERCISE);
        actualModel.setExercise(WALK_EXERCISE, WALK_EXERCISE_EDITED);
        assertUndoEventSuccess(editExerciseEvent, expectedModel, actualModel);
    }

    @Test
    public void redo_modelAfterUndoingEditExercise_modelWithExerciseEdited() {
        expectedModel.addExercise(WALK_EXERCISE_EDITED);
        actualModel.addExercise(WALK_EXERCISE_EDITED); // Simulating edit exercise event
        editExerciseEvent.undo(actualModel);
        assertRedoEventSuccess(editExerciseEvent, expectedModel, actualModel);
    }
}
