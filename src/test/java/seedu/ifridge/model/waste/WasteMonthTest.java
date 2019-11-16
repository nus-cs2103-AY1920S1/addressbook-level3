package seedu.ifridge.model.waste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.jfree.data.time.Month;
import org.junit.jupiter.api.Test;

import seedu.ifridge.model.waste.exceptions.WasteMonthException;

public class WasteMonthTest {

    public static final WasteMonth MONTH = new WasteMonth(10, 2019);
    public static final WasteMonth EARLIER_MONTH = new WasteMonth(8, 2019);
    public static final WasteMonth LATER_MONTH = new WasteMonth(12, 2019);
    public static final WasteMonth LAST_MONTH = new WasteMonth(9, 2019);
    public static final WasteMonth NEXT_MONTH = new WasteMonth(11, 2019);
    public static final WasteMonth MONTH_LAST_YEAR = new WasteMonth(10, 2018);
    public static final WasteMonth SAME_MONTH = new WasteMonth(10, 2019);

    @Test
    public void constructorDefault_invalidWasteMonth_throwsWasteMonthException() {
        int invalidMonth1 = 0;
        int invalidMonth2 = 13;
        int invalidMonth3 = -1;
        int invalidYear1 = 999;
        int invalidYear2 = 5001;
        int validMonth = 5;
        int validYear = 2019;
        assertThrows(WasteMonthException.class, () -> new WasteMonth(invalidMonth1, validYear));
        assertThrows(WasteMonthException.class, () -> new WasteMonth(invalidMonth2, validYear));
        assertThrows(WasteMonthException.class, () -> new WasteMonth(invalidMonth3, validYear));
        assertThrows(WasteMonthException.class, () -> new WasteMonth(validMonth, invalidYear1));
        assertThrows(WasteMonthException.class, () -> new WasteMonth(validMonth, invalidYear2));
    }

