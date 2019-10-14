package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.NASI_AYAM;

import org.junit.jupiter.api.Test;

public class DefaultComparatorTest {

    @Test
    public void check_compare() {
        assertThrows(NullPointerException.class, () -> new DefaultComparator().compare(CARBONARA, null));
        assertEquals(new DefaultComparator().compare(CARBONARA, CARBONARA), 0);
        assertNotEquals(new DefaultComparator().compare(NASI_AYAM, CARBONARA), 0);
    }
}
