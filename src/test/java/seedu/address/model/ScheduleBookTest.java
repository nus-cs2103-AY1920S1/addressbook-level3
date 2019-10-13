package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSchedules.CBD_SCHEDULE;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.exceptions.DuplicateScheduleException;
import seedu.address.testutil.ScheduleBuilder;

public class ScheduleBookTest {

    private final ScheduleBook scheduleBook = new ScheduleBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), scheduleBook.getList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> scheduleBook.resetData(null));
    }


    @Test
    public void resetData_withValidReadOnlyDataBookSchedule_replacesData() {
        ScheduleBook newData = getTypicalScheduleBook();
        scheduleBook.resetData(newData);
        assertEquals(newData, scheduleBook);
    }

    @Test
    public void resetData_withDuplicateSchedules_throwsDuplicateScheduleException() {
        // Two schedules with the same identity fields
        Schedule editedSchedule = new ScheduleBuilder(CBD_SCHEDULE).build();
        List<Schedule> newSchedules = Arrays.asList(CBD_SCHEDULE, editedSchedule);
        ScheduleBookStub newData = new ScheduleBookStub(newSchedules);

        assertThrows(DuplicateScheduleException.class, () -> scheduleBook.resetData(newData));
    }

    @Test
    public void hasSchedule_nullSchedule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> scheduleBook.hasSchedule(null));
    }

    @Test
    public void hasSchedule_scheduleNotInAddressBook_returnsFalse() {
        assertFalse(scheduleBook.hasSchedule(CBD_SCHEDULE));
    }

    @Test
    public void hasSchedule_scheduleInScheduleBook_returnsTrue() {
        scheduleBook.addSchedule(CBD_SCHEDULE);
        assertTrue(scheduleBook.hasSchedule(CBD_SCHEDULE));
    }

    @Test
    public void hasSchedule_scheduleWithSameIdentityFieldsInScheduleBook_returnsTrue() {
        scheduleBook.addSchedule(CBD_SCHEDULE);
        Schedule clonedSchedule = new ScheduleBuilder(CBD_SCHEDULE).build();
        assertTrue(scheduleBook.hasSchedule(clonedSchedule));
    }

    @Test
    public void getScheduleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> scheduleBook.getList().remove(0));
    }

    /**
     * A stub ReadOnlyDataBook(Schedule) whose schedules list can violate interface constraints.
     */
    private static class ScheduleBookStub implements ReadOnlyDataBook<Schedule> {
        private final ObservableList<Schedule> schedules = FXCollections.observableArrayList();

        ScheduleBookStub(Collection<Schedule> schedules) {
            this.schedules.setAll(schedules);
        }

        @Override
        public ObservableList<Schedule> getList() {
            return schedules;
        }
    }

}
