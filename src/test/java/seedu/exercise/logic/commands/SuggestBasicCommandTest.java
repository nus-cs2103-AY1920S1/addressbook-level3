package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.model.util.DefaultPropertyManagerUtil.getDefaultPropertyManager;
import static seedu.exercise.testutil.TypicalExercises.getTypicalExerciseBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.RegimeBook;
import seedu.exercise.model.UserPrefs;

public class SuggestBasicCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExerciseBook(), new RegimeBook(),
                getTypicalExerciseBook(), new UserPrefs(), getDefaultPropertyManager());
        expectedModel = new ModelManager(model.getExerciseBookData(), new RegimeBook(),
                model.getDatabaseBook(), new UserPrefs(), getDefaultPropertyManager());
    }

    @Test
    public void execute_suggestBasic_success() {
        assertCommandSuccess(new SuggestBasicCommand(), model, SuggestBasicCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
