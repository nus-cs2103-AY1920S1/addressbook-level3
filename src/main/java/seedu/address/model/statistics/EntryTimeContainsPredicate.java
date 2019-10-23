package seedu.address.model.statistics;

import java.util.function.Predicate;

import seedu.address.model.person.Entry;


/**
 * Filters the entrylist predicates based on the int month.
 */
public class EntryTimeContainsPredicate implements Predicate<Entry> {
    private int monthFromUser;

    /**
     * Filters the entrylist based on the int monthFromUser.
     */
    public EntryTimeContainsPredicate(int monthFromUser) {
        this.monthFromUser = monthFromUser;
    }

    @Override
    public boolean test(Entry entry) {
        return monthFromUser == (entry.getDate().getDate().getMonth().getValue());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryTimeContainsPredicate // instanceof handles nulls
                && monthFromUser == ((((EntryTimeContainsPredicate) other).monthFromUser))); // state check
    }
}
