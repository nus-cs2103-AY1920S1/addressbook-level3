package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSchedules.SCHEDULEONE;
import static seedu.address.testutil.TypicalSchedules.SCHEDULETWO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.schedule.exceptions.DuplicateScheduleException;
import seedu.address.model.schedule.exceptions.ScheduleNotFoundException;

class UniqueScheduleListTest {

    private UniqueScheduleList uniqueScheduleList = new UniqueScheduleList();

    @Test
    void contains_nullSchedule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueScheduleList.contains(null));
    }

    @Test
    void contains_scheduleNotInList_returnsFalse() {
        assertFalse(uniqueScheduleList.contains(SCHEDULEONE));
    }

    @Test
    void contains_scheduleInList_returnsTrue() {
        uniqueScheduleList.add(SCHEDULEONE);
        assertTrue(uniqueScheduleList.contains(SCHEDULEONE));
    }

    @Test
    void add_nullSchedule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueScheduleList.add(null));
    }

    @Test
    void add_duplicateSchedule_throwsDuplicateScheduleException() {
        uniqueScheduleList.add(SCHEDULEONE);
        assertThrows(DuplicateScheduleException.class, () -> uniqueScheduleList.add(SCHEDULEONE));
    }

    @Test
    void add_uniqueSchedule_success() {
        uniqueScheduleList.add(SCHEDULEONE);
        uniqueScheduleList.add(SCHEDULETWO);
        assertTrue(uniqueScheduleList.contains(SCHEDULETWO));
    }

    @Test
    void setSchedule_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueScheduleList.setSchedule(null, SCHEDULEONE));
    }

    @Test
    void setSchedule_nullEditedSchedule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueScheduleList.setSchedule(SCHEDULEONE, null));
    }

    @Test
    void setSchedule_targetNotInList_throwsScheduleNotFoundException() {
        assertThrows(ScheduleNotFoundException.class, () -> uniqueScheduleList.setSchedule(SCHEDULEONE, SCHEDULETWO));
    }

    @Test
    void setSchedule_targetNotSameAsEditedAndEditedIsDuplicate_throwsDuplicateScheduleException() {
        uniqueScheduleList.add(SCHEDULEONE);
        uniqueScheduleList.add(SCHEDULETWO);
        assertThrows(DuplicateScheduleException.class, () ->
                uniqueScheduleList.setSchedule(SCHEDULETWO, SCHEDULEONE));
    }

    @Test
    void setSchedule_targetNotSameAsEditedAndEditedIsNotDuplicate_success() {
        uniqueScheduleList.add(SCHEDULEONE);
        uniqueScheduleList.setSchedule(SCHEDULEONE, SCHEDULETWO);
        assertTrue(uniqueScheduleList.contains(SCHEDULETWO));
    }

    @Test
    void setSchedule_targetSameAsEdited_success() {
        uniqueScheduleList.add(SCHEDULEONE);
        uniqueScheduleList.setSchedule(SCHEDULEONE, SCHEDULEONE);
        assertTrue(uniqueScheduleList.contains(SCHEDULEONE));
    }

    @Test
    void remove_nullSchedule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueScheduleList.remove(null));
    }

    @Test
    void remove_scheduleNotInList_throwsScheduleNotFoundException() {
        assertThrows(ScheduleNotFoundException.class, () -> uniqueScheduleList.remove(SCHEDULEONE));
    }

    @Test
    void remove_scheduleInList_success() {
        uniqueScheduleList.add(SCHEDULEONE);
        uniqueScheduleList.remove(SCHEDULEONE);
        assertFalse(uniqueScheduleList.contains(SCHEDULEONE));
    }

    @Test
    void setSchedules_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueScheduleList.setSchedules((List<Schedule>) null));
    }

    @Test
    void setSchedules_nullUniqueScheduleList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueScheduleList.setSchedules((UniqueScheduleList) null));
    }

    @Test
    void setSchedules_uniqueScheduleList_success() {
        UniqueScheduleList newList = new UniqueScheduleList();
        uniqueScheduleList.add(SCHEDULEONE);
        uniqueScheduleList.setSchedules(newList);
        assertEquals(uniqueScheduleList, newList);
    }

    @Test
    void setSchedules_listWithDuplicates_throwsDuplicateScheduleException() {
        List<Schedule> newList = Arrays.asList(SCHEDULEONE, SCHEDULEONE);
        assertThrows(DuplicateScheduleException.class, () -> uniqueScheduleList.setSchedules(newList));
    }

    @Test
    void setSchedules_listWithNoDuplicates_success() {
        List<Schedule> newList = Collections.singletonList(SCHEDULEONE);
        UniqueScheduleList expectedUniqueScheduleList = new UniqueScheduleList();
        expectedUniqueScheduleList.add(SCHEDULEONE);
        uniqueScheduleList.setSchedules(newList);
        assertEquals(uniqueScheduleList, expectedUniqueScheduleList);
    }

    @Test
    void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueScheduleList.asUnmodifiableObservableList().remove(0));
    }

}
