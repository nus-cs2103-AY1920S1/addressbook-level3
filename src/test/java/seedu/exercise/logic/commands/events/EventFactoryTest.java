package seedu.exercise.logic.commands.events;

import static seedu.exercise.logic.commands.events.EventFactory.MESSAGE_COMMAND_NOT_UNDOABLE;
import static seedu.exercise.logic.commands.events.EventTestUtil.ADD_EXERCISE_COMMAND_WITH_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.ADD_EXERCISE_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.ADD_REGIME_COMMAND_WITH_ADD_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.ADD_REGIME_COMMAND_WITH_EDIT_ONE_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.ADD_REGIME_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.DELETE_EXERCISE_COMMAND_WITH_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.DELETE_EXERCISE_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.DELETE_REGIME_COMMAND_WITH_DELETE_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.DELETE_REGIME_COMMAND_WITH_EDIT_ONE_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.DELETE_REGIME_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.EDIT_EXERCISE_COMMAND_WITH_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.EDIT_EXERCISE_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.EDIT_REGIME_BY_ADD_EVENT_ONE_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.EDIT_REGIME_BY_DELETE_EVENT_ONE_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.SCHEDULE_COMPLETE_COMMAND_LEVEL_ONE_REGIME_DATE_1_WITH_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.SCHEDULE_COMPLETE_LEVEL_ONE_REGIME_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.SCHEDULE_LEVEL_ONE_REGIME_EVENT_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.SCHEDULE_REGIME_COMMAND_LEVEL_ONE_REGIME_DATE_1_WITH_PAYLOAD;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertGeneratedAddEventEquals;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertGeneratedDeleteEventEquals;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertGeneratedEventEquals;
import static seedu.exercise.logic.commands.events.EventTestUtil.assertGeneratedScheduleEventEquals;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExercises;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;

public class EventFactoryTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        ReadOnlyResourceBook<Exercise> exerciseBook = new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR);
        exerciseBook.setResources(getTypicalExercises());
        model.setExerciseBook(exerciseBook);
    }

    @Test
    public void commandToEvent_invalidCommandWord_throwsCommandException() {
        String commandWord = "custom";
        UndoableCommand undoableCommand = () -> commandWord;
        String expectedMessage = String.format(MESSAGE_COMMAND_NOT_UNDOABLE, commandWord);
        assertThrows(CommandException.class, expectedMessage, () ->
                EventFactory.commandToEvent(undoableCommand));
    }

    @Test
    public void commandToEvent_addExerciseCommand_returnsAddExerciseEvent() {
        Event expectedEvent = new AddExerciseEvent(ADD_EXERCISE_EVENT_PAYLOAD);
        assertGeneratedEventEquals(expectedEvent, ADD_EXERCISE_COMMAND_WITH_PAYLOAD);
    }

    @Test
    public void commandToEvent_deleteExerciseCommand_returnsDeleteExerciseEvent() {
        Event expectedEvent = new DeleteExerciseEvent(DELETE_EXERCISE_EVENT_PAYLOAD);
        assertGeneratedEventEquals(expectedEvent, DELETE_EXERCISE_COMMAND_WITH_PAYLOAD);
    }

    @Test
    public void commandToEvent_editCommand_returnsEditEvent() {
        Event expectedEvent = new EditExerciseEvent(EDIT_EXERCISE_EVENT_PAYLOAD);
        assertGeneratedEventEquals(expectedEvent, EDIT_EXERCISE_COMMAND_WITH_PAYLOAD);
    }

    @Test
    public void generateEventFromAddCommand_addExerciseCommand_returnsAddExerciseEvent() {
        Event expectedEvent = new AddExerciseEvent(ADD_EXERCISE_EVENT_PAYLOAD);
        assertGeneratedAddEventEquals(expectedEvent, ADD_EXERCISE_COMMAND_WITH_PAYLOAD);
    }

    @Test
    public void generateEventFromAddCommand_addRegimeCommand_returnsAddRegimeEvent() {
        Event expectedEvent = new AddRegimeEvent(ADD_REGIME_EVENT_PAYLOAD);
        assertGeneratedAddEventEquals(expectedEvent, ADD_REGIME_COMMAND_WITH_ADD_PAYLOAD);
    }

    @Test
    public void generateEventFromAddCommand_addRegimeCommand_returnsEditRegimeEvent() {
        Event expectedEvent = new EditRegimeEvent(EDIT_REGIME_BY_ADD_EVENT_ONE_PAYLOAD);
        assertGeneratedAddEventEquals(expectedEvent, ADD_REGIME_COMMAND_WITH_EDIT_ONE_PAYLOAD);
    }

    @Test
    public void generateEventFromDeleteCommand_deleteExerciseCommand_returnsDeleteExerciseEvent() {
        Event expectedEvent = new DeleteExerciseEvent(DELETE_EXERCISE_EVENT_PAYLOAD);
        assertGeneratedDeleteEventEquals(expectedEvent, DELETE_EXERCISE_COMMAND_WITH_PAYLOAD);
    }

    @Test
    public void generateEventFromDeleteCommand_deleteRegimeCommand_returnsDeleteRegimeEvent() {
        Event expectedEvent = new DeleteRegimeEvent(DELETE_REGIME_EVENT_PAYLOAD);
        assertGeneratedDeleteEventEquals(expectedEvent, DELETE_REGIME_COMMAND_WITH_DELETE_PAYLOAD);
    }

    @Test
    public void generateEventFromDeleteCommand_deleteRegimeCommand_returnsEditRegimeEvent() {
        Event expectedEvent = new EditRegimeEvent(EDIT_REGIME_BY_DELETE_EVENT_ONE_PAYLOAD);
        assertGeneratedDeleteEventEquals(expectedEvent, DELETE_REGIME_COMMAND_WITH_EDIT_ONE_PAYLOAD);
    }

    @Test
    public void generateEventFromScheduleCommand_scheduleRegimeCommand_returnsScheduleRegimeEvent() {
        Event expectedEvent = new ScheduleRegimeEvent(SCHEDULE_LEVEL_ONE_REGIME_EVENT_PAYLOAD);
        assertGeneratedScheduleEventEquals(expectedEvent, SCHEDULE_REGIME_COMMAND_LEVEL_ONE_REGIME_DATE_1_WITH_PAYLOAD);
    }

    @Test
    public void generateEventFromScheduleCommand_scheduleCompleteCommand_returnsScheduleCompleteEvent() {
        Event expectedEvent = new ScheduleCompleteEvent(SCHEDULE_COMPLETE_LEVEL_ONE_REGIME_EVENT_PAYLOAD);
        assertGeneratedScheduleEventEquals(expectedEvent,
                SCHEDULE_COMPLETE_COMMAND_LEVEL_ONE_REGIME_DATE_1_WITH_PAYLOAD);
    }

}
