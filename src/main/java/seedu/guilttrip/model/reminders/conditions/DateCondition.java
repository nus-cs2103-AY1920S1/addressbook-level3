package seedu.guilttrip.model.reminders.conditions;

import java.util.function.Predicate;

import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Entry;

/**
 * Condition is met when entry date falls within specified time period.
 */
public class DateCondition extends Condition {
    private Date date;
    private boolean isStart;
    private Predicate<Entry> datePredicate = new Predicate<>() {
        @Override
        public boolean test(Entry entry) {
            boolean isAtBoundary = entry.getDate().getDate().isEqual(date.getDate());
            boolean isWithinBoundary;
            if (isStart) {
                isWithinBoundary = entry.getDate().getDate().isAfter(date.getDate());
            } else {
                isWithinBoundary = entry.getDate().getDate().isBefore(date.getDate());
            }
            return isAtBoundary || isWithinBoundary;
        }
    };
    public DateCondition(Date date, boolean isStart) {
        super("Date Condition");
        this.date = date;
        this.isStart = isStart;
        setPred(datePredicate);
    }

    public boolean isStart() {

        return isStart;
    }

    public Date getDate() {
        return date;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof DateCondition)) {
            return false;
        } else {
            return this.date.equals(((DateCondition) other).date);
        }
    }
}
