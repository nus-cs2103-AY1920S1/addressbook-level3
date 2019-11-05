package seedu.exercise.logic.commands.events;

import static seedu.exercise.logic.commands.events.EventTestUtil.LEVEL_ONE_AND_TWO_REGIME_DATE_1_CONFLICT;
import static seedu.exercise.logic.commands.events.EventTestUtil.LEVEL_ONE_REGIME_DATE_1_SCHEDULE;
import static seedu.exercise.logic.commands.events.EventTestUtil.LEVEL_THREE_REGIME;
import static seedu.exercise.logic.commands.events.EventTestUtil.LEVEL_THREE_REGIME_DATE_1_SCHEDULE;
import static seedu.exercise.logic.commands.events.EventTestUtil.LEVEL_TWO_REGIME_DATE_1_SCHEDULE;
import static seedu.exercise.logic.commands.events.EventTestUtil.RESOLVE_LEVEL_ONE_AND_LEVEL_TWO_CONFLICT_AND_CREATE_LEVEL_THREE_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.RESOLVE_LEVEL_ONE_AND_LEVEL_TWO_CONFLICT_AND_TAKE_LEVEL_TWO_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertRedoEventSuccess;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertUndoEventSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;

public class ResolveEventTest {
    private Model actualModel;
    private Model expectedModel;
    private ResolveEvent resolveEvent;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager();
        actualModel = new ModelManager();
        expectedModel.setConflict(LEVEL_ONE_AND_TWO_REGIME_DATE_1_CONFLICT);
        actualModel.setConflict(LEVEL_ONE_AND_TWO_REGIME_DATE_1_CONFLICT);
    }

    @Test
    public void undo_modelAfterResolvingByCreatingNewRegime_modelWithConflictNotResolved() {
        resolveEvent = new ResolveEvent(RESOLVE_LEVEL_ONE_AND_LEVEL_TWO_CONFLICT_AND_CREATE_LEVEL_THREE_PAYLOAD);
        expectedModel.addSchedule(LEVEL_ONE_REGIME_DATE_1_SCHEDULE);

        actualModel.addRegime(LEVEL_THREE_REGIME);
        actualModel.addSchedule(LEVEL_THREE_REGIME_DATE_1_SCHEDULE);
        // Undo should remove the newly created regime and replaced the new schedule with the original schedule
        assertUndoEventSuccess(resolveEvent, expectedModel, actualModel);
    }

    @Test
    public void redo_modelAfterUndoingResolveByCreatingNewRegime_modelWithConflictResolved() {
        resolveEvent = new ResolveEvent(RESOLVE_LEVEL_ONE_AND_LEVEL_TWO_CONFLICT_AND_CREATE_LEVEL_THREE_PAYLOAD);
        expectedModel.addRegime(LEVEL_THREE_REGIME);
        expectedModel.addSchedule(LEVEL_THREE_REGIME_DATE_1_SCHEDULE);

        actualModel.addRegime(LEVEL_THREE_REGIME); // Simulates the resolved state
        actualModel.addSchedule(LEVEL_THREE_REGIME_DATE_1_SCHEDULE);

        resolveEvent.undo(actualModel);
        assertRedoEventSuccess(resolveEvent, expectedModel, actualModel);
    }

    @Test
    public void undo_modelAfterResolvingByTakingExistingRegime_modelWithConflictNotResolved() {
        resolveEvent = new ResolveEvent(RESOLVE_LEVEL_ONE_AND_LEVEL_TWO_CONFLICT_AND_TAKE_LEVEL_TWO_PAYLOAD);
        expectedModel.addSchedule(LEVEL_ONE_REGIME_DATE_1_SCHEDULE);
        actualModel.addSchedule(LEVEL_TWO_REGIME_DATE_1_SCHEDULE);
        assertUndoEventSuccess(resolveEvent, expectedModel, actualModel);
    }

    @Test

    public void redo_modelAfterUndoingResolveByTakingExistingRegime_modelWithConflictResolved() {
        resolveEvent = new ResolveEvent(RESOLVE_LEVEL_ONE_AND_LEVEL_TWO_CONFLICT_AND_TAKE_LEVEL_TWO_PAYLOAD);
        expectedModel.addSchedule(LEVEL_TWO_REGIME_DATE_1_SCHEDULE);
        actualModel.addSchedule(LEVEL_TWO_REGIME_DATE_1_SCHEDULE);
        resolveEvent.undo(actualModel);
        assertRedoEventSuccess(resolveEvent, expectedModel, actualModel);
    }
}
