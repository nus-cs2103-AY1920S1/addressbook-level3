package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class TitleTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    void testEquals() {
        Title title = new Title("Programming Methodology II");

        // same values -> return true
        assertTrue(title.equals(new Title("Programming Methodology II")));

        // same object -> return true
        assertTrue(title.equals(title));

        // null -> returns false
        assertFalse(title.equals(null));

        // different title -> returns false
        assertFalse(title.equals(new Title("Data Structures and Algorithms")));
    }

    @Test
    void testHashCode() {
        Title t1 = new Title("Programming Methodology II");
        Title t2 = new Title("Programming Methodology II");
        Title t3 = new Title("Data Structures and Algorithms");
        assertEquals(t1.hashCode(), t1.hashCode());
        assertEquals(t1.hashCode(), t2.hashCode());
        assertNotEquals(t1.hashCode(), t3.hashCode());
    }
}
