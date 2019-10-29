package seedu.exercise.logic.commands.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.exercise.logic.commands.events.EventFactory.MESSAGE_COMMAND_NOT_UNDOABLE;
import static seedu.exercise.logic.commands.events.EventTestUtil.ADD_EXERCISE_COMMAND_WITH_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.ADD_EXERCISE_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.CLEAR_COMMAND_WITH_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.CLEAR_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.EDIT_EXERCISE_COMMAND_WITH_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.EDIT_EXERCISE_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.WALK_EXERCISE;
import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;

public class EventHistoryTest {

    private UndoableCommand undoableCommand;
    private EventHistory eventHistory;
    private Model actualModel;

    @BeforeEach
    public void setUp() {
        eventHistory = EventHistory.getInstance();
        eventHistory.reset();
        actualModel = new ModelManager();
    }

    @Test
    public void addCommandToUndoStack_nonUndoableCommand_throwsCommandException() {
        String commandWord = "custom";
        undoableCommand = () -> commandWord;

        String expectedMessage = String.format(MESSAGE_COMMAND_NOT_UNDOABLE, commandWord);
        assertThrows(CommandException.class, expectedMessage, () ->
                eventHistory.addCommandToUndoStack(undoableCommand));
    }

    @Test
    public void addCommandToUndoStack_undoableCommand_success() {
        undoableCommand = EDIT_EXERCISE_COMMAND_WITH_PAYLOAD;
        try {
            eventHistory.addCommandToUndoStack(undoableCommand);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        Event expectedEvent = new EditEvent(EDIT_EXERCISE_EVENT_PAYLOAD);
        Event actualEvent = eventHistory.getUndoStack().peek();
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void undo_emptyUndoStack_assertionError() {
        assertThrows(AssertionError.class, () -> eventHistory.undo(actualModel));
    }

    @Test
    public void undo_nonEmptyUndoStack_success() {
        try {
            eventHistory.addCommandToUndoStack(ADD_EXERCISE_COMMAND_WITH_PAYLOAD);
            actualModel.addExercise(WALK_EXERCISE);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        Event eventToUndo = eventHistory.undo(actualModel);

        Model expectedModel = new ModelManager();
        Event expectedEvent = new AddExerciseEvent(ADD_EXERCISE_EVENT_PAYLOAD);
        assertEquals(expectedEvent, eventToUndo);
        assertEquals(expectedModel, actualModel);
        assertFalse(() -> eventHistory.isRedoStackEmpty());
    }

    @Test
    public void redo_emptyRedoStack_assertionError() {
        assertThrows(AssertionError.class, () -> eventHistory.redo(actualModel));
    }

    @Test
    public void redo_nonEmptyRedoStack_success() {
        try {
            eventHistory.addCommandToUndoStack(CLEAR_COMMAND_WITH_PAYLOAD);
            eventHistory.undo(actualModel);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        Event eventToRedo = eventHistory.redo(actualModel);

        Model expectedModel = new ModelManager();
        Event expectedEvent = new ClearEvent(CLEAR_EVENT_PAYLOAD);
        assertEquals(expectedEvent, eventToRedo);
        assertEquals(expectedModel, actualModel);
    }

}
