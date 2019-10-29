package seedu.exercise.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Resource;

public class SampleDataUtilTest {

    @Test
    public void getSampleExerciseBook_matchesSampleData() {
        assertSampleDataCorrect(SampleDataUtil.getSampleExerciseBook(),
                Arrays.asList(SampleDataUtil.getSampleExercises()));
    }

    @Test
    public void getSampleScheduleBook_matchesSampleData() {
        assertSampleDataCorrect(SampleDataUtil.getSampleScheduleBook(),
                Arrays.asList(SampleDataUtil.getSampleSchedules()));
    }

    @Test
    public void getSampleRegimeBook_matchesSampleData() {
        assertSampleDataCorrect(SampleDataUtil.getSampleRegimeBook(),
                Arrays.asList(SampleDataUtil.getSampleRegimes()));
    }

    private void assertSampleDataCorrect(ReadOnlyResourceBook<? extends Resource> book,
                                         List<? extends Resource> toCheck) {
        assertEquals(book.getResourceList(), toCheck);
    }
}
