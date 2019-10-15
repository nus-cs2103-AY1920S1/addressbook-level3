package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExercises.getTypicalDukeCooks;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.WorkoutPlanner;
import seedu.address.model.WorkoutPlannerUserPrefs;

public class ClearExerciseCommandTest {

    @Test
    public void execute_emptyDukeCooks_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearExerciseCommand(), model, ClearExerciseCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyDukeCooks_success() {
        Model model = new ModelManager(getTypicalDukeCooks(), new WorkoutPlannerUserPrefs());
        Model expectedModel = new ModelManager(getTypicalDukeCooks(), new WorkoutPlannerUserPrefs());
        expectedModel.setDukeCooks(new WorkoutPlanner());

        assertCommandSuccess(new ClearExerciseCommand(), model, ClearExerciseCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
