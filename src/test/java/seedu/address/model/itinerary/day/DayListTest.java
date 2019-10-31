package seedu.address.model.itinerary.day;

import static seedu.address.testutil.TypicalDays.DAY_A;
import static seedu.address.testutil.TypicalDays.DAY_B;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.day.exceptions.DayNotFoundException;
import seedu.address.testutil.DayBuilder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DayListTest {
    public static final DayList dayList = new DayList();

    @Test
    void contains_nullDay_throwsNullPointerException() {
        DayList dayList = new DayList();
        assertThrows(NullPointerException.class, () -> dayList.contains(null));
    }

    @Test
    void contains_dayNotInList_returnsFalse() {
        DayList dayList = new DayList();
        assertFalse(dayList.contains(DAY_A));
    }

    @Test
    void contains_dayInList_returnsTrue() {
        DayList dayList = new DayList();
        assertDoesNotThrow(() -> {
            dayList.add(DAY_A);
            assertTrue(dayList.contains(DAY_A));
        });
    }

    @Test
    void contains_dayWithSameIdentityFieldsInList_returnsTrue() {
        DayList dayList = new DayList();
        assertDoesNotThrow(() -> {
            dayList.add(DAY_A);
            Day editedDayA = DayBuilder.of(DAY_A).setTotalBudget(new Budget("10"))
                    .build();
            assertTrue(dayList.contains(editedDayA));
        });
    }

    @Test
    void add_nullDay_throwsNullPointerException() {
        DayList dayList = new DayList();
        assertThrows(NullPointerException.class, () -> dayList.add(null));
    }

    @Test
    void setDay_nullTargetDay_throwsNullPointerException() {
        DayList dayList = new DayList();
        assertThrows(NullPointerException.class, () -> dayList.set(null, DAY_A));
    }

    @Test
    void setDay_nullEditedDay_throwsNullPointerException() {
        DayList dayList = new DayList();
        assertThrows(NullPointerException.class, () -> dayList.set(DAY_A, null));
    }

    @Test
    void setDay_targetDayNotInList_throwsDayNotFoundException() {
        DayList dayList = new DayList();
        assertThrows(DayNotFoundException.class, () -> dayList.set(DAY_A, DAY_A));
    }

    @Test
    void setDay_editedDayIsSameDay_success() {
        DayList dayList = new DayList();
        assertDoesNotThrow(() -> {
            dayList.add(DAY_A);
            dayList.set(DAY_A, DAY_A);
            DayList expectedDayList = new DayList();
            expectedDayList.add(DAY_A);
            assertEquals(expectedDayList, dayList);
        });
    }

    @Test
    void setDay_editedDayHasSameIdentity_success() {
        DayList dayList = new DayList();
        assertDoesNotThrow(() -> {
            dayList.add(DAY_A);
            Day editedDay = DayBuilder.of(DAY_A)
                    .setTotalBudget(new Budget("100"))
                    .build();
            dayList.set(DAY_A, editedDay);
            DayList expectedUniqueDayList = new DayList();
            expectedUniqueDayList.add(editedDay);
            assertEquals(expectedUniqueDayList, dayList);
        });
    }

    @Test
    void setDay_editedDayHasDifferentIdentity_success() {
        DayList dayList = new DayList();
        assertDoesNotThrow(() -> {
            dayList.add(DAY_A);
            dayList.set(DAY_A, DAY_B);
            DayList expectedUniqueDayList = new DayList();
            expectedUniqueDayList.add(DAY_B);
            assertEquals(expectedUniqueDayList, dayList);
        });
    }

    @Test
    public void remove_nullDay_throwsNullPointerException() {
        DayList dayList = new DayList();
        assertThrows(NullPointerException.class, () -> dayList.remove((Day) null));
    }

    @Test
    public void remove_dayDoesNotExist_throwsDayNotFoundException() {
        DayList dayList = new DayList();
        assertThrows(DayNotFoundException.class, () -> dayList.remove(DAY_A));
    }

    @Test
    public void remove_existingDay_removesDay() {
        DayList dayList = new DayList();
        assertDoesNotThrow(() -> {
            dayList.add(DAY_A);
            dayList.remove(DAY_A);
            DayList expectedUniqueDayList = new DayList();
            assertEquals(expectedUniqueDayList, dayList);
        });
    }

    //-------------------------------------------------------------------

    @Test
    public void setDays_nullList_throwsNullPointerException() {
        DayList dayList = new DayList();
        assertThrows(NullPointerException.class, () -> dayList.set((List<Day>) null));
    }

    @Test
    public void setDays_list_replacesOwnListWithProvidedList() {
        DayList dayList = new DayList();
        assertDoesNotThrow(() -> {
            dayList.add(DAY_A);
            List<Day> days = Collections.singletonList(DAY_B);
            dayList.set(days);
            DayList expectedUniqueDayList = new DayList();
            expectedUniqueDayList.add(DAY_B);
            assertEquals(expectedUniqueDayList, dayList);
        });
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        DayList dayList = new DayList();
        assertThrows(UnsupportedOperationException.class, () ->
                dayList.asUnmodifiableObservableList().remove(0));
    }
}
