package organice.model.comparator;

import java.util.Comparator;

import organice.model.person.MatchedPatient;

/**
 * This comparator is used to sort a list of matched patients according to their priority in descending order.
 * Order: from high to medium to low.
 */
public class PriorityComparator implements Comparator<MatchedPatient> {
    @Override
    public int compare(MatchedPatient o1, MatchedPatient o2) {
        return o2.getPriority().compareTo(o1.getPriority());
    }
}
