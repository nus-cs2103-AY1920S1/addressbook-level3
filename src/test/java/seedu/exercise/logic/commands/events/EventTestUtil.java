package seedu.exercise.logic.commands.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.logic.commands.events.AddExerciseEvent.KEY_EXERCISE_TO_ADD;
import static seedu.exercise.logic.commands.events.AddRegimeEvent.KEY_REGIME_TO_ADD;
import static seedu.exercise.logic.commands.events.ClearEvent.KEY_EXERCISE_BOOK_CLEARED;
import static seedu.exercise.logic.commands.events.DeleteExerciseEvent.KEY_EXERCISE_TO_DELETE;
import static seedu.exercise.logic.commands.events.DeleteRegimeEvent.KEY_REGIME_TO_DELETE;
import static seedu.exercise.logic.commands.events.EditExerciseEvent.KEY_EDITED_EXERCISE;
import static seedu.exercise.logic.commands.events.EditExerciseEvent.KEY_ORIGINAL_EXERCISE;
import static seedu.exercise.logic.commands.events.EditRegimeEvent.KEY_EDITED_REGIME;
import static seedu.exercise.logic.commands.events.EditRegimeEvent.KEY_IS_REGIME_EDITED;
import static seedu.exercise.logic.commands.events.EditRegimeEvent.KEY_ORIGINAL_REGIME;
import static seedu.exercise.logic.commands.events.ResolveEvent.KEY_CONFLICT;
import static seedu.exercise.logic.commands.events.ResolveEvent.KEY_RESOLVED_SCHEDULE;
import static seedu.exercise.logic.commands.events.ScheduleCompleteEvent.KEY_TO_COMPLETE;
import static seedu.exercise.logic.commands.events.ScheduleRegimeEvent.KEY_TO_SCHEDULE;
import static seedu.exercise.testutil.CommonTestData.VALID_CALORIES_BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalDates.DATE_1;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.WALK;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExercises;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_FIFTH;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_FIRST;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_FOURTH;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_SECOND;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_THIRD;

import java.util.List;

import seedu.exercise.MainApp;
import seedu.exercise.commons.core.State;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.AddCommand;
import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.AddRegimeCommand;
import seedu.exercise.logic.commands.ClearCommand;
import seedu.exercise.logic.commands.DeleteCommand;
import seedu.exercise.logic.commands.DeleteExerciseCommand;
import seedu.exercise.logic.commands.DeleteRegimeCommand;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.logic.commands.ResolveCommand;
import seedu.exercise.logic.commands.ScheduleCommand;
import seedu.exercise.logic.commands.ScheduleCompleteCommand;
import seedu.exercise.logic.commands.ScheduleRegimeCommand;
import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.testutil.builder.ConflictBuilder;
import seedu.exercise.testutil.builder.EditExerciseDescriptorBuilder;
import seedu.exercise.testutil.builder.ExerciseBuilder;
import seedu.exercise.testutil.builder.RegimeBuilder;
import seedu.exercise.testutil.builder.ScheduleBuilder;

/**
 * Contains helper methods for testing classes in the 'events' package.
 */
class EventTestUtil {

    // Exercises
    static final Exercise WALK_EXERCISE = WALK;
    static final Exercise WALK_EXERCISE_EDITED = new ExerciseBuilder(WALK_EXERCISE)
            .withCalories(VALID_CALORIES_BASKETBALL).build();

    // Regimes
    static final String LEVEL_ONE_REGIME_NAME = "R1";
    static final String LEVEL_TWO_REGIME_NAME = "R2";
    static final String LEVEL_THREE_REGIME_NAME = "R3";
    static final Regime LEVEL_ONE_REGIME = new RegimeBuilder()
            .withName(LEVEL_ONE_REGIME_NAME)
            .withExerciseList(getTypicalExercises().subList(0, 3))
            .build();
    static final Regime LEVEL_ONE_REGIME_WITH_ONE_ADDITIONAL = new RegimeBuilder(LEVEL_ONE_REGIME)
            .withExercise(getTypicalExercises().get(3))
            .build();
    static final Regime LEVEL_ONE_REGIME_WITH_TWO_ADDITIONAL = new RegimeBuilder(LEVEL_ONE_REGIME)
            .withExercise(getTypicalExercises().get(3))
            .withExercise(getTypicalExercises().get(4))
            .build();
    static final Regime LEVEL_TWO_REGIME = new RegimeBuilder()
            .withName(LEVEL_TWO_REGIME_NAME)
            .withExerciseList(getTypicalExercises().subList(3, 6))
            .build();
    // Has the last 2 exercises of Regime 1 and first exercise of regime 2.
    static final Regime LEVEL_THREE_REGIME = new RegimeBuilder()
            .withName(LEVEL_THREE_REGIME_NAME)
            .withExerciseList(getTypicalExercises().subList(1, 4))
            .build();

