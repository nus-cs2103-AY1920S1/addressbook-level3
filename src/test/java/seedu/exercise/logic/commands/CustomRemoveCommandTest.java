package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_END_DATE;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_REMARK;
import static seedu.exercise.testutil.TestUtil.assertCommonEqualsTest;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.REMARK;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.WALK;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_FIRST;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_SECOND;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_VERY_LARGE_NUMBER;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
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
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.builder.ExerciseBookBuilder;
import seedu.exercise.testutil.builder.ExerciseBuilder;

//@@author weihaw08
public class CustomRemoveCommandTest {


    private Map<String, String> customPropertiesMap = Map.of("Remark", "Text");
    private Exercise firstExercise = new ExerciseBuilder().withCustomProperties(customPropertiesMap).build();
    private Exercise secondExercise = new ExerciseBuilder(WALK).withCustomProperties(customPropertiesMap).build();
    private ReadOnlyResourceBook<Exercise> exerciseBook = new ExerciseBookBuilder().withExercise(firstExercise)
        .withExercise(secondExercise).build();
    private Model model = new ModelManager(exerciseBook,
        new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
        new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
        new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR), new UserPrefs());
    private PropertyBook propertyBook = PropertyBook.getInstance();

    @BeforeEach
    public void setUp() {
        propertyBook.clearCustomProperties();
        propertyBook.addCustomProperty(REMARK);
        model.setExerciseBook(exerciseBook);
    }

    @Test
    public void constructor_withNull_throwsNullPointerException() {
        // null and empty Optional
        assertThrows(NullPointerException.class, () -> new CustomRemoveCommand(null, Optional.empty()));

        // null and nonempty Optional
        assertThrows(NullPointerException.class, () -> new CustomRemoveCommand(null,
            Optional.of(INDEX_ONE_BASED_FIRST)));

        // valid String and null
        assertThrows(NullPointerException.class, () -> new CustomRemoveCommand(VALID_FULL_NAME_REMARK, null));
    }

    @Test
    public void execute_customPropertyRemovedFromBook_success() throws CommandException {
        CustomRemoveCommand customRemoveCommand = new CustomRemoveCommand(VALID_FULL_NAME_REMARK, Optional.empty());

        // Expected result
        Exercise updatedExercise = new ExerciseBuilder().build();
        ReadOnlyResourceBook<Exercise> updatedExerciseBook =
            new ExerciseBookBuilder().withExercise(updatedExercise).withExercise(WALK).build();
        String expectedMessage = String.format(CustomRemoveCommand.MESSAGE_SUCCESS_ALL_REMOVED, VALID_FULL_NAME_REMARK);
        Model expectedModel = new ModelManager(updatedExerciseBook,
            new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
            new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
            new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
            new UserPrefs());

        assertCommandSuccess(customRemoveCommand, model, expectedMessage, expectedModel);
        assertEquals(new HashSet<>(), propertyBook.getCustomProperties());
    }

    @Test
    public void execute_customPropertyRemovedForOneExercise_success() {
        CustomRemoveCommand customRemoveCommand = new CustomRemoveCommand(VALID_FULL_NAME_REMARK,
            Optional.of(INDEX_ONE_BASED_FIRST));

        // Expected result
        Exercise updatedExercise = new ExerciseBuilder().build();
        ReadOnlyResourceBook<Exercise> updatedExerciseBook =
            new ExerciseBookBuilder().withExercise(updatedExercise).withExercise(secondExercise).build();
        String expectedMessage = String.format(CustomRemoveCommand.MESSAGE_SUCCESS_SINGLE_REMOVED,
            VALID_FULL_NAME_REMARK, INDEX_ONE_BASED_FIRST.getOneBased());
        Model expectedModel = new ModelManager(updatedExerciseBook,
            new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
            new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
            new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
            new UserPrefs());
        Set<CustomProperty> expectedSet = Set.of(REMARK); // Custom property should not be removed

        assertCommandSuccess(customRemoveCommand, model, expectedMessage, expectedModel);
        assertEquals(expectedSet, propertyBook.getCustomProperties());
    }

    @Test
    public void execute_fullNameNotUsed_throwsCommandException() {
        CustomRemoveCommand customRemoveCommand = new CustomRemoveCommand("Date", Optional.empty());
        assertThrows(CommandException.class, () -> customRemoveCommand.execute(model));
    }

    @Test
    public void execute_invalidExerciseIndexUnfilteredList_failure() {
        CustomRemoveCommand customRemoveCommand = new CustomRemoveCommand(VALID_FULL_NAME_REMARK,
            Optional.of(INDEX_VERY_LARGE_NUMBER));
        assertThrows(CommandException.class, () -> customRemoveCommand.execute(model));
    }

    @Test
    public void equals() {
        CustomRemoveCommand removeRating = new CustomRemoveCommand(VALID_FULL_NAME_RATING, Optional.empty());
        CustomRemoveCommand removeRatingNonEmptyIndex = new CustomRemoveCommand(VALID_FULL_NAME_REMARK,
            Optional.of(INDEX_ONE_BASED_FIRST));
        CustomRemoveCommand removeRatingDifferentIndex = new CustomRemoveCommand(VALID_FULL_NAME_REMARK,
            Optional.of(INDEX_ONE_BASED_SECOND));
        CustomRemoveCommand removeEndDate = new CustomRemoveCommand(VALID_FULL_NAME_END_DATE, Optional.empty());
        CustomRemoveCommand anotherRemoveRating = new CustomRemoveCommand(VALID_FULL_NAME_RATING, Optional.empty());

        // same object -> true and null -> false
        assertCommonEqualsTest(removeRating);

        // Same values -> true
        assertTrue(removeRating.equals(anotherRemoveRating));

        // Empty index and nonempty index -> false
        assertFalse(removeRating.equals(removeRatingNonEmptyIndex));

        // Different indices -> false
        assertFalse(removeRatingNonEmptyIndex.equals(removeRatingDifferentIndex));

        // Different values -> false
        assertFalse(removeRating.equals(removeEndDate));

        // Different object -> false
        assertFalse(removeRating.equals(3.5));

    }
}
