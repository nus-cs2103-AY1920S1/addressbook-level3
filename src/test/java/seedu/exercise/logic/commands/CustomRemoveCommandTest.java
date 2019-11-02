package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.model.util.DefaultPropertyBookUtil.getDefaultPropertyBook;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_REMARK;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.REMARK;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;

public class CustomRemoveCommandTest {

    private Model model = new ModelManager(getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
        new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new UserPrefs(), getDefaultPropertyBook());

    @BeforeEach
    public void reset() {
        model.getPropertyBook().addCustomProperty(REMARK);
    }

    @Test
    public void constructor_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CustomRemoveCommand(null));
    }

    @Test
    public void execute_customPropertyRemoved_success() throws CommandException {
        CustomRemoveCommand customRemoveCommand = new CustomRemoveCommand(VALID_FULL_NAME_REMARK);
        String commandResult = customRemoveCommand.execute(model).getFeedbackToUser();

        // Expected result
        String expectedMessage = String.format(CustomRemoveCommand.MESSAGE_SUCCESS, VALID_FULL_NAME_REMARK);
        Model expectedModel = new ModelManager(getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
            new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(),
            new UserPrefs(), getDefaultPropertyBook());

        assertEquals(commandResult, expectedMessage);
        assertEquals(model, expectedModel);
    }

}
