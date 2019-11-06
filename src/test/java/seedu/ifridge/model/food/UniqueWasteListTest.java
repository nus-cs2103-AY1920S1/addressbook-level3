package seedu.ifridge.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.ifridge.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.ifridge.model.waste.WasteMonth;

class UniqueWasteListTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UniqueWasteList(null));
    }

    @Test
    void getWasteMonth() {
        WasteMonth month = new WasteMonth(2, 2019);
        WasteMonth expectedMonth = new WasteMonth(2, 2019);
        WasteMonth unexpectedMonth = new WasteMonth(10, 2019);
        UniqueWasteList wasteList = new UniqueWasteList(month);
        assertEquals(expectedMonth, wasteList.getWasteMonth());
        assertNotEquals(unexpectedMonth, wasteList.getWasteMonth());
    }
}
