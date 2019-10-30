package seedu.address.model.statistics;

import java.util.function.Predicate;

import seedu.address.model.person.Entry;


/**
 * Filters the entrylist predicates based on the int month.
 */
public class EntryTimeContainsPredicate implements Predicate<Entry> {
    private int monthFromUser;
    private int yearFromUser;

    /**
     * Filters the entrylist based on the int monthFromUser and int yearFromUser.
     */
    public EntryTimeContainsPredicate(int monthFromUser, int yearFromUser) {
        this.monthFromUser = monthFromUser;
        this.yearFromUser = yearFromUser;
    }

    @Override
    public boolean test(Entry entry) {
        return monthFromUser == (entry.getDate().getDate().getMonth().getValue())
                && yearFromUser == (entry.getDate().getDate().getYear());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryTimeContainsPredicate // instanceof handles nulls
                && monthFromUser == ((((EntryTimeContainsPredicate) other).monthFromUser))
                && yearFromUser == ((((EntryTimeContainsPredicate) other).yearFromUser))); // state check
    }
}
