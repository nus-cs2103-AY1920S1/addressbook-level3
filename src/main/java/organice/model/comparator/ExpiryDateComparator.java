package organice.model.comparator;

import java.util.Comparator;

import organice.model.person.Donor;

/**
 * This comparator is used to sort a list of matched donors according to the donated organ expiry date.
 * Order: Earlier expiry date to later.
 */
public class ExpiryDateComparator implements Comparator<Donor> {

    @Override
    public int compare(Donor d1, Donor d2) {
        return d1.getOrganExpiryDate().compareTo(d2.getOrganExpiryDate());
    }
}
