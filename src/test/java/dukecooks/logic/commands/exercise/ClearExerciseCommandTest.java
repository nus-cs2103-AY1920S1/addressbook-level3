package dukecooks.logic.commands.exercise;

import static dukecooks.testutil.exercise.TypicalExercises.getTypicalWorkoutPlanner;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.workout.exercise.ExerciseCatalogue;

public class ClearExerciseCommandTest {

    @Test
    public void execute_emptyDukeCooks_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandTestUtil.assertCommandSuccess(new ClearExerciseCommand(), model,
                ClearExerciseCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyDukeCooks_success() {
        Model model = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());
        expectedModel.setExerciseCatalogue(new ExerciseCatalogue());

        CommandTestUtil.assertCommandSuccess(new ClearExerciseCommand(), model,
                ClearExerciseCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
