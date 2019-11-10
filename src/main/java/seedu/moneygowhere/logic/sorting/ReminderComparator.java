//@@author minpyaemoe-reused
//Reused from SpendingComparator @@author Nanosync
package seedu.moneygowhere.logic.sorting;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

import seedu.moneygowhere.model.reminder.Reminder;

/**
 * Compares Reminder objects.
 */
public class ReminderComparator implements Comparator<Reminder> {
    private Set<SortField> fields;

    /**
     * Creates a Reminder comparator, ordered by Date in descending order
     */
    public ReminderComparator() {
        fields = new LinkedHashSet<>();
        fields.add(new SortField(SortAttribute.DEADLINE, SortOrder.DESCENDING));
    }

    /**
     * Creates a Reminder comparator sorted by a defined order.
     *
     * @param fields Fields to be sorted and their order
     */
    public ReminderComparator(Set<SortField> fields) {
        this.fields = fields;
    }

    @Override
    public int compare(Reminder o1, Reminder o2) {
        int rank = 0;
        for (SortField field : fields) {
            if (rank != 0) {
                break;
            }

            switch (field.getAttribute()) {
            case DEADLINE:
                rank = -(o1.getDeadline().compareTo(o2.getDeadline()));
                break;
            case REMINDER_MESSAGE:
                rank = o1.getReminderMessage().compareTo(o2.getReminderMessage());
                break;
            default:
                throw new IllegalArgumentException("Reminder comparator field is unrecognised");
            }
            if (field.getOrder() == SortOrder.DESCENDING) {
                rank = -rank;
            }
        }
        return rank;
    }
}
