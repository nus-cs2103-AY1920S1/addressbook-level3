package seedu.moolah.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moolah.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class TimekeeperTest {

    //    private class TimekeeperStub {
    //
    //        boolean hasTranspired(Timestamp timestamp) {
    //            return timestamp.isBefore(systemTime);
    //        }
    //
    //        Optional<Reminder> createReminderIfValid(Event event) {
    //            Timestamp timestamp = event.getTimestamp();
    //            long daysLeft = calculateDaysRemaining(timestamp);
    //            return (isUrgent(timestamp)) ? Optional.of(new Reminder(event, daysLeft)) : Optional.empty();
    //        }
    //
    //        long calculateDaysRemaining(Timestamp timestamp) {
    //            long daysLeft = systemTime.getFullTimestamp().until(timestamp.getFullTimestamp(), ChronoUnit.DAYS);
    //            return daysLeft;
    //        }
    //
    //        boolean isUrgent(Timestamp timestamp) {
    //            long daysLeft = calculateDaysRemaining(timestamp);
    //            return daysLeft < UPPER_THRESHOLD && !hasTranspired(timestamp);
    //        }
    //
    //        List<Event> getTranspiredEvents() {
    //            List<Event> eventsToNotify = new ArrayList<>();
    //            List<Event> eventsToBeRemoved = new ArrayList<>();
    //
    //            for (Event event : events) {
    //                Timestamp timestamp = event.getTimestamp();
    //                if (hasTranspired(timestamp)) {
    //                    eventsToBeRemoved.add(event);
    //                    if (logic.hasBudgetWithName(event.getBudgetName())) {
    //                        eventsToNotify.add(event);
    //                    }
    //                }
    //            }
    //
    //            return eventsToNotify;
    //        }
    //
    //        public String displayReminders() {
    //            for (Event event : events) {
    //                Optional<Reminder> potentialReminder = createReminderIfValid(event);
    //                if (potentialReminder.isPresent()) {
    //                    reminders.add(potentialReminder.get());
    //                }
    //            }
    //
    //            StringBuilder remindersMessage =
    //                    new StringBuilder((reminders.isEmpty()
    //                            ? "You have no upcoming events!"
    //                            : "These are your upcoming events:"));
    //            for (Reminder reminder: reminders) {
    //                remindersMessage.append("\n" + reminder.toString());
    //            }
    //
    //            return remindersMessage.toString();
    //        }
    //    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Timekeeper(null));
    }

    @Test
    public void convertToLocalDateTime() {
        Date date = new Date();
        LocalDateTime expectedDateTime = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDateTime actualDateTime = Timekeeper.convertToLocalDateTime(date);
        assertEquals(expectedDateTime, actualDateTime);
    }
}
