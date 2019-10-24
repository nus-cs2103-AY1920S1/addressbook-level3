package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_FULL_NAME_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_FULL_NAME_REMARK;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_PARAMETER_TYPE_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_PARAMETER_TYPE_REMARK;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_PREFIX_NAME_RATING;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_PREFIX_NAME_REMARK;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.logic.commands.CustomCommand.MESSAGE_DUPLICATE_FULL_NAME;
import static seedu.exercise.logic.commands.CustomCommand.MESSAGE_DUPLICATE_PREFIX_NAME;
import static seedu.exercise.model.util.DefaultPropertyBookUtil.getDefaultPropertyBook;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.TypicalCustomProperties.RATING;
import static seedu.exercise.testutil.TypicalCustomProperties.REMARK;
import static seedu.exercise.testutil.exercise.TypicalExercises.getTypicalExerciseBook;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.testutil.CustomPropertyBuilder;

class CustomCommandTest {


    private Model model = new ModelManager(getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
            new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new UserPrefs(), getDefaultPropertyBook());

    @Test
    public void execute_customCommandCreated_success() {
        CustomProperty customPropertyToBeCreated = RATING;
        CustomCommand customCommand = new CustomCommand(customPropertyToBeCreated);
        String expectedMessage = String.format(CustomCommand.MESSAGE_SUCCESS, customPropertyToBeCreated);
        Model expectedModel = new ModelManager(getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
                new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(),
                new UserPrefs(), getDefaultPropertyBook());
        expectedModel.getPropertyBook().addCustomProperty(customPropertyToBeCreated);
        assertCommandSuccess(customCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateShortNameCustomProperty_throwsCommandException() {
        CustomProperty rating = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_RATING)
                .withFullName(VALID_FULL_NAME_RATING).withParameterType(VALID_PARAMETER_TYPE_RATING).build();
        CustomProperty duplicateShortName = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_RATING)
                .withFullName(VALID_FULL_NAME_REMARK).withParameterType(VALID_PARAMETER_TYPE_REMARK).build();
        Model model1 = new ModelManager(getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
                new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(),
                new UserPrefs(), getDefaultPropertyBook());
        model1.getPropertyBook().addCustomProperty(rating);
        CustomCommand customCommand = new CustomCommand(duplicateShortName);
        assertThrows(CommandException.class,
                MESSAGE_DUPLICATE_PREFIX_NAME, () -> customCommand.execute(model1));
    }

    @Test
    public void execute_duplicateFullNameCustomProperty_throwsCommandException() {
        CustomProperty rating = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_RATING)
                .withFullName(VALID_FULL_NAME_RATING).withParameterType(VALID_PARAMETER_TYPE_RATING).build();
        CustomProperty duplicateFullName = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_REMARK)
                .withFullName(VALID_FULL_NAME_RATING).withParameterType(VALID_PARAMETER_TYPE_REMARK).build();
        Model model1 = new ModelManager(getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
                new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(),
                new UserPrefs(), getDefaultPropertyBook());
        model1.getPropertyBook().addCustomProperty(rating);
        CustomCommand customCommand = new CustomCommand(duplicateFullName);
        assertThrows(CommandException.class,
                MESSAGE_DUPLICATE_FULL_NAME, () -> customCommand.execute(model1));
    }

    @Test
    public void testEquals() {
        CustomCommand ratingCustomPropertyCommand = new CustomCommand(RATING);
        CustomCommand remarkCustomPropertyCommand = new CustomCommand(REMARK);

        // same object -> returns true
        assertTrue(ratingCustomPropertyCommand.equals(ratingCustomPropertyCommand));

        // same values -> returns true
        CustomCommand ratingCustomPropertyCommandCopy = new CustomCommand(RATING);
        assertTrue(ratingCustomPropertyCommand.equals(ratingCustomPropertyCommandCopy));

        // different types -> returns false
        assertFalse(ratingCustomPropertyCommand.equals(1));

        // null -> returns false
        assertFalse(ratingCustomPropertyCommand.equals(null));

        // different exercises -> returns false
        assertFalse(ratingCustomPropertyCommand.equals(remarkCustomPropertyCommand));
    }
}
