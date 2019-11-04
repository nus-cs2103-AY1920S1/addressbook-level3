package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AcadYearTest {
    private AcadYear acadYear1;
    private AcadYear acadYear2;
    private AcadYear acadYear3;

    @BeforeEach
    void setUp() {
        acadYear1 = new AcadYear("2018/2019");
        acadYear2 = new AcadYear("2018/2019");
        acadYear3 = new AcadYear("2019/2020");
    }

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AcadYear(null));
    }

    @Test
    void constructor_invalidValue_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new AcadYear("!@)!$*@#"));
        assertThrows(IllegalArgumentException.class, () -> new AcadYear("2012/2020"));
        assertThrows(IllegalArgumentException.class, () -> new AcadYear("2012-2020"));
        assertThrows(IllegalArgumentException.class, () -> new AcadYear("2/0122020"));
    }

    @Test
    void testToStringDashed() {
        assertEquals(acadYear1.toStringDashed(), "2018-2019");
    }

    @Test
    void testEquals() {
        // same values -> return true
        assertTrue(acadYear1.equals(new AcadYear("2018/2019")));

        // same object -> return true
        assertTrue(acadYear1.equals(acadYear1));

        // null -> returns false
        assertFalse(acadYear1.equals(null));

        // different acadYear -> returns false
        assertFalse(acadYear1.equals(acadYear3));
    }

    @Test
    void testHashCode() {
        assertEquals(acadYear1.hashCode(), acadYear1.hashCode());
        assertEquals(acadYear1.hashCode(), acadYear2.hashCode());
        assertNotEquals(acadYear1.hashCode(), acadYear3.hashCode());
    }
}
