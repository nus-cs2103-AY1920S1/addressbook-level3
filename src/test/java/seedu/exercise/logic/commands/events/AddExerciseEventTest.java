package seedu.exercise.logic.commands.events;

import static seedu.exercise.logic.commands.events.EventTestUtil.ADD_EXERCISE_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.WALK_EXERCISE;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertRedoEventSuccess;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertUndoEventSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;

public class AddExerciseEventTest {

    private Model actualModel;
    private Model expectedModel;
    private AddExerciseEvent addExerciseEvent;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager();
        actualModel = new ModelManager();
        addExerciseEvent = new AddExerciseEvent(ADD_EXERCISE_EVENT_PAYLOAD);
    }

    @Test
    public void undo_modelAfterAdding_modelWithExerciseDeleted() {
        actualModel.addExercise(WALK_EXERCISE);
        assertUndoEventSuccess(addExerciseEvent, expectedModel, actualModel);
    }

    @Test
    public void redo_modelAfterUndoingAddExercise_modelWithExerciseAdded() {
        expectedModel.addExercise(WALK_EXERCISE);
        actualModel.addExercise(WALK_EXERCISE); // Simulating add exercise event
        addExerciseEvent.undo(actualModel);
        assertRedoEventSuccess(addExerciseEvent, expectedModel, actualModel);
    }
}
