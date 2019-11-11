package seedu.exercise.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.TestUtil;
import seedu.exercise.testutil.typicalutil.TypicalExercises;
import seedu.exercise.ui.testutil.GuiUnitTest;

public class ExerciseListCardTest extends GuiUnitTest {

    private Exercise testExercise = TypicalExercises.BASKETBALL;
    private Exercise otherExercise = TypicalExercises.AEROBICS;
    private int zeroIndex = 0;
    private int oneIndex = 1;

    @Test
    public void equals_variousScenarios_succeed() {
        ExerciseListCard testCard = new ExerciseListCard(testExercise, zeroIndex);
        ExerciseListCard diffExerciseSameIndex = new ExerciseListCard(otherExercise, zeroIndex);
        ExerciseListCard diffExerciseDiffIndex = new ExerciseListCard(otherExercise, oneIndex);
        ExerciseListCard sameExerciseDiffIndex = new ExerciseListCard(testExercise, oneIndex);
        ExerciseListCard sameExerciseSameIndex = new ExerciseListCard(testExercise, zeroIndex);

        TestUtil.assertCommonEqualsTest(testCard);

        // diff object, same index -> return false
        assertFalse(testCard.equals(diffExerciseSameIndex));

        // same object, same index -> return true
        assertTrue(testCard.equals(sameExerciseSameIndex));

        // same object, diff index -> return false
        assertFalse(testCard.equals(sameExerciseDiffIndex));

        // diff object, diff index -> return false
        assertFalse(testCard.equals(diffExerciseDiffIndex));
    }
}
