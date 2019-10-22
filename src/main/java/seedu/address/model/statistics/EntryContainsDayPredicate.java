package seedu.address.model.statistics;

import seedu.address.model.person.Entry;

public class EntryContainsDayPredicate {

    private int dayFromUser;

    public EntryContainsDayPredicate(int dayFromUser) {
        this.dayFromUser = dayFromUser;
    }

    @Override
    public boolean test(Entry entry) {
        return dayFromUser == (entry.getDate().getDate().getDayOfMonth());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsDayPredicate // instanceof handles nulls
                && dayFromUser == ((((EntryContainsDayPredicate) other).dayFromUser))); // state check
    }

}
