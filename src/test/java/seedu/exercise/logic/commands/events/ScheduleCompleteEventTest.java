package seedu.exercise.logic.commands.events;

import static seedu.exercise.logic.commands.events.EventTestUtil.LEVEL_ONE_REGIME_DATE_1_SCHEDULE;
import static seedu.exercise.logic.commands.events.EventTestUtil.SCHEDULE_COMPLETE_LEVEL_ONE_REGIME_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertRedoEventSuccess;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertUndoEventSuccess;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.resource.Exercise;

public class ScheduleCompleteEventTest {
    private Model actualModel;
    private Model expectedModel;
    private ScheduleCompleteEvent scheduleCompleteEvent;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager();
        actualModel = new ModelManager();
        scheduleCompleteEvent = new ScheduleCompleteEvent(SCHEDULE_COMPLETE_LEVEL_ONE_REGIME_EVENT_PAYLOAD);
    }

    @Test
    public void undo_modelAfterCompleting_modelWithScheduleNotComplete() {
        List<Exercise> scheduleExercises = LEVEL_ONE_REGIME_DATE_1_SCHEDULE.getExercises();
        for (Exercise exercise : scheduleExercises) {
            actualModel.addExercise(exercise);
        }
        expectedModel.addSchedule(LEVEL_ONE_REGIME_DATE_1_SCHEDULE);

        // Undo should remove completed exercises and add schedule back to scheduled list
        assertUndoEventSuccess(scheduleCompleteEvent, expectedModel, actualModel);
    }

    @Test
    public void redo_modelAfterUndoingScheduleComplete_modelWithScheduleCompleted() {
        List<Exercise> scheduleExercises = LEVEL_ONE_REGIME_DATE_1_SCHEDULE.getExercises();
        for (Exercise exercise : scheduleExercises) {
            expectedModel.addExercise(exercise);
        }
        actualModel.addSchedule(LEVEL_ONE_REGIME_DATE_1_SCHEDULE);
        actualModel.completeSchedule(LEVEL_ONE_REGIME_DATE_1_SCHEDULE); // Calls a schedule complete event
        scheduleCompleteEvent.undo(actualModel);
        assertRedoEventSuccess(scheduleCompleteEvent, expectedModel, actualModel);
    }
}
