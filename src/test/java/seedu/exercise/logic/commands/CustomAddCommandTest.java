package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CustomAddCommand.MESSAGE_DUPLICATE_FULL_NAME;
import static seedu.exercise.logic.commands.CustomAddCommand.MESSAGE_DUPLICATE_PREFIX_NAME;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_REMARK;
import static seedu.exercise.testutil.CommonTestData.VALID_PARAMETER_TYPE_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_PARAMETER_TYPE_REMARK;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_REMARK;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.RATING;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.REMARK;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.property.PropertyBook;
import seedu.exercise.model.property.custom.CustomProperty;
import seedu.exercise.testutil.builder.CustomPropertyBuilder;

class CustomAddCommandTest {

    private Model model = new ModelManager(getTypicalExerciseBook(),
        new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
        new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
        new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
        new UserPrefs());
    private PropertyBook propertyBook = PropertyBook.getInstance();

    @BeforeEach
    public void setUp() {
        propertyBook.clearCustomProperties();
    }

    @Test
    public void execute_customCommandCreated_success() throws CommandException {
        CustomAddCommand customAddCommand = new CustomAddCommand(RATING);
        String actualMessage = customAddCommand.execute(model).getFeedbackToUser();

        String expectedMessage = String.format(CustomAddCommand.MESSAGE_SUCCESS, RATING);
        Set<CustomProperty> expectedSet = Set.of(RATING);
        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSet, propertyBook.getCustomProperties());
    }

    @Test
    public void execute_duplicateShortNameCustomProperty_throwsCommandException() {
        CustomProperty rating = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_RATING)
            .withFullName(VALID_FULL_NAME_RATING).withParameterType(VALID_PARAMETER_TYPE_RATING).build();
        CustomProperty duplicateShortName = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_RATING)
            .withFullName(VALID_FULL_NAME_REMARK).withParameterType(VALID_PARAMETER_TYPE_REMARK).build();
        propertyBook.addCustomProperty(rating);
        CustomAddCommand customAddCommand = new CustomAddCommand(duplicateShortName);
        assertThrows(CommandException.class,
            MESSAGE_DUPLICATE_PREFIX_NAME, () -> customAddCommand.execute(model));
    }

    @Test
    public void execute_duplicateFullNameCustomProperty_throwsCommandException() {
        CustomProperty rating = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_RATING)
            .withFullName(VALID_FULL_NAME_RATING).withParameterType(VALID_PARAMETER_TYPE_RATING).build();
        CustomProperty duplicateFullName = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_REMARK)
            .withFullName(VALID_FULL_NAME_RATING).withParameterType(VALID_PARAMETER_TYPE_REMARK).build();
        propertyBook.addCustomProperty(rating);
        CustomAddCommand customAddCommand = new CustomAddCommand(duplicateFullName);
        assertThrows(CommandException.class,
            MESSAGE_DUPLICATE_FULL_NAME, () -> customAddCommand.execute(model));
    }

    @Test
    public void testEquals() {
        CustomAddCommand ratingCustomPropertyCommand = new CustomAddCommand(RATING);
        CustomAddCommand remarkCustomPropertyCommand = new CustomAddCommand(REMARK);

        // same object -> returns true
        assertTrue(ratingCustomPropertyCommand.equals(ratingCustomPropertyCommand));

        // same values -> returns true
        CustomCommand ratingCustomPropertyCommandCopy = new CustomAddCommand(RATING);
        assertTrue(ratingCustomPropertyCommand.equals(ratingCustomPropertyCommandCopy));

        // different types -> returns false
        assertFalse(ratingCustomPropertyCommand.equals(1));

        // null -> returns false
        assertFalse(ratingCustomPropertyCommand.equals(null));

        // different exercises -> returns false
        assertFalse(ratingCustomPropertyCommand.equals(remarkCustomPropertyCommand));
    }
}
