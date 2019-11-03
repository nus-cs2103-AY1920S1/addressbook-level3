package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.builder.ExerciseBuilder;
import seedu.exercise.ui.ListResourceType;

/**
 * Contains integration tests (interaction with the Model) for {@code AddExerciseCommand}.
 */
public class AddExerciseCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExerciseBook(), new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(),
            new ReadOnlyResourceBook<>(), new UserPrefs());
    }

    @Test
    public void execute_newExercise_success() {
        Exercise validExercise = new ExerciseBuilder().build();

        Model expectedModel = new ModelManager(model.getExerciseBookData(), new ReadOnlyResourceBook<>(),
            new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new UserPrefs());
        expectedModel.addExercise(validExercise);

        CommandResult expectedCommandResult = new CommandResult(
            String.format(AddExerciseCommand.MESSAGE_SUCCESS, validExercise),
            ListResourceType.EXERCISE);
        assertCommandSuccess(new AddExerciseCommand(validExercise), model, expectedCommandResult, expectedModel);

    }

    @Test
    public void execute_duplicateExercise_throwsCommandException() {
        Exercise exerciseInList = model.getExerciseBookData().getResourceList().get(0);
        assertCommandFailure(new AddExerciseCommand(exerciseInList), model,
            AddExerciseCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

}
