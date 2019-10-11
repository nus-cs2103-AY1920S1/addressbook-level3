package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ActivityBook;

/**
 * A class to access ActivityBook stored in the hard disk as a json file
 */

public class JsonActivityBookStorage implements ActivityBookStorage {

    private Path filePath;

    public JsonActivityBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getActivityBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ActivityBook> readActivityBook() throws DataConversionException {
        return readActivityBook(filePath);
    }

    /**
     * Similar to {@link #readActivityBook()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<ActivityBook> readActivityBook(Path prefsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(filePath, ActivityBook.class);
    }

    @Override
    public void saveActivityBook(ActivityBook activityBook) throws IOException {
        saveActivityBook(activityBook, filePath);
    }

    /**
     * Similar to {@link #saveActivityBook(ActivityBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveActivityBook(ActivityBook activityBook, Path filePath) throws IOException {
        requireNonNull(activityBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(activityBook, filePath);
    }

}
