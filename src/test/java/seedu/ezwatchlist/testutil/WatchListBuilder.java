package seedu.ezwatchlist.testutil;

import seedu.ezwatchlist.model.AddressBook;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.person.Person;
import seedu.ezwatchlist.model.show.Show;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class WatchListBuilder {

    private WatchList watchList;

    public WatchListBuilder() {
        watchList = new WatchList();
    }

    public WatchListBuilder(WatchList watchlist) {
        this.watchList = watchlist;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public WatchListBuilder withShow(Show show) {
        watchList.addShow(show);
        return this;
    }

    public WatchList build() {
        return watchList;
    }
}
