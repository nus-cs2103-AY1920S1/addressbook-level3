package seedu.ezwatchlist.storage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.commons.util.JsonUtil;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.TvSeason;
import seedu.ezwatchlist.model.show.TvShow;

public class JsonSerializableWatchListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableWatchListTest");
    private static final Path TYPICAL_SHOWS_FILE = TEST_DATA_FOLDER.resolve("typicalShowsWatchList.json");
    private static final Path INVALID_SHOW_FILE = TEST_DATA_FOLDER.resolve("invalidShowWatchList.json");
    private static final Path DUPLICATE_SHOW_FILE = TEST_DATA_FOLDER.resolve("duplicateShowWatchList.json");

    @Test
    public void toModelType_typicalActorsFile_success() throws Exception {
        /*JsonSerializableWatchList dataFromFile = JsonUtil.readJsonFile(TYPICAL_SHOWS_FILE,
                JsonSerializableWatchList.class).get();
        WatchList watchListFromFile = dataFromFile.toModelType();
        WatchList typicalShowsWatchList = TypicalShows.getTypicalWatchList();
        assertEquals(watchListFromFile, typicalShowsWatchList);*/
    }

    @Test
    public void toModelType_invalidShowFile_throwsIllegalValueException() throws Exception {
        JsonSerializableWatchList dataFromFile = JsonUtil.readJsonFile(INVALID_SHOW_FILE,
                JsonSerializableWatchList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateShows_throwsIllegalValueException() throws Exception {
        JsonSerializableWatchList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_SHOW_FILE,
                JsonSerializableWatchList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableWatchList.MESSAGE_DUPLICATE_SHOW,
                dataFromFile::toModelType);
    }

    @Test
    void constructor() throws IllegalValueException {
        WatchList watchList = new WatchList();
        Set<Actor> actors = new HashSet<>();
        JsonSerializableWatchList jsonSerializableWatchList = new JsonSerializableWatchList(watchList);
        assertTrue(jsonSerializableWatchList.getShows() instanceof JsonAdaptedShows);
        Movie movie = new Movie(new Name("test"), new Description("123"), new IsWatched("false"),
                new Date("24/09/1997"), new RunningTime(1), actors);

        TvShow tvShow = new TvShow(new Name("test1"), new Description("123"), new IsWatched("false"),
                new Date("24/09/1997"), new RunningTime(1), actors, 1, 1,
                new ArrayList<TvSeason>());
        watchList.addShow(movie);
        watchList.addShow(tvShow);
        assertTrue(new JsonSerializableWatchList(watchList).getShows() instanceof JsonAdaptedShows);
        assertTrue(new JsonSerializableWatchList(watchList).toModelType() instanceof WatchList);

    }
    @Test
    void toModelType() {
    }
}
