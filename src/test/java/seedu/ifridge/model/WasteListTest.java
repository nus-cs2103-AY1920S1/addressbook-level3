package seedu.ifridge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_LAST_MONTH;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;
import static seedu.ifridge.testutil.TypicalWasteList.COFFEE;
import static seedu.ifridge.testutil.TypicalWasteList.COOKIES;

import java.time.LocalDate;
import java.util.Collections;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import seedu.ifridge.model.waste.WasteMonth;

public class WasteListTest {

    private TreeMap<WasteMonth, WasteList> wasteArchive = getTypicalWasteArchive();
    private WasteList wasteList = new WasteList(new WasteMonth(LocalDate.now()));

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WasteList(null));
    }

    @Test
    public void constructor_valid() {
        assertEquals(Collections.emptyList(), wasteList.getWasteList());
    }

    @Test
    public void resetData_replacesData() {
        WasteList validWasteList = wasteArchive.get(new WasteMonth(LocalDate.now()));
        wasteList.resetData(validWasteList);
        assertEquals(wasteList, validWasteList);
    }

    @Test
    void addWasteItem_nullWasteItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> wasteList.addWasteItem(null));
    }

    @Test
    void hasWasteItem_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> wasteList.hasWasteItem(null));
    }

    @Test
    void hasWasteItem_notInWasteList_returnsFalse() {
        assertFalse(wasteList.hasWasteItem(COOKIES));
    }

    @Test
    void hasWasteItem_inWasteList_returnsTrue() {
        wasteList.addWasteItem(COOKIES);
        assertTrue(wasteList.hasWasteItem(COOKIES));
    }

    @Test
    void addWasteItem() {
        assertFalse(wasteList.hasWasteItem(COFFEE));
        wasteList.addWasteItem(COFFEE);
        assertTrue(wasteList.hasWasteItem(COFFEE));
    }

    @Test
    void getWasteList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> wasteList.getWasteList().remove(0));
    }

    @Test
    void getWasteListByMonth() {
        WasteList.initialiseWasteArchive();
        WasteList.addWasteArchive(wasteArchive);
        WasteMonth lastWasteMonth = new WasteMonth(LocalDate.now()).previousWasteMonth();
        assertEquals(WASTE_LIST_LAST_MONTH, WasteList.getWasteListByMonth(lastWasteMonth));
    }

}
