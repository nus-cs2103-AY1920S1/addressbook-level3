package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;
import static seedu.exercise.model.util.SampleDataUtil.getBasicExercises;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.ui.ListResourceType;

public class SuggestBasicCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExerciseBook(),
                new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
                getTypicalExerciseBook(), new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
                new UserPrefs());
        expectedModel = new ModelManager(model.getExerciseBookData(),
                new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
                getTypicalExerciseBook(), new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
                new UserPrefs());
        expectedModel.setSuggestions(Arrays.asList(getBasicExercises()));
    }

    @Test
    public void execute_suggestBasic_success() {
        String expectedMessage = SuggestBasicCommand.MESSAGE_SUCCESS;

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.SUGGESTION);
        assertCommandSuccess(new SuggestBasicCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        SuggestCommand suggestBasicCommand = new SuggestBasicCommand();

        // same object -> returns true
        assertTrue(suggestBasicCommand.equals(suggestBasicCommand));

        // same class -> returns true
        SuggestCommand suggestBasicCommandCopy = new SuggestBasicCommand();
        assertTrue(suggestBasicCommand.equals(suggestBasicCommandCopy));

        // different types -> returns false
        assertFalse(suggestBasicCommand.equals(1));

        // null -> returns false
        assertFalse(suggestBasicCommand.equals(null));

    }

}
