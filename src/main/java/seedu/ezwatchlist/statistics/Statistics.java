package seedu.ezwatchlist.statistics;

import javafx.collections.ObservableList;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.UniqueShowList;

public class Statistics {
    private final Model model;

    public Statistics (Model model) {
        this.model = model;
    }

    public ObservableList<Show> getForgotten() {
        ObservableList<Show> watchlist = model.getWatchList().getShowList().filtered(show -> !show.isWatched().value);
        UniqueShowList forgotten = new UniqueShowList();
        if (watchlist.size() > 5) {
            forgotten.add(watchlist.get(0));
            forgotten.add(watchlist.get(1));
            forgotten.add(watchlist.get(2));
        }
        return forgotten.asUnmodifiableObservableList();
    }
}
