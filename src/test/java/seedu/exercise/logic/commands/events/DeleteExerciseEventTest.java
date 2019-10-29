package seedu.exercise.logic.commands.events;

import static seedu.exercise.logic.commands.events.EventTestUtil.DELETE_EXERCISE_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.WALK_EXERCISE;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertRedoEventSuccess;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertUndoEventSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;

public class DeleteExerciseEventTest {

    private Model actualModel;
    private Model expectedModel;
    private DeleteExerciseEvent deleteExerciseEvent;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager();
        actualModel = new ModelManager();
        deleteExerciseEvent = new DeleteExerciseEvent(DELETE_EXERCISE_EVENT_PAYLOAD);
        expectedModel.addExercise(WALK_EXERCISE);
        actualModel.addExercise(WALK_EXERCISE);
    }

    @Test
    public void undo_modelAfterDeleting_modelWithExerciseAdded() {
        actualModel.deleteExercise(WALK_EXERCISE);
        assertUndoEventSuccess(deleteExerciseEvent, expectedModel, actualModel);
    }

    @Test
    public void redo_modelAfterUndoingDeleteExercise_modelWithExerciseDeleted() {
        expectedModel.deleteExercise(WALK_EXERCISE);
        actualModel.deleteExercise(WALK_EXERCISE); // Simulating delete exercise event
        deleteExerciseEvent.undo(actualModel);
        assertRedoEventSuccess(deleteExerciseEvent, expectedModel, actualModel);
    }
}
