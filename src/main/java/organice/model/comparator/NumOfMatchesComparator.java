package organice.model.comparator;

import java.util.Comparator;

import organice.model.person.MatchedPatient;

/**
 * This comparator is used to sort a list of matched patients according to the number of matched donors they have.
 * Order: from patients with most matches to those with the least matches.
 */
public class NumOfMatchesComparator implements Comparator<MatchedPatient> {

    @Override
    public int compare(MatchedPatient o1, MatchedPatient o2) {
        if (o1.getNumberOfMatches() < o2.getNumberOfMatches()) {
            return 1;
        } else if (o1.getNumberOfMatches() > o2.getNumberOfMatches()) {
            return -1;
        } else {
            return o1.getPriority().compareTo(o2.getPriority());
        }
    }
}