    // Schedules
    static final Schedule LEVEL_ONE_REGIME_DATE_1_SCHEDULE = new ScheduleBuilder()
            .withRegime(LEVEL_ONE_REGIME)
            .withDate(DATE_1)
            .build();
    static final Schedule LEVEL_TWO_REGIME_DATE_1_SCHEDULE = new ScheduleBuilder()
            .withRegime(LEVEL_TWO_REGIME)
            .withDate(DATE_1)
            .build();
    static final Schedule LEVEL_THREE_REGIME_DATE_1_SCHEDULE = new ScheduleBuilder()
            .withRegime(LEVEL_THREE_REGIME)
            .withDate(DATE_1)
            .build();

    // Conflict
    static final Conflict LEVEL_ONE_AND_TWO_REGIME_DATE_1_CONFLICT = new ConflictBuilder()
            .withScheduled(LEVEL_ONE_REGIME_DATE_1_SCHEDULE)
            .withConflict(LEVEL_TWO_REGIME_DATE_1_SCHEDULE)
            .build();

    // Exercise Book
    static final ReadOnlyResourceBook<Exercise> EXERCISE_BOOK = getTypicalExerciseBook();

    // Exercise Payloads
    static final EventPayload<Exercise> ADD_EXERCISE_EVENT_PAYLOAD = new EventPayload<Exercise>()
            .put(KEY_EXERCISE_TO_ADD, WALK_EXERCISE);
    static final EventPayload<Exercise> DELETE_EXERCISE_EVENT_PAYLOAD = new EventPayload<Exercise>()
            .put(KEY_EXERCISE_TO_DELETE, WALK_EXERCISE);
    static final EventPayload<Exercise> EDIT_EXERCISE_EVENT_PAYLOAD = new EventPayload<Exercise>()
            .put(KEY_ORIGINAL_EXERCISE, WALK_EXERCISE)
            .put(KEY_EDITED_EXERCISE, WALK_EXERCISE_EDITED);

    // Regime Payloads
    static final EventPayload<Object> ADD_REGIME_EVENT_PAYLOAD = new EventPayload<>()
            .put(KEY_IS_REGIME_EDITED, false)
            .put(KEY_REGIME_TO_ADD, LEVEL_ONE_REGIME);
    static final EventPayload<Object> DELETE_REGIME_EVENT_PAYLOAD = new EventPayload<>()
            .put(KEY_IS_REGIME_EDITED, false)
            .put(KEY_REGIME_TO_DELETE, LEVEL_ONE_REGIME);
    static final EventPayload<Object> EDIT_REGIME_BY_ADD_EVENT_ONE_PAYLOAD = new EventPayload<>()
            .put(KEY_IS_REGIME_EDITED, true)
            .put(KEY_ORIGINAL_REGIME, LEVEL_ONE_REGIME)
            .put(KEY_EDITED_REGIME, LEVEL_ONE_REGIME_WITH_ONE_ADDITIONAL);
    static final EventPayload<Object> EDIT_REGIME_BY_ADD_EVENT_TWO_PAYLOAD = new EventPayload<>()
            .put(KEY_IS_REGIME_EDITED, true)
            .put(KEY_ORIGINAL_REGIME, LEVEL_ONE_REGIME)
            .put(KEY_EDITED_REGIME, LEVEL_ONE_REGIME_WITH_TWO_ADDITIONAL);
    static final EventPayload<Object> EDIT_REGIME_BY_DELETE_EVENT_ONE_PAYLOAD = new EventPayload<>()
            .put(KEY_IS_REGIME_EDITED, true)
            .put(KEY_ORIGINAL_REGIME, LEVEL_ONE_REGIME_WITH_ONE_ADDITIONAL)
            .put(KEY_EDITED_REGIME, LEVEL_ONE_REGIME);
    static final EventPayload<Object> EDIT_REGIME_BY_DELETE_EVENT_TWO_PAYLOAD = new EventPayload<>()
            .put(KEY_IS_REGIME_EDITED, true)
            .put(KEY_ORIGINAL_REGIME, LEVEL_ONE_REGIME_WITH_TWO_ADDITIONAL)
            .put(KEY_EDITED_REGIME, LEVEL_ONE_REGIME);

