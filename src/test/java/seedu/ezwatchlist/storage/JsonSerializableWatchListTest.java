package seedu.ezwatchlist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.commons.util.JsonUtil;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.testutil.TypicalShows;

public class JsonSerializableWatchListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableWatchListTest");
    private static final Path TYPICAL_SHOWS_FILE = TEST_DATA_FOLDER.resolve("typicalShowsWatchList.json");
    private static final Path INVALID_SHOW_FILE = TEST_DATA_FOLDER.resolve("invalidShowWatchList.json");
    private static final Path DUPLICATE_SHOW_FILE = TEST_DATA_FOLDER.resolve("duplicateShowWatchList.json");
    /*
        @Test
        public void toModelType_typicalActorsFile_success() throws Exception {
            JsonSerializableWatchList dataFromFile = JsonUtil.readJsonFile(TYPICAL_SHOWS_FILE,
                    JsonSerializableWatchList.class).get();
            WatchList watchListFromFile = dataFromFile.toModelType();
            WatchList typicalShowsWatchList = TypicalShows.getTypicalWatchList();
            assertEquals(watchListFromFile, typicalShowsWatchList);
        }
    */
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

}
