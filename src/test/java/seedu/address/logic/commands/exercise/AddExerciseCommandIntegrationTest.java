package seedu.address.logic.commands.exercise;

import static seedu.address.logic.commands.CommandTestUtil.assertExerciseCommandFailure;
import static seedu.address.testutil.exercise.TypicalExercises.getTypicalWorkoutPlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exercise.components.Exercise;

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
        Exercise exerciseInList = model.getWorkoutPlanner().getExerciseList().get(0);
        assertExerciseCommandFailure(new AddExerciseCommand(exerciseInList), model, AddExerciseCommand
                .MESSAGE_DUPLICATE_PERSON);
    }

}