    // Schedule Payloads
    static final EventPayload<Schedule> SCHEDULE_LEVEL_ONE_REGIME_EVENT_PAYLOAD = new EventPayload<Schedule>()
            .put(KEY_TO_SCHEDULE, LEVEL_ONE_REGIME_DATE_1_SCHEDULE);
    static final EventPayload<Schedule> SCHEDULE_LEVEL_TWO_REGIME_EVENT_PAYLOAD = new EventPayload<Schedule>()
            .put(KEY_TO_SCHEDULE, LEVEL_TWO_REGIME_DATE_1_SCHEDULE);
    static final EventPayload<Schedule> SCHEDULE_COMPLETE_LEVEL_ONE_REGIME_EVENT_PAYLOAD =
            new EventPayload<Schedule>()
                    .put(KEY_TO_COMPLETE, LEVEL_ONE_REGIME_DATE_1_SCHEDULE);

    // Resolve Conflict Payloads
    static final EventPayload<Object> RESOLVE_LEVEL_ONE_AND_LEVEL_TWO_CONFLICT_AND_CREATE_LEVEL_THREE_PAYLOAD =
            new EventPayload<>()
                    .put(KEY_CONFLICT, LEVEL_ONE_AND_TWO_REGIME_DATE_1_CONFLICT)
                    .put(KEY_RESOLVED_SCHEDULE, LEVEL_THREE_REGIME_DATE_1_SCHEDULE);
    static final EventPayload<Object> RESOLVE_LEVEL_ONE_AND_LEVEL_TWO_CONFLICT_AND_TAKE_LEVEL_TWO_PAYLOAD =
            new EventPayload<>()
                    .put(KEY_CONFLICT, LEVEL_ONE_AND_TWO_REGIME_DATE_1_CONFLICT)
                    .put(KEY_RESOLVED_SCHEDULE, LEVEL_TWO_REGIME_DATE_1_SCHEDULE);

    // Exercise Book Payloads
    static final EventPayload<ReadOnlyResourceBook<Exercise>> CLEAR_EVENT_PAYLOAD =
            new EventPayload<ReadOnlyResourceBook<Exercise>>()
                    .put(KEY_EXERCISE_BOOK_CLEARED, getTypicalExerciseBook());

