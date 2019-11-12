package seedu.eatme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eatme.commons.exceptions.DataConversionException;
import seedu.eatme.model.ReadOnlyEateryList;
import seedu.eatme.model.ReadOnlyFeedList;
import seedu.eatme.model.ReadOnlyUserPrefs;
import seedu.eatme.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends EateryListStorage, UserPrefsStorage, FeedListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getEateryListFilePath();

    @Override
    Optional<ReadOnlyEateryList> readEateryList() throws DataConversionException, IOException;

    @Override
    void saveEateryList(ReadOnlyEateryList eateryList) throws IOException;

    @Override
    Path getFeedListFilePath();

    @Override
    Optional<ReadOnlyFeedList> readFeedList() throws DataConversionException, IOException;

    @Override
    void saveFeedList(ReadOnlyFeedList feedList) throws IOException;

}
