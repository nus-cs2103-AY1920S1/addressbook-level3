package seedu.exercise.logic.parser.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.BASKETBALL;

import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExerciseCustomPropertyPredicateTest {
    private ExerciseCustomPropertyPredicate exerciseCustomPropertyPredicate;

    private Map<String, String> customProperties;

    @BeforeEach
    public void set_up() {
        customProperties = new TreeMap<>();
        customProperties.put("fullNameA", "A");
        customProperties.put("fullNameB", "B");
    }

    @Test
    public void testStrict_failure() {
        exerciseCustomPropertyPredicate = new ExerciseCustomPropertyPredicate(customProperties, true);
        assertEquals(exerciseCustomPropertyPredicate.test(BASKETBALL), false);
    }

    @Test
    public void testLoose_failure() {
        exerciseCustomPropertyPredicate = new ExerciseCustomPropertyPredicate(customProperties, false);
        assertEquals(exerciseCustomPropertyPredicate.test(BASKETBALL), false);
    }

    @Test
    public void equals() {
        exerciseCustomPropertyPredicate = new ExerciseCustomPropertyPredicate(customProperties, true);

        // same object -> returns true
        assertTrue(exerciseCustomPropertyPredicate.equals(exerciseCustomPropertyPredicate));

        // same class -> returns true
        ExerciseCustomPropertyPredicate exerciseCustomPropertyPredicateCopy =
                new ExerciseCustomPropertyPredicate(customProperties, true);
        assertTrue(exerciseCustomPropertyPredicate.equals(exerciseCustomPropertyPredicateCopy));

        // different types -> returns false
        assertFalse(exerciseCustomPropertyPredicate.equals(1));

        // null -> returns false
        assertFalse(exerciseCustomPropertyPredicate.equals(null));

        //less custom properties -> returns false
        Map<String, String> customPropertiesCopy = new TreeMap<>();
        customPropertiesCopy.put("fullNameC", "C");
        ExerciseCustomPropertyPredicate differentExerciseCustomPropertiesPredicate =
            new ExerciseCustomPropertyPredicate(customPropertiesCopy, true);
        assertFalse(exerciseCustomPropertyPredicate.equals(differentExerciseCustomPropertiesPredicate));

        //different custom properties -> returns false
        customPropertiesCopy = new TreeMap<>();
        customPropertiesCopy.put("fullNameC", "C");
        customPropertiesCopy.put("fullNameD", "D");
        differentExerciseCustomPropertiesPredicate = new ExerciseCustomPropertyPredicate(customPropertiesCopy, true);
        assertFalse(exerciseCustomPropertyPredicate.equals(differentExerciseCustomPropertiesPredicate));
    }
}
