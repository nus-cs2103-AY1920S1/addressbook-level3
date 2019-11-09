package seedu.module.model.module;

import java.util.Comparator;

/**
 * Sorts deadline tasks according to priority and date and time.
 */
public class DeadlineComparator implements Comparator<Deadline> {
    @Override
    public int compare(Deadline d1, Deadline d2) {
        if (d1.getTag().equals(d2.getTag())) {
            return (d1.getDate().compareTo((d2).getDate()));
        }
        if (d1.getTag().equals("LOW")) {
            return 1;
        } else if (d1.getTag().equals("HIGH")) {
            return -1;
        } else if (d1.getTag().equals("MEDIUM") && d2.getTag().equals("LOW")) {
            return -1;
        } else {
            return 0;
        }
    }
}
