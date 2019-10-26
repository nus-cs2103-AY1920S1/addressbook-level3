package seedu.address.model.reminders.conditions;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.address.model.person.Date;
import seedu.address.model.person.Description;
import seedu.address.model.person.Entry;

/**
 * Condition is met when entry date falls within specified time period.
 */
public class DateCondition extends Condition {
    private Date start;
    private Date end;
    private Predicate<Entry> datePredicate = new Predicate<>() {
        @Override
        public boolean test(Entry entry) {
            boolean isAtBoundary = entry.getDate().getDate().isEqual(start.getDate())
                    || entry.getDate().getDate().isEqual(end.getDate());
            boolean isWithinBoundary =
                    entry.getDate().getDate().isAfter(start.getDate())
                            && entry.getDate().getDate().isBefore(end.getDate());
            return isAtBoundary || isWithinBoundary;
        }
    };
    public DateCondition(Description desc, Date start, Date end) {
        super(desc);
        this.start = start;
        this.end = end;
        setPred(datePredicate);
    }
    public Date getStart() {
        return start;
    }
    public Date getEnd() {
        return end;
    }
}
