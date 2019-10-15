package seedu.weme.model;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.meme.Meme;
import seedu.weme.statistics.LikeData;
import seedu.weme.statistics.LikeManager;

/**
 * Represents the in-memory model of the meme book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final MemeBook memeBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Meme> filteredMemes;
    private final LikeData likeData;
    private final ObservableMap<String, Integer> observableLikeData;

    // ModelContext determines which parser to use at any point of time.
    private ModelContext context = ModelContext.CONTEXT_MEMES;

    /**
     * Initializes a ModelManager with the given memeBook and userPrefs.
     */
    public ModelManager(ReadOnlyMemeBook memeBook, ReadOnlyUserPrefs userPrefs, LikeData likeData) {
        super();
        requireAllNonNull(memeBook, userPrefs);

        logger.fine("Initializing with meme book: " + memeBook + " and user prefs " + userPrefs);

        this.memeBook = new MemeBook(memeBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMemes = new FilteredList<>(this.memeBook.getMemeList());
        this.likeData = likeData;
        this.observableLikeData = new ReadOnlyMapWrapper<String, Integer>(likeData.getLikeDataMap());
    }

    public ModelManager() {
        this(new MemeBook(), new UserPrefs(), new LikeManager());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getMemeBookFilePath() {
        return userPrefs.getMemeBookFilePath();
    }

    @Override
    public void setMemeBookFilePath(Path memeBookFilePath) {
        requireNonNull(memeBookFilePath);
        userPrefs.setMemeBookFilePath(memeBookFilePath);
    }

    //=========== MemeBook ================================================================================

    @Override
    public void setMemeBook(ReadOnlyMemeBook memeBook) {
        this.memeBook.resetData(memeBook);
    }

    @Override
    public ReadOnlyMemeBook getMemeBook() {
        return memeBook;
    }

    @Override
    public boolean hasMeme(Meme meme) {
        requireNonNull(meme);
        return memeBook.hasMeme(meme);
    }

    @Override
    public void deleteMeme(Meme target) {
        memeBook.removeMeme(target);
    }

    @Override
    public void addMeme(Meme meme) {
        memeBook.addMeme(meme);
        updateFilteredMemeList(PREDICATE_SHOW_ALL_MEMES);
    }

    @Override
    public void setMeme(Meme target, Meme editedMeme) {
        requireAllNonNull(target, editedMeme);

        memeBook.setMeme(target, editedMeme);
    }

    //=========== Filtered Meme List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Meme} backed by the internal list of
     * {@code versionedMemeBook}
     */
    @Override
    public ObservableList<Meme> getFilteredMemeList() {
        return filteredMemes;
    }

    @Override
    public void updateFilteredMemeList(Predicate<Meme> predicate) {
        requireNonNull(predicate);
        filteredMemes.setPredicate(predicate);
    }

    @Override
    public ModelContext getContext() {
        return context;
    }

    @Override
    public void setContext(ModelContext context) {
        this.context = context;
    }

    @Override
    public LikeData getLikeData() {
        return this.likeData;
    }

    @Override
    public ObservableMap<String, Integer> getObservableLikeData() {
        return observableLikeData;
    }

    @Override
    public void deleteLikesByMeme(Meme meme) {
        likeData.deleteLikesByMeme(meme);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return memeBook.equals(other.memeBook)
                && userPrefs.equals(other.userPrefs)
                && filteredMemes.equals(other.filteredMemes)
                && context.equals(other.context);
    }

}
