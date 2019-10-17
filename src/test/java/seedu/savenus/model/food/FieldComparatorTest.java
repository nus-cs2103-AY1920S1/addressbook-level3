package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.NASI_LEMAK;

import org.junit.jupiter.api.Test;

public class FieldComparatorTest {
    private FieldComparator fieldComparator;
    @Test
    public void compareFields() {
        fieldComparator = new FieldComparator();
        assertNotEquals(fieldComparator.compare(CARBONARA.getPrice(), NASI_LEMAK.getPrice()), 0);
        assertEquals(fieldComparator.compare(CARBONARA.getPrice(), CARBONARA.getPrice()), 0);
        assertThrows(ClassCastException.class, () ->
                                               fieldComparator.compare(CARBONARA.getPrice(), CARBONARA.getName()));
    }
}
