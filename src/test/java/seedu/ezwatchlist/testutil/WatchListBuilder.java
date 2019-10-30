package seedu.ezwatchlist.testutil;

import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.show.Show;

/**
 * A utility class to help with building WatchList objects.
 * Example usage: <br>
 *     {@code WatchList wl = new WatchListBuilder().withShow(show).build();}
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
     * Adds a new {@code show} to the {@code WatchList} that we are building.
     */
    public WatchListBuilder withShow(Show show) {
        watchList.addShow(show);
        return this;
    }

    public WatchList build() {
        return watchList;
    }
}
