package seedu.exercise.logic.parser.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.BASKETBALL;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.property.Muscle;

class ExercisePredicateTest {
    private ExercisePredicate exercisePredicate;
    private Set<Muscle> muscles;
    private Map<String, String> customProperties;

    @BeforeEach
    public void set_up() {
        muscles = new HashSet<>();
        muscles.add(new Muscle(VALID_MUSCLE_AEROBICS));
        muscles.add(new Muscle(VALID_MUSCLE_BASKETBALL));
        customProperties = new TreeMap<>();
        customProperties.put("fullNameA", "A");
    }

    @Test
    public void test_success() {
        exercisePredicate = PredicateUtil.getExercisePredicate(muscles, customProperties, false);
        assertTrue(exercisePredicate.test(BASKETBALL));
    }

    @Test
    public void test_failure() {
        exercisePredicate = PredicateUtil.getExercisePredicate(muscles, customProperties, true);
        assertFalse(exercisePredicate.test(BASKETBALL));
    }

    @Test
    public void equals() {
        BasePropertyPredicate basePredicateMuscle = PredicateUtil.getBasePredicateMuscle(muscles, true);
        exercisePredicate = new ExercisePredicate(true, basePredicateMuscle);

        // same object -> returns true
        assertTrue(exercisePredicate.equals(exercisePredicate));

        // same class -> returns true
        ExercisePredicate exercisePredicateCopy = new ExercisePredicate(true, basePredicateMuscle);
        assertTrue(exercisePredicate.equals(exercisePredicateCopy));

        // different types -> returns false
        assertFalse(exercisePredicate.equals(1));

        // null -> returns false
        assertFalse(exercisePredicate.equals(null));
    }
}
