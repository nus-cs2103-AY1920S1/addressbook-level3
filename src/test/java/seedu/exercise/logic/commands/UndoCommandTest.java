package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.logic.commands.UndoCommand.MESSAGE_EMPTY_UNDO_STACK;
import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.events.Event;
import seedu.exercise.logic.commands.events.EventFactory;
import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.builder.ExerciseBuilder;

public class UndoCommandTest {

    private Model actualModel;
    private Model expectedModel;
    private Event eventToUndo;
    private EventHistory eventHistory;

    @BeforeEach
    public void setUp() {
        actualModel = new ModelManager();
        expectedModel = new ModelManager();
        eventHistory = EventHistory.getInstance();
        Exercise validExercise = new ExerciseBuilder().build();
        AddExerciseCommand addExerciseCommand = new AddExerciseCommand(validExercise);
        try {
            addExerciseCommand.execute(actualModel);
            eventToUndo = EventFactory.commandToEvent(addExerciseCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_emptyUndoStack_throwsCommandException() {
        eventHistory.reset();

        UndoCommand undoCommand = new UndoCommand();
        assertThrows(CommandException.class,
                MESSAGE_EMPTY_UNDO_STACK, () -> undoCommand.execute(actualModel));
    }

    @Test
    public void execute_nonEmptyUndoStack_success() {
        String expectedString = String.format(UndoCommand.MESSAGE_SUCCESS, eventToUndo);
        assertCommandSuccess(new UndoCommand(), actualModel, expectedString, expectedModel);
    }
}
