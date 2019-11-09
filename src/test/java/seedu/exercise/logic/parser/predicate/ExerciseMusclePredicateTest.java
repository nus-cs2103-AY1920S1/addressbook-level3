package seedu.exercise.logic.parser.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.AEROBICS;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.CLAP;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.property.Muscle;

class ExerciseMusclePredicateTest {

    private ExerciseMusclePredicate exerciseMusclePredicate;

    private Set<Muscle> muscles;

    @BeforeEach
    public void set_up() {
        muscles = new HashSet<Muscle>();
        muscles.add(new Muscle(VALID_MUSCLE_AEROBICS));
        muscles.add(new Muscle(VALID_MUSCLE_BASKETBALL));
    }

    @Test
    public void testStrict_success() {
        exerciseMusclePredicate = new ExerciseMusclePredicate(muscles, true);
        assertEquals(exerciseMusclePredicate.test(BASKETBALL), true);
    }

    @Test
    public void testStrict_failure() {
        exerciseMusclePredicate = new ExerciseMusclePredicate(muscles, true);
        assertFalse(exerciseMusclePredicate.test(AEROBICS));
    }


    @Test
    public void testLoose_success() {
        exerciseMusclePredicate = new ExerciseMusclePredicate(muscles, false);
        assertEquals(exerciseMusclePredicate.test(BASKETBALL), true);
        assertEquals(exerciseMusclePredicate.test(AEROBICS), true);
    }

    @Test
    public void testLoose_failure() {
        exerciseMusclePredicate = new ExerciseMusclePredicate(muscles, false);
        assertFalse(exerciseMusclePredicate.test(CLAP));
    }

    @Test
    public void equals() {
        exerciseMusclePredicate = new ExerciseMusclePredicate(muscles, true);

        // same object -> returns true
        assertTrue(exerciseMusclePredicate.equals(exerciseMusclePredicate));

        // same class -> returns true
        ExerciseMusclePredicate exerciseMusclePredicateCopy = new ExerciseMusclePredicate(muscles, true);
        assertTrue(exerciseMusclePredicate.equals(exerciseMusclePredicateCopy));

        // different types -> returns false
        assertFalse(exerciseMusclePredicate.equals(1));

        // null -> returns false
        assertFalse(exerciseMusclePredicate.equals(null));

        //less muscles -> returns false
        Set<Muscle> musclesCopy = new HashSet<>();
        musclesCopy.add(new Muscle(VALID_MUSCLE_AEROBICS));
        ExerciseMusclePredicate differentExerciseMusclePredicate = new ExerciseMusclePredicate(musclesCopy, true);
        assertFalse(exerciseMusclePredicate.equals(differentExerciseMusclePredicate));

        //different muscles -> returns false
        musclesCopy = new HashSet<>();
        musclesCopy.add(new Muscle("muscleA"));
        musclesCopy.add(new Muscle("muscleB"));
        differentExerciseMusclePredicate = new ExerciseMusclePredicate(musclesCopy, true);
        assertFalse(exerciseMusclePredicate.equals(differentExerciseMusclePredicate));
    }
}
