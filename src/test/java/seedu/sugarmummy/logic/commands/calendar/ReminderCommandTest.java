package seedu.sugarmummy.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.logic.commands.calendar.ReminderCommand.MESSAGE_DUPLICATE_REMINDER;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.ModelStub;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.Reminder;
import seedu.sugarmummy.testutil.ReminderBuilder;

class ReminderCommandTest {
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderCommand(null));
    }

    @Test
    public void execute_reminderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingReminderAdded modelStub = new ModelStubAcceptingReminderAdded();
        Reminder validReminder = new ReminderBuilder().build();

        CommandResult commandResult = new ReminderCommand(validReminder).execute(modelStub);

        assertEquals(String.format(ReminderCommand.MESSAGE_SUCCESS, validReminder), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validReminder), modelStub.remindersAdded);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() throws Exception {
        Reminder validReminder = new ReminderBuilder().build();
        ReminderCommand reminderCommand = new ReminderCommand(validReminder);
        ModelStub modelStub = new ModelStubWithReminder(validReminder);

        assertThrows(CommandException.class, () -> reminderCommand.execute(modelStub), MESSAGE_DUPLICATE_REMINDER);
    }

    @Test
    public void equals() {
        Reminder insulinInjection = new ReminderBuilder().build();
        Reminder wakeUp = new ReminderBuilder().withDescription("Wake up").build();
        ReminderCommand insulinInjectionCommand = new ReminderCommand(insulinInjection);
        ReminderCommand wakeUpCommand = new ReminderCommand(wakeUp);

        // same object -> returns true
        assertTrue(insulinInjection.equals(insulinInjection));

        // same values -> returns true
        ReminderCommand insulinInjectionCommandCopy = new ReminderCommand(insulinInjection);
        assertTrue(insulinInjectionCommand.equals(insulinInjectionCommandCopy));

        // different types -> returns false
        assertFalse(insulinInjectionCommand.equals(1));

        // null -> returns false
        assertFalse(insulinInjectionCommand.equals(null));

        // different reminders -> returns false
        assertFalse(insulinInjectionCommand.equals(wakeUpCommand));
    }

    /**
     * A Model stub that contains a single reminder.
     */
    private class ModelStubWithReminder extends ModelStub {
        private final Reminder reminder;

        ModelStubWithReminder(Reminder reminder) {
            requireNonNull(reminder);
            this.reminder = reminder;
        }

        @Override
        public boolean hasCalendarEntry(CalendarEntry calendarEntry) {
            requireNonNull(calendarEntry);
            return this.reminder.isSameCalendarEntry(calendarEntry);
        }
    }

    /**
     * A Model stub that always accept the reminder being added.
     */
    private class ModelStubAcceptingReminderAdded extends ModelStub {
        final ArrayList<Reminder> remindersAdded = new ArrayList<>();

        @Override
        public boolean hasCalendarEntry(CalendarEntry calendarEntry) {
            requireNonNull(calendarEntry);
            return remindersAdded.stream().anyMatch(calendarEntry::isSameCalendarEntry);
        }

        @Override
        public void addCalendarEntry(CalendarEntry calendarEntry) {
            requireNonNull(calendarEntry);
            remindersAdded.add((Reminder) calendarEntry);
        }

        @Override
        public void schedule() {
        }

        @Override
        public boolean overlapsCalendarEntry(CalendarEntry calendarEntry) {
            return false;
        }
    }

}
