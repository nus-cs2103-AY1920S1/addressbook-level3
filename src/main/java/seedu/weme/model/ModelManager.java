package seedu.weme.model;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.meme.Meme;

/**
 * Represents the in-memory model of the meme book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedMemeBook versionedMemeBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Meme> filteredMemes;

    // ModelContext determines which parser to use at any point of time.
    private SimpleObjectProperty<ModelContext> context = new SimpleObjectProperty<>(ModelContext.CONTEXT_MEMES);

    /**
     * Initializes a ModelManager with the given memeBook and userPrefs.
     */
    public ModelManager(ReadOnlyMemeBook memeBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(memeBook, userPrefs);

        logger.fine("Initializing with meme book: " + memeBook + " and user prefs " + userPrefs);

        versionedMemeBook = new VersionedMemeBook(memeBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMemes = new FilteredList<>(versionedMemeBook.getMemeList());
    }

    public ModelManager() {
        this(new MemeBook(), new UserPrefs());
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
        this.versionedMemeBook.resetData(memeBook);
    }

    @Override
    public ReadOnlyMemeBook getMemeBook() {
        return versionedMemeBook;
    }

    @Override
    public boolean hasMeme(Meme meme) {
        requireNonNull(meme);
        return versionedMemeBook.hasMeme(meme);
    }

    @Override
    public void deleteMeme(Meme target) {
        versionedMemeBook.removeMeme(target);
    }

    @Override
    public void addMeme(Meme meme) {
        versionedMemeBook.addMeme(meme);
        updateFilteredMemeList(PREDICATE_SHOW_ALL_MEMES);
    }

    @Override
    public void setMeme(Meme target, Meme editedMeme) {
        requireAllNonNull(target, editedMeme);

        versionedMemeBook.setMeme(target, editedMeme);
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
    public SimpleObjectProperty<ModelContext> getContext() {
        return context;
    }

    @Override
    public boolean canUndoMemeBook() {
        return versionedMemeBook.canUndo();
    }

    @Override
    public boolean canRedoMemeBook() {
        return versionedMemeBook.canRedo();
    }

    @Override
    public void undoMemeBook() {
        versionedMemeBook.undo();
    }

    @Override
    public void redoMemeBook() {
        versionedMemeBook.redo();
    }

    @Override
    public void commitMemeBook() {
        versionedMemeBook.commit();
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
        return versionedMemeBook.equals(other.versionedMemeBook)
                && userPrefs.equals(other.userPrefs)
                && filteredMemes.equals(other.filteredMemes)
                && context.getValue().equals(other.context.getValue());
    }

}
