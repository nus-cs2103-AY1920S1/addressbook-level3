package seedu.address.model.reminders;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_TIME_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_TIME_2;
import static seedu.address.testutil.TypicalReminders.CS2100;
import static seedu.address.testutil.TypicalReminders.CS2103T;

import org.junit.jupiter.api.Test;

import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.ReminderBuilder;

public class ReminderTest {
    @Test
    public void isSameReminder() {
        // same object -> returns true
        assertTrue(CS2103T.isSameReminder(CS2103T));

        // null -> returns false
        assertFalse(CS2103T.isSameReminder(null));

        // different object -> returns false
        assertFalse(CS2103T.isSameReminder(CS2100));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Reminder cs2103TCopy = new ReminderBuilder(CS2103T).build();
        assertTrue(CS2103T.equals(cs2103TCopy));

        // same object -> returns true
        assertTrue(CS2103T.equals(CS2103T));

        // null -> returns false
        assertFalse(CS2103T.equals(null));

        // different type -> returns false
        assertFalse(CS2103T.equals(5));

        // different person -> returns false
        assertFalse(CS2103T.equals(CS2100));

        // different name -> returns false
        Reminder editedCS2103T = new ReminderBuilder(CS2103T).withDescription(VALID_CLASSID_AMY).build();
        assertFalse(CS2103T.equals(editedCS2103T));

        // different task time -> returns false
        editedCS2103T = new ReminderBuilder(CS2103T).withReminderTimes(VALID_TASK_TIME_1, VALID_TASK_TIME_2).build();
        assertFalse(CS2103T.equals(editedCS2103T));
    }
}
