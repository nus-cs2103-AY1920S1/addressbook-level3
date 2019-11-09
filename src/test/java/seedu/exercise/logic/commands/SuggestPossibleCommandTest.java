package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.logic.parser.predicate.PredicateUtil.predicateShowExerciseWithCustomProperty;
import static seedu.exercise.logic.parser.predicate.PredicateUtil.predicateShowExercisesWithMuscle;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_REMARK;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_PARAMETER_TYPE_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_PARAMETER_TYPE_REMARK;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_REMARK;
import static seedu.exercise.testutil.CommonTestData.VALID_VALUE_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_VALUE_REMARK;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.PropertyBook;
import seedu.exercise.model.property.custom.CustomProperty;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.builder.CustomPropertyBuilder;
import seedu.exercise.ui.ListResourceType;

public class SuggestPossibleCommandTest {

    private Model model;
    private Model expectedModel;
    private Set<Muscle> targetMuscles;
    private Map<String, String> targetCustomProperties;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExerciseBook(),
            new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
            getTypicalExerciseBook(),
            new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
            new UserPrefs());
        expectedModel = new ModelManager(model.getExerciseBookData(),
            new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
            getTypicalExerciseBook(),
            new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
            new UserPrefs());

        targetMuscles = new HashSet<>();
        targetMuscles.add(new Muscle(VALID_MUSCLE_AEROBICS));
        targetMuscles.add(new Muscle(VALID_MUSCLE_BASKETBALL));

        targetCustomProperties = new TreeMap<>();
        targetCustomProperties.put(VALID_FULL_NAME_RATING, VALID_VALUE_RATING);
        targetCustomProperties.put(VALID_FULL_NAME_REMARK, VALID_VALUE_REMARK);
        CustomProperty rating = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_RATING)
            .withFullName(VALID_FULL_NAME_RATING).withParameterType(VALID_PARAMETER_TYPE_RATING).build();
        CustomProperty remark = new CustomPropertyBuilder().withPrefix(VALID_PREFIX_NAME_REMARK)
            .withFullName(VALID_FULL_NAME_REMARK).withParameterType(VALID_PARAMETER_TYPE_REMARK).build();
        PropertyBook.getInstance().addCustomProperty(rating);
        PropertyBook.getInstance().addCustomProperty(remark);
    }

    @Test
    public void execute_suggestPossibleMuscle_success() {
        boolean isStrict = true;
        Predicate<Exercise> predicateMuscleAnd = predicateShowExercisesWithMuscle(targetMuscles, isStrict);

        expectedModel.updateSuggestedExerciseList(predicateMuscleAnd);
        String expectedMessage = SuggestPossibleCommand.MESSAGE_SUCCESS;

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.SUGGESTION);
        assertCommandSuccess(new SuggestPossibleCommand(predicateMuscleAnd),
            model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_suggestPossibleCustomProperty_success() {
        boolean isStrict = true;
        Predicate<Exercise> predicateCustomPropertyAnd =
            predicateShowExerciseWithCustomProperty(targetCustomProperties, isStrict);

        expectedModel.updateSuggestedExerciseList(predicateCustomPropertyAnd);
        String expectedMessage = SuggestPossibleCommand.MESSAGE_SUCCESS;

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.SUGGESTION);
        assertCommandSuccess(new SuggestPossibleCommand(predicateCustomPropertyAnd),
            model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        boolean isStrict = true;
        Predicate<Exercise> predicateMuscleAnd = predicateShowExercisesWithMuscle(targetMuscles, isStrict);
        SuggestCommand suggestPossibleCommand = new SuggestPossibleCommand(predicateMuscleAnd);

        // same object -> returns true
        assertTrue(suggestPossibleCommand.equals(suggestPossibleCommand));

        // same class -> returns true
        SuggestCommand suggestPossibleCommandCopy = new SuggestPossibleCommand(predicateMuscleAnd);
        assertTrue(suggestPossibleCommand.equals(suggestPossibleCommandCopy));

        // different types -> returns false
        assertFalse(suggestPossibleCommand.equals(1));

        // null -> returns false
        assertFalse(suggestPossibleCommand.equals(null));

        //different predicate with same values -> returns true
        Predicate<Exercise> predicateMuscleAndCopy = predicateShowExercisesWithMuscle(targetMuscles, isStrict);
        assertTrue(suggestPossibleCommand.equals(new SuggestPossibleCommand(predicateMuscleAndCopy)));

        //different predicate with different values -> returns false
        Predicate<Exercise> predicateMuscleOr = predicateShowExercisesWithMuscle(targetMuscles, false);
        assertFalse(suggestPossibleCommand.equals(new SuggestPossibleCommand(predicateMuscleOr)));
    }

}
