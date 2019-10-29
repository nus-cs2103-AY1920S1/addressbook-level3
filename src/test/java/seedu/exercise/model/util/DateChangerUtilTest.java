package seedu.exercise.model.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.exercise.model.property.Date;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.CommonTestData;
import seedu.exercise.testutil.typicalutil.TypicalExercises;

public class DateChangerUtilTest {

    @Test
    public void changeAllDate_onSampleExercises_allSuccessful() {
        List<Exercise> testExercise = TypicalExercises.getTypicalExercises();

        Collection<Exercise> changed = DateChangerUtil.changeAllDate(testExercise,
                new Date(CommonTestData.VALID_DATE_2));

        // ensure a different copy of object is returned
        assertNotSame(changed, testExercise);

        // ensure all dates are different from the original ones
        assertFalse(changed.stream()
                .anyMatch(testExercise::contains));
    }
}
