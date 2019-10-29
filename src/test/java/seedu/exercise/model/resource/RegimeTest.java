package seedu.exercise.model.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.BENCH_PRESS;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.testutil.CommonTestData;
import seedu.exercise.testutil.typicalutil.TypicalExercises;
import seedu.exercise.testutil.typicalutil.TypicalRegime;

public class RegimeTest {

    private Regime testRegime;

    @BeforeEach
    public void setUp() {
        testRegime = TypicalRegime.VALID_REGIME_CARDIO;
    }

    @Test
    public void deepCopy_returnsDeepCopy() {
        Regime copied = testRegime.deepCopy();
        //ensure not the same reference
        assertNotSame(copied, testRegime);

        //Have to test each component since equals method in regime only checks name
        assertEquals(copied.getRegimeName(), testRegime.getRegimeName());
        assertEquals(copied.getRegimeExercises(), testRegime.getRegimeExercises());
    }

    @Test
    public void deleteExercise_deleteExercise_correctlyDeleted() {
        Regime toTest = testRegime.deepCopy();
        toTest.deleteExercise(BENCH_PRESS);

        List<Exercise> expectedList = new ArrayList<>(toTest.getRegimeExercises().asUnmodifiableObservableList());
        expectedList.remove(BENCH_PRESS);
        assertEquals(expectedList, toTest.getRegimeExercises().asUnmodifiableObservableList());
    }

    @Test
    public void addExercise_addExercise_correctlyAdded() {
        Regime regime = TypicalRegime.VALID_REGIME_LEGS;
        regime.addExercise(BENCH_PRESS);

        List<Exercise> expectedList = new ArrayList<>();
        expectedList.add(TypicalExercises.SPRINT);
        expectedList.add(TypicalExercises.WALK);
        expectedList.add(BENCH_PRESS);
        assertEquals(expectedList, regime.getRegimeExercises().asUnmodifiableObservableList());
    }

    @Test
    public void toString_returnsCorrectlyFormattedString() {
        assertEquals(CommonTestData.VALID_REGIME_STRING_FOR_TYPICAL_REGIME_CARDIO, testRegime.toString());
    }
}
