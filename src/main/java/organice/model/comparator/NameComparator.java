package organice.model.comparator;

import java.util.Comparator;

import organice.model.person.MatchedPatient;

/**
 * This comparator is used to sort a list of matched patients according to their name in alphabetical order.
 * Order: A - Z.
 */
public class NameComparator implements Comparator<MatchedPatient> {


    @Override
    public int compare(MatchedPatient o1, MatchedPatient o2) {
        return o1.getName().toString().compareTo(o2.getName().toString());
    }
}