    // Commands
    static final AddExerciseCommand ADD_EXERCISE_COMMAND_WITH_PAYLOAD = new AddExerciseCommand(WALK_EXERCISE);
    static final DeleteExerciseCommand DELETE_EXERCISE_COMMAND_WITH_PAYLOAD =
            new DeleteExerciseCommand(INDEX_ONE_BASED_FIRST);
    static final EditCommand EDIT_EXERCISE_COMMAND_WITH_PAYLOAD = new EditCommand(
            INDEX_ONE_BASED_FIRST,
            new EditExerciseDescriptorBuilder(WALK_EXERCISE_EDITED).build());
    static final AddRegimeCommand ADD_REGIME_COMMAND_WITH_ADD_PAYLOAD = new AddRegimeCommand(
            List.of(INDEX_ONE_BASED_FIRST, INDEX_ONE_BASED_SECOND, INDEX_ONE_BASED_THIRD),
            LEVEL_ONE_REGIME.getRegimeName()
    );
    static final AddRegimeCommand ADD_REGIME_COMMAND_WITH_EDIT_ONE_PAYLOAD = new AddRegimeCommand(
            List.of(INDEX_ONE_BASED_FOURTH),
            LEVEL_ONE_REGIME.getRegimeName()
    );
    static final AddRegimeCommand ADD_REGIME_COMMAND_WITH_EDIT_TWO_PAYLOAD = new AddRegimeCommand(
            List.of(INDEX_ONE_BASED_FOURTH, INDEX_ONE_BASED_FIFTH),
            LEVEL_ONE_REGIME.getRegimeName()
    );
    static final DeleteRegimeCommand DELETE_REGIME_COMMAND_WITH_DELETE_PAYLOAD = new DeleteRegimeCommand(
            LEVEL_ONE_REGIME.getRegimeName(),
            null
    );
    static final DeleteRegimeCommand DELETE_REGIME_COMMAND_WITH_EDIT_ONE_PAYLOAD = new DeleteRegimeCommand(
            LEVEL_ONE_REGIME.getRegimeName(),
            List.of(INDEX_ONE_BASED_FOURTH)
    );
    static final DeleteRegimeCommand DELETE_REGIME_COMMAND_WITH_EDIT_TWO_PAYLOAD = new DeleteRegimeCommand(
            LEVEL_ONE_REGIME.getRegimeName(),
            List.of(INDEX_ONE_BASED_FOURTH, INDEX_ONE_BASED_FIFTH)
    );
    static final ClearCommand CLEAR_COMMAND_WITH_PAYLOAD = new ClearCommand();
    static final ScheduleRegimeCommand SCHEDULE_REGIME_COMMAND_LEVEL_ONE_REGIME_DATE_1_WITH_PAYLOAD =
            new ScheduleRegimeCommand(LEVEL_ONE_REGIME.getRegimeName(), DATE_1
    );
    static final ScheduleRegimeCommand SCHEDULE_REGIME_COMMAND_LEVEL_TWO_REGIME_DATE_1_WITH_PAYLOAD =
            new ScheduleRegimeCommand(
            LEVEL_TWO_REGIME.getRegimeName(),
            DATE_1
    );
    static final ScheduleCompleteCommand SCHEDULE_COMPLETE_COMMAND_LEVEL_ONE_REGIME_DATE_1_WITH_PAYLOAD =
            new ScheduleCompleteCommand(INDEX_ONE_BASED_FIRST);
    static final ResolveCommand RESOLVE_COMMAND_LEVEL_ONE_AND_TWO_CONFLICT_AND_CREATE_THREE_WITH_PAYLOAD =
            new ResolveCommand(LEVEL_THREE_REGIME.getRegimeName(),
                    List.of(Index.fromZeroBased(1),
                            Index.fromZeroBased(2)),
                    List.of(Index.fromZeroBased(0)));
    static final ResolveCommand RESOLVE_COMMAND_LEVEL_ONE_AND_TWO_CONFLICT_AND_TAKE_TWO_WITH_PAYLOAD =
            new ResolveCommand(LEVEL_TWO_REGIME.getRegimeName(),
                    List.of(),
                    List.of());

    private static final Model model = new ModelManager();

    // Executing commands to store eventPayload
    static {
        generateExerciseRelatedCommandPayload();
        generateRegimeRelatedCommandPayload();
        generateScheduleRelatedCommandPayload();
    }

