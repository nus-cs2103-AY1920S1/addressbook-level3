package seedu.exercise.logic.commands.events;

import static seedu.exercise.logic.commands.events.EventTestUtil.LEVEL_ONE_REGIME_DATE_1_SCHEDULE;
import static seedu.exercise.logic.commands.events.EventTestUtil.SCHEDULE_LEVEL_ONE_REGIME_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertRedoEventSuccess;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertUndoEventSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;

public class ScheduleRegimeEventTest {
    private Model actualModel;
    private Model expectedModel;
    private ScheduleRegimeEvent scheduleRegimeEvent;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager();
        actualModel = new ModelManager();
        scheduleRegimeEvent = new ScheduleRegimeEvent(SCHEDULE_LEVEL_ONE_REGIME_EVENT_PAYLOAD);
    }

    @Test
    public void undo_modelAfterScheduling_modelWithScheduleRemoved() {
        actualModel.addSchedule(LEVEL_ONE_REGIME_DATE_1_SCHEDULE);
        assertUndoEventSuccess(scheduleRegimeEvent, expectedModel, actualModel);
    }

    @Test
    public void redo_modelAfterUndoingScheduleRegime_modelWithScheduleAdded() {
        expectedModel.addSchedule(LEVEL_ONE_REGIME_DATE_1_SCHEDULE);
        actualModel.addSchedule(LEVEL_ONE_REGIME_DATE_1_SCHEDULE); // Simulating schedule regime event
        scheduleRegimeEvent.undo(actualModel);
        assertRedoEventSuccess(scheduleRegimeEvent, expectedModel, actualModel);
    }
}
