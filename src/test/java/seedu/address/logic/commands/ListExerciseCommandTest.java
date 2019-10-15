package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExerciseAtIndex;
import static seedu.address.testutil.TypicalExercises.getTypicalDukeCooks;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.WorkoutPlannerUserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListExerciseCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalDukeCooks(), new WorkoutPlannerUserPrefs());
        expectedModel = new ModelManager(model.getDukeCooks(), new WorkoutPlannerUserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListExerciseCommand(), model, ListExerciseCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showExerciseAtIndex(model, INDEX_FIRST_EXERCISE);
        assertCommandSuccess(new ListExerciseCommand(), model, ListExerciseCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
