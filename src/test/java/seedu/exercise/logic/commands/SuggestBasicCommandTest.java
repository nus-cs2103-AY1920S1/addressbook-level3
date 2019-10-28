package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.model.util.DefaultPropertyBookUtil.getDefaultPropertyBook;
import static seedu.exercise.model.util.SampleDataUtil.getBasicExercises;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;

public class SuggestBasicCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
                getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
                new UserPrefs(), getDefaultPropertyBook());
        expectedModel = new ModelManager(model.getExerciseBookData(), new ReadOnlyResourceBook<>(),
                getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
                new UserPrefs(), getDefaultPropertyBook());
        expectedModel.setSuggestions(Arrays.asList(getBasicExercises()));
    }

    @Test
    public void execute_suggestBasic_success() {
        assertCommandSuccess(new SuggestBasicCommand(), model, SuggestBasicCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
