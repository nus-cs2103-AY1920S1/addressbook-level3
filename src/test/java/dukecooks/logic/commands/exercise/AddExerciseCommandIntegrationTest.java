package dukecooks.logic.commands.exercise;

import static dukecooks.testutil.exercise.TypicalExercises.getTypicalWorkoutPlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.workout.exercise.components.Exercise;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddExerciseCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());
    }

    @Test
    public void execute_duplicateExercise_throwsCommandException() {
        Exercise exerciseInList = model.getExerciseCatalogue().getExerciseList().get(0);
        CommandTestUtil.assertExerciseCommandFailure(new AddExerciseCommand(exerciseInList), model, AddExerciseCommand
                .MESSAGE_DUPLICATE_PERSON);
    }

}
