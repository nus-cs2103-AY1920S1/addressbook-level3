package seedu.exercise.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Resource;
import seedu.exercise.model.resource.Schedule;

public class SampleDataUtilTest {

    @Test
    public void getSampleExerciseBook_matchesSampleData() {
        List<Exercise> sampleExercise = Arrays.asList(SampleDataUtil.getSampleExercises());
        sampleExercise.sort(DEFAULT_EXERCISE_COMPARATOR);
        assertSampleDataCorrect(SampleDataUtil.getSampleExerciseBook(), sampleExercise);
    }

    @Test
    public void getSampleScheduleBook_matchesSampleData() {
        List<Schedule> sampleSchedule = Arrays.asList(SampleDataUtil.getSampleSchedules());
        sampleSchedule.sort(DEFAULT_SCHEDULE_COMPARATOR);

        assertSampleDataCorrect(SampleDataUtil.getSampleScheduleBook(), sampleSchedule);
    }


    @Test
    public void getSampleRegimeBook_matchesSampleData() {
        List<Regime> sampleRegime = Arrays.asList(SampleDataUtil.getSampleRegimes());
        sampleRegime.sort(DEFAULT_REGIME_COMPARATOR);

        assertSampleDataCorrect(SampleDataUtil.getSampleRegimeBook(), sampleRegime);
    }

    private void assertSampleDataCorrect(ReadOnlyResourceBook<? extends Resource> book,
                                         List<? extends Resource> toCheck) {
        assertEquals(book.getSortedResourceList(), toCheck);
    }
}
