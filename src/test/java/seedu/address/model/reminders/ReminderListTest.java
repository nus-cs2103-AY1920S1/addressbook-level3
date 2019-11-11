package seedu.address.model.reminders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReminders.CS2100;
import static seedu.address.testutil.TypicalReminders.CS2103T;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.UniqueReminderList;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.testutil.ReminderBuilder;

public class ReminderListTest {
    private static final String VALID_PICTURE_BOB = "bob.jpg";
    private final UniqueReminderList reminderList = new UniqueReminderList();

    @Test
    public void contains_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.contains(null));
    }

    @Test
    public void contains_reminderNotInList_returnsFalse() {
        assertFalse(reminderList.contains(CS2103T));
    }

    @Test
    public void contains_reminderInList_returnsTrue() {
        reminderList.add(CS2103T);
        assertTrue(reminderList.contains(CS2103T));
    }

    @Test
    public void contains_reminderWithSameIdentityFieldsInList_returnsTrue() {
        reminderList.add(CS2103T);
        Reminder editedCS2103T = new ReminderBuilder(CS2103T).build();
        assertTrue(reminderList.contains(editedCS2103T));
    }

    @Test
    public void add_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.add(null));
    }

    @Test
    public void add_duplicateReminder_throwsDuplicateReminderException() {
        reminderList.add(CS2103T);
        assertThrows(DuplicateReminderException.class, () -> reminderList.add(CS2103T));
    }

    @Test
    public void setTask_nullTargetReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.setReminder(null, CS2103T));
    }

    @Test
    public void setTask_nullEditedReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.setReminder(CS2103T, null));
    }

    @Test
    public void setTask_targetReminderNotInList_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> reminderList.setReminder(CS2103T, CS2103T));
    }

    @Test
    public void setReminder_editedReminderIsSameReminder_success() {
        reminderList.add(CS2103T);
        reminderList.setReminder(CS2103T, CS2103T);
        UniqueReminderList expectedReminderList = new UniqueReminderList();
        expectedReminderList.add(CS2103T);
        assertEquals(expectedReminderList, reminderList);
    }

    @Test
    public void setReminder_editedReminderHasSameIdentity_success() {
        reminderList.add(CS2103T);
        Reminder editedCS2103T = new ReminderBuilder(CS2103T).build();
        reminderList.setReminder(CS2103T, editedCS2103T);
        UniqueReminderList expectedReminderList = new UniqueReminderList();
        expectedReminderList.add(editedCS2103T);
        assertEquals(expectedReminderList, reminderList);
    }

    @Test
    public void setReminder_editedReminderHasDifferentIdentity_success() {
        reminderList.add(CS2103T);
        reminderList.setReminder(CS2103T, CS2100);
        UniqueReminderList expectedReminderList = new UniqueReminderList();
        expectedReminderList.add(CS2100);
        assertEquals(expectedReminderList, reminderList);
    }

    @Test
    public void setReminder_editedReminderHasNonUniqueIdentity_throwsDuplicateReminderException() {
        reminderList.add(CS2103T);
        reminderList.add(CS2100);
        assertThrows(DuplicateReminderException.class, () -> reminderList.setReminder(CS2103T, CS2100));
    }

    @Test
    public void remove_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.remove(null));
    }

    @Test
    public void remove_reminderDoesNotExist_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> reminderList.remove(CS2103T));
    }

    @Test
    public void remove_existingReminder_removesReminder() {
        reminderList.add(CS2100);
        reminderList.remove(CS2100);
        UniqueReminderList expectedReminderList = new UniqueReminderList();
        assertEquals(expectedReminderList, reminderList);
    }

    @Test
    public void setReminder_nullReminderList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.setReminder((UniqueReminderList) null));
    }

    @Test
    public void setReminder_reminderList_replacesOwnListWithProvidedReminderList() {
        reminderList.add(CS2103T);
        UniqueReminderList expectedReminderList = new UniqueReminderList();
        expectedReminderList.add(CS2100);
        reminderList.setReminder(expectedReminderList);
        assertEquals(expectedReminderList, reminderList);
    }

    @Test
    public void setReminder_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.setReminder((List<Reminder>) null));
    }

    @Test
    public void setReminder_list_replacesOwnListWithProvidedList() {
        reminderList.add(CS2103T);
        List<Reminder> newReminderList = Collections.singletonList(CS2100);
        reminderList.setReminder(newReminderList);
        UniqueReminderList expectedReminderList = new UniqueReminderList();
        expectedReminderList.add(CS2100);
        assertEquals(expectedReminderList, reminderList);
    }

    @Test
    public void setReminder_listWithDuplicateReminders_throwsDuplicateRemindersException() {
        List<Reminder> listWithDuplicateReminders = Arrays.asList(CS2100, CS2100);
        assertThrows(DuplicateReminderException.class, () -> reminderList.setReminder(listWithDuplicateReminders));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> reminderList.asUnmodifiableObservableList().remove(0));
    }
}
