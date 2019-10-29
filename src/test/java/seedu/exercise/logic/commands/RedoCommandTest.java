package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.logic.commands.RedoCommand.MESSAGE_EMPTY_REDO_STACK;
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

public class RedoCommandTest {

    private Model actualModel;
    private Model expectedModel;
    private Event eventToRedo;
    private EventHistory eventHistory;

    @BeforeEach
    public void setUp() {
        actualModel = new ModelManager();
        expectedModel = new ModelManager();
        eventHistory = EventHistory.getInstance();
        eventHistory.reset();
        Exercise validExercise = new ExerciseBuilder().build();
        expectedModel.addExercise(validExercise);
        AddExerciseCommand addExerciseCommand = new AddExerciseCommand(validExercise);
        try {
            addExerciseCommand.execute(actualModel);
            eventToRedo = EventFactory.commandToEvent(addExerciseCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_emptyRedoStack_throwsCommandException() {
        RedoCommand redoCommand = new RedoCommand();
        assertThrows(CommandException.class,
                MESSAGE_EMPTY_REDO_STACK, () -> redoCommand.execute(actualModel));
    }

    @Test
    public void execute_nonEmptyRedoStack_success() {
        EventHistory.getInstance().undo(actualModel);
        String expectedString = String.format(RedoCommand.MESSAGE_SUCCESS, eventToRedo);
        assertCommandSuccess(new RedoCommand(), actualModel, expectedString, expectedModel);
    }
}
