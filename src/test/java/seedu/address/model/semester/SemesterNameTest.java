package seedu.address.model.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.semester.exceptions.SemesterNotFoundException;

public class SemesterNameTest {
    @Test
    public void isValid() {
        assertTrue(SemesterName.isValidSemesterName("Y1S1"));
        assertTrue(SemesterName.isValidSemesterName("Y1S2"));
        assertTrue(SemesterName.isValidSemesterName("Y1sT1"));
        assertTrue(SemesterName.isValidSemesterName("Y2s2"));
        assertTrue(SemesterName.isValidSemesterName("y4St1"));
        assertTrue(SemesterName.isValidSemesterName("Y5S1"));
        assertFalse(SemesterName.isValidSemesterName("Y3S3"));
        assertFalse(SemesterName.isValidSemesterName("y1s3"));
        assertFalse(SemesterName.isValidSemesterName("t123"));
        assertFalse(SemesterName.isValidSemesterName(""));
    }

    @Test
    public void getEnum_invalid_throwsSemesterNotFoundException() {
        assertThrows(SemesterNotFoundException.class, () -> SemesterName.getEnum(1, 3));
        assertThrows(SemesterNotFoundException.class, () -> SemesterName.getEnum(2, 3));
        assertThrows(SemesterNotFoundException.class, () -> SemesterName.getEnum(3, 3));
        assertThrows(SemesterNotFoundException.class, () -> SemesterName.getEnum(4, 3));

        assertThrows(SemesterNotFoundException.class, () -> SemesterName.getSpecialTermEnum(1, 3));
        assertThrows(SemesterNotFoundException.class, () -> SemesterName.getSpecialTermEnum(2, 3));
        assertThrows(SemesterNotFoundException.class, () -> SemesterName.getSpecialTermEnum(3, 3));
        assertThrows(SemesterNotFoundException.class, () -> SemesterName.getSpecialTermEnum(4, 3));

    }

    @Test
    public void getEnum_valid() {
        assertEquals(SemesterName.Y1S1, SemesterName.getEnum(1, 1));
        assertEquals(SemesterName.Y1S2, SemesterName.getEnum(1, 2));
        assertEquals(SemesterName.Y2S1, SemesterName.getEnum(2, 1));
        assertEquals(SemesterName.Y2S2, SemesterName.getEnum(2, 2));
        assertEquals(SemesterName.Y3S1, SemesterName.getEnum(3, 1));
        assertEquals(SemesterName.Y3S2, SemesterName.getEnum(3, 2));
        assertEquals(SemesterName.Y4S1, SemesterName.getEnum(4, 1));
        assertEquals(SemesterName.Y4S2, SemesterName.getEnum(4, 2));

        assertEquals(SemesterName.Y1ST1, SemesterName.getSpecialTermEnum(1, 1));
        assertEquals(SemesterName.Y1ST2, SemesterName.getSpecialTermEnum(1, 2));
        assertEquals(SemesterName.Y2ST1, SemesterName.getSpecialTermEnum(2, 1));
        assertEquals(SemesterName.Y2ST2, SemesterName.getSpecialTermEnum(2, 2));
        assertEquals(SemesterName.Y3ST1, SemesterName.getSpecialTermEnum(3, 1));
        assertEquals(SemesterName.Y3ST2, SemesterName.getSpecialTermEnum(3, 2));
        assertEquals(SemesterName.Y4ST1, SemesterName.getSpecialTermEnum(4, 1));
        assertEquals(SemesterName.Y4ST2, SemesterName.getSpecialTermEnum(4, 2));
    }
}
