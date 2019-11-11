package seedu.address.model.reminder;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Task} matches the date given.
 */
public class ReminderContainsDatePredicate implements Predicate<Reminder> {
    private final String date;

    public ReminderContainsDatePredicate(String date) {
        this.date = date;
    }

    @Override
    public boolean test(Reminder reminder) {
        return //task.getTime().stream().forEach(taskTime -> date.matches(taskTime.fullTime));
                reminder.getTime().stream().anyMatch(reminderTime ->
                        StringUtil.containsWordIgnoreCase(reminderTime.fullTime,
                                this.date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderContainsDatePredicate // instanceof handles nulls
                && date.equals(((ReminderContainsDatePredicate) other).date)); // state check
    }
}
