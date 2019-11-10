package seedu.module.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SemesterDetailListTest {
    private List<SemesterDetail> testDetails;

    @BeforeEach
    public void beforeEach() {
        testDetails = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            testDetails.add(new SemesterDetail(i));
        }
    }

    @Test
    public void constructor() {
        assertAllEquals(testDetails, new SemesterDetailList().getAsObservableList());
    }

    @Test
    public void constructor_withDifferentSemesterDetailsWithExam_equals() {
        testDetails.set(0, new SemesterDetail(1, LocalDateTime.of(2019, 1, 1, 0, 0), 120));
        assertAllEquals(testDetails, new SemesterDetailList(testDetails).getAsObservableList());
    }

    @Test
    public void constructor_withDifferentSemesterDetailsWithoutExam_equals() {
        // null indicates no exam, but the semester is still offered
        testDetails.set(0, new SemesterDetail(1, null, 0));
        assertAllEquals(testDetails, new SemesterDetailList(testDetails).getAsObservableList());
    }

    /**
     * Asserts all the corresponding semester details in
     * {@code list} and {@code expectedList} are equal.
     * @param list
     * @param expectedList
     */
    private void assertAllEquals(List<SemesterDetail> expectedList, List<SemesterDetail> list) {
        for (int i = 0; i < 4; i++) {
            SemesterDetail expectedSemesterDetail = expectedList.get(i);
            SemesterDetail semesterDetail = list.get(i);

            assertEquals(expectedSemesterDetail.getSemester(), semesterDetail.getSemester());
            assertEquals(expectedSemesterDetail.getExamDate(), semesterDetail.getExamDate());
            assertEquals(expectedSemesterDetail.getExamDuration(), semesterDetail.getExamDuration());
            assertEquals(expectedSemesterDetail.isOffered(), semesterDetail.isOffered());
        }
    }
}