    /**
     * Undoes the given {@code event}, confirms that <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    static void assertUndoEventSuccess(Event event, Model expectedModel, Model actualModel) {
        event.undo(actualModel);
        assertEquals(expectedModel, actualModel);
    }

    /**
     * Redoes the given {@code event}, confirms that <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    static void assertRedoEventSuccess(Event event, Model expectedModel, Model actualModel) {
        event.redo(actualModel);
        assertEquals(expectedModel, actualModel);
    }

    /**
     * Generates an {@code Event} from the {@code UndoableCommand} using <br>
     * the {@code EventFactory.commandToEvent} method and confirms that <br>
     * - the generated {@code Event} matches {@code expectedEvent}
     */
    static void assertGeneratedEventEquals(Event expectedEvent, UndoableCommand undoableCommand) {
        Event actualEvent = new EditExerciseEvent(new EventPayload<>());
        try {
            actualEvent = EventFactory.commandToEvent(undoableCommand);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        assertEquals(expectedEvent, actualEvent);
    }

    /**
     * Generates an {@code Event} from the {@code AddCommand} using <br>
     * the {@code EventFactory.generateEventFromAddCommand} method <br>
     * and confirms that <br>
     * - the generated {@code Event} matches {@code expectedEvent}
     */
    static void assertGeneratedAddEventEquals(Event expectedEvent, AddCommand addCommand) {
        Event actualEvent = new AddExerciseEvent(new EventPayload<>());
        try {
            actualEvent = EventFactory.generateEventFromAddCommand(addCommand);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        assertEquals(expectedEvent, actualEvent);
    }

    /**
     * Generates an {@code Event} from the {@code DeleteCommand} using <br>
     * the {@code EventFactory.generateEventFromDeleteCommand} method <br>
     * and confirms that <br>
     * - the generated {@code Event} matches {@code expectedEvent}
     */
    static void assertGeneratedDeleteEventEquals(Event expectedEvent, DeleteCommand deleteCommand) {
        Event actualEvent = new DeleteExerciseEvent(new EventPayload<>());
        try {
            actualEvent = EventFactory.generateEventFromDeleteCommand(deleteCommand);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        assertEquals(expectedEvent, actualEvent);
    }

    /**
     * Generates an {@code Event} from the {@code ScheduleCommand} using <br>
     * the {@code EventFactory.generateEventFromScheduleCommand} method <br>
     * and confirms that <br>
     * - the generated {@code Event} matches {@code expectedEvent}
     */
    static void assertGeneratedScheduleEventEquals(Event expectedEvent, ScheduleCommand scheduleCommand) {
        Event actualEvent = new ScheduleRegimeEvent(new EventPayload<>());
        try {
            actualEvent = EventFactory.generateEventFromScheduleCommand(scheduleCommand);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        assertEquals(expectedEvent, actualEvent);
    }

    /**
     * Updates payload of all exercise-related commands. Payload is only
     * updated upon execution of the commands on model.
     */
    private static void generateExerciseRelatedCommandPayload() {
        try {
            ADD_EXERCISE_COMMAND_WITH_PAYLOAD.execute(model);
            DELETE_EXERCISE_COMMAND_WITH_PAYLOAD.execute(model);
            ADD_EXERCISE_COMMAND_WITH_PAYLOAD.execute(model);
            EDIT_EXERCISE_COMMAND_WITH_PAYLOAD.execute(model);
            resetExerciseBook();
            CLEAR_COMMAND_WITH_PAYLOAD.execute(model);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }


    /**
     * Updates payload of all regime-related commands. Payload is only
     * updated upon execution of the commands on model.
     */
    private static void generateRegimeRelatedCommandPayload() {
        try {
            resetExerciseBook();
            ADD_REGIME_COMMAND_WITH_ADD_PAYLOAD.execute(model);
            ADD_REGIME_COMMAND_WITH_EDIT_ONE_PAYLOAD.execute(model);
            DELETE_REGIME_COMMAND_WITH_EDIT_ONE_PAYLOAD.execute(model);
            ADD_REGIME_COMMAND_WITH_EDIT_TWO_PAYLOAD.execute(model);
            DELETE_REGIME_COMMAND_WITH_EDIT_TWO_PAYLOAD.execute(model);
            DELETE_REGIME_COMMAND_WITH_DELETE_PAYLOAD.execute(model);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates payload of all schedule-related commands. Payload is only
     * updated upon execution of the commands on model.
     */
    private static void generateScheduleRelatedCommandPayload() {
        try {
            model.addRegime(LEVEL_ONE_REGIME);
            model.addRegime(LEVEL_TWO_REGIME);
            SCHEDULE_REGIME_COMMAND_LEVEL_ONE_REGIME_DATE_1_WITH_PAYLOAD.execute(model);
            SCHEDULE_COMPLETE_COMMAND_LEVEL_ONE_REGIME_DATE_1_WITH_PAYLOAD.execute(model);

            model.addSchedule(LEVEL_ONE_REGIME_DATE_1_SCHEDULE);
            SCHEDULE_REGIME_COMMAND_LEVEL_TWO_REGIME_DATE_1_WITH_PAYLOAD.execute(model);
            RESOLVE_COMMAND_LEVEL_ONE_AND_TWO_CONFLICT_AND_CREATE_THREE_WITH_PAYLOAD.execute(model);

            model.removeSchedule(LEVEL_THREE_REGIME_DATE_1_SCHEDULE);
            model.addSchedule(LEVEL_ONE_REGIME_DATE_1_SCHEDULE);
            SCHEDULE_REGIME_COMMAND_LEVEL_TWO_REGIME_DATE_1_WITH_PAYLOAD.execute(model);
            RESOLVE_COMMAND_LEVEL_ONE_AND_TWO_CONFLICT_AND_TAKE_TWO_WITH_PAYLOAD.execute(model);
            MainApp.setState(State.IN_CONFLICT);

        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    /**
     * A convenient method to populate the Exercise Book with the default
     * Exercises.
     */
    private static void resetExerciseBook() {
        EXERCISE_BOOK.setResources(getTypicalExercises());
        model.setExerciseBook(EXERCISE_BOOK);
    }
}
