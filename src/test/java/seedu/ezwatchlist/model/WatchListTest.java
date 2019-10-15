package seedu.ezwatchlist.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.exceptions.DuplicateShowException;
import seedu.ezwatchlist.testutil.ShowBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalShows.*;

public class WatchListTest {

    private final WatchList watchList = new WatchList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), watchList.getShowList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> watchList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyWatchList_replacesData() {
        WatchList newData = getTypicalWatchList();
        watchList.resetData(newData);
        assertEquals(newData, watchList);
    }

    @Test
    public void resetData_withDuplicateShows_throwsDuplicateShowException() {
        // Two Shows with the same identity fields
        Show editedAvengersEndgame = new ShowBuilder(AVENGERSENDGAME).build();
        List<Show> newShows = Arrays.asList(AVENGERSENDGAME, editedAvengersEndgame);
        WatchListStub newData = new WatchListStub(newShows);

        assertThrows(DuplicateShowException.class, () -> watchList.resetData(newData));
    }

    @Test
    public void hasShow_nullShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> watchList.hasShow(null));
    }

    @Test
    public void hasShow_showNotInWatchList_returnsFalse() {
        assertFalse(watchList.hasShow(AVENGERSENDGAME));
    }

    @Test
    public void hasShow_showInWatchList_returnsTrue() {
        watchList.addShow(AVENGERSENDGAME);
        assertTrue(watchList.hasShow(AVENGERSENDGAME));
    }

    @Test
    public void hasShow_showWithSameIdentityFieldsInWatchList_returnsTrue() {
        watchList.addShow(AVENGERSENDGAME);
        Show editedAvenger = new ShowBuilder(AVENGERSENDGAME).build();
        assertTrue(watchList.hasShow(editedAvenger));
    }

    @Test
    public void getShowList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> watchList.getShowList().remove(0));
    }

    /**
     * A stub ReadOnlyWatchList whose shows list can violate interface constraints.
     */
    private static class WatchListStub implements ReadOnlyWatchList {
        private final ObservableList<Show> shows = FXCollections.observableArrayList();

        WatchListStub(Collection<Show> shows) {
            this.shows.setAll(shows);
        }

        @Override
        public ObservableList<Show> getShowList() {
            return shows;
        }
    }

}