    @Test
    public void constructorString_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WasteMonth((String) null));
    }

    @Test
    public void constructorString_invalidWasteMonth_throwsWasteMonthException() {
        String wrongFormat1 = "10/2019";
        String wrongFormat2 = "Oct 2019";
        String wrongFormat3 = "10.2019";
        String wrongFormat4 = "";
        String invalidMonth1 = "13-2019";
        String invalidMonth2 = "00-2019";
        String invalidYear1 = "10-0000";
        String invalidYear2 = "10-10000";
        assertThrows(WasteMonthException.class, () -> new WasteMonth(wrongFormat1));
        assertThrows(WasteMonthException.class, () -> new WasteMonth(wrongFormat2));
        assertThrows(WasteMonthException.class, () -> new WasteMonth(wrongFormat3));
        assertThrows(WasteMonthException.class, () -> new WasteMonth(wrongFormat4));
        assertThrows(WasteMonthException.class, () -> new WasteMonth(invalidMonth1));
        assertThrows(WasteMonthException.class, () -> new WasteMonth(invalidMonth2));
        assertThrows(WasteMonthException.class, () -> new WasteMonth(invalidYear1));
        assertThrows(WasteMonthException.class, () -> new WasteMonth(invalidYear2));
    }

    @Test
    public void constructorString_valid() {
        String validWasteMonth1 = "01-2019";
        String validWasteMonth2 = "12-2030";
        assertEquals(new WasteMonth(1, 2019), new WasteMonth(validWasteMonth1));
        assertEquals(new WasteMonth(12, 2030), new WasteMonth(validWasteMonth2));
    }

    @Test
    public void constructorLocalDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WasteMonth((LocalDate) null));
    }

    @Test
    public void constructorLocalDate_valid() {
        LocalDate validDate = LocalDate.of(2019, 10, 31);
        assertEquals(new WasteMonth(10, 2019), new WasteMonth(validDate));
    }

    @Test
    public void getMonth() {
        WasteMonth validWasteMonth = new WasteMonth(10, 2019);
        assertEquals(10, validWasteMonth.getMonth());
        assertNotEquals(12, validWasteMonth.getMonth());
    }

    @Test
    public void getYear() {
        WasteMonth validWasteMonth = new WasteMonth(10, 2019);
        assertEquals(2019, validWasteMonth.getYear());
        assertNotEquals(2020, validWasteMonth.getYear());
    }

    @Test
    public void isBefore() {
        assertFalse(MONTH.isBefore(EARLIER_MONTH));
        assertTrue(MONTH.isBefore(LATER_MONTH));
        assertFalse(MONTH.isBefore(SAME_MONTH));
    }

    @Test
    public void isAfter() {
        assertTrue(MONTH.isAfter(EARLIER_MONTH));
        assertFalse(MONTH.isAfter(LATER_MONTH));
        assertFalse(MONTH.isAfter(SAME_MONTH));
    }

    @Test
    public void nextWasteMonth() {
        assertEquals(NEXT_MONTH, MONTH.nextWasteMonth());
        assertNotEquals(LAST_MONTH, MONTH.nextWasteMonth());
        assertNotEquals(MONTH_LAST_YEAR, MONTH.nextWasteMonth());
    }

    @Test
    public void addWasteMonth() {
        WasteMonth oct2019 = new WasteMonth(10, 2019);
        WasteMonth dec2019 = new WasteMonth(12, 2019);
        WasteMonth jan2020 = new WasteMonth(1, 2020);
        WasteMonth sep2019 = new WasteMonth(9, 2019);
        assertEquals(dec2019, oct2019.addWasteMonth(2));
        assertEquals(jan2020, oct2019.addWasteMonth(3));
        assertEquals(oct2019, oct2019.addWasteMonth(0));
        assertEquals(sep2019, oct2019.addWasteMonth(-1));
    }

    @Test
    public void previousWasteMonth() {
        assertEquals(LAST_MONTH, MONTH.previousWasteMonth());
        assertNotEquals(NEXT_MONTH, MONTH.previousWasteMonth());
        assertNotEquals(MONTH_LAST_YEAR, MONTH.previousWasteMonth());
    }

    @Test
    public void minusWasteMonth() {
        WasteMonth feb2019 = new WasteMonth(2, 2019);
        WasteMonth jan2019 = new WasteMonth(1, 2019);
        WasteMonth dec2018 = new WasteMonth(12, 2018);
        WasteMonth mar2019 = new WasteMonth(3, 2019);
        assertEquals(jan2019, feb2019.minusWasteMonth(1));
        assertEquals(dec2018, feb2019.minusWasteMonth(2));
        assertEquals(feb2019, feb2019.minusWasteMonth(0));
        assertEquals(mar2019, feb2019.minusWasteMonth(-1));
    }

    @Test
    public void toStorageFormat() {
        WasteMonth sep2019 = new WasteMonth(9, 2019);
        String correctStorageFormat = "09-2019";
        String incorrectStorageFormat1 = "09.2019";
        String incorrectStorageFormat2 = "09/2019";
        String incorrectStorageFormat3 = "Sep 2019";
        String incorrectStorageFormat4 = "9-2019";
        assertEquals(correctStorageFormat, sep2019.toStorageFormat());
        assertNotEquals(incorrectStorageFormat1, sep2019.toStorageFormat());
        assertNotEquals(incorrectStorageFormat2, sep2019.toStorageFormat());
        assertNotEquals(incorrectStorageFormat3, sep2019.toStorageFormat());
        assertNotEquals(incorrectStorageFormat4, sep2019.toStorageFormat());
    }

    @Test
    public void toJFreeMonth() {
        WasteMonth month = new WasteMonth(10, 2019);
        Month jFreeMonth = new Month(10, 2019);
        Month otherJFreeMonth = new Month(2, 2019);
        assertEquals(jFreeMonth, month.toJFreeMonth());
        assertNotEquals(otherJFreeMonth, month.toJFreeMonth());
    }

    @Test
    public void getCurrentWasteMonth() {
        WasteMonth currentWasteMonth = new WasteMonth(LocalDate.now());
        assertEquals(currentWasteMonth, WasteMonth.getCurrentWasteMonth());
    }

    @Test
    public void earlier() {
        assertEquals(EARLIER_MONTH, WasteMonth.earlier(MONTH, EARLIER_MONTH));
        assertEquals(MONTH, WasteMonth.earlier(MONTH, LATER_MONTH));
    }

    @Test
    public void later() {
        assertEquals(LATER_MONTH, WasteMonth.later(MONTH, LATER_MONTH));
        assertEquals(MONTH, WasteMonth.later(MONTH, EARLIER_MONTH));
    }

    @Test
    public void testEquals() {
        assertTrue(MONTH.equals(SAME_MONTH));
        assertFalse(MONTH.equals(LAST_MONTH));
        assertFalse(MONTH.equals("09-2019"));
    }

    @Test
    public void testToString() {
        WasteMonth sep2019 = new WasteMonth(9, 2019);
        assertEquals("Sep 2019", sep2019.toString());
    }
}
