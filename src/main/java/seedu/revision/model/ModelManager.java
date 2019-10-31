package seedu.revision.model;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.revision.commons.core.GuiSettings;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.quiz.Statistics;

/**
 * Represents the in-memory model of the revision tool data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Answerable> filteredAnswerables;
    private final FilteredList<Statistics> filteredStatistics;
    private final History history;

    /**
     * Initializes a ModelManager with the given addressBook, userPrefs and quiz history.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ReadOnlyHistory history) {
        super();
        requireAllNonNull(addressBook, userPrefs, history);

        logger.fine("Initializing with revision tool: " + addressBook + " and user prefs " + userPrefs
                + " and quiz history " + history);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.history = new History(history);
        filteredAnswerables = new FilteredList<>(this.addressBook.getAnswerableList());
        filteredStatistics = new FilteredList<>(this.history.getStatisticsList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new History());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public Path getHistoryFilePath() {
        return userPrefs.getHistoryFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public void setHistoryFilePath(Path historyFilePath) {
        requireNonNull(historyFilePath);
        userPrefs.setHistoryFilePath(historyFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public void setHistory(ReadOnlyHistory history) {
        this.history.resetData(history);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public ReadOnlyHistory getHistory() {
        return history;
    }

    @Override
    public boolean hasAnswerable(Answerable answerable) {
        requireNonNull(answerable);
        return addressBook.hasAnswerable(answerable);
    }

    @Override
    public void deleteAnswerable(Answerable target) {
        addressBook.removeAnswerable(target);
    }

    @Override
    public void addAnswerable(Answerable answerable) {
        addressBook.addAnswerable(answerable);
        updateFilteredAnswerableList(PREDICATE_SHOW_ALL_ANSWERABLE);
    }

    @Override
    public void addStatistics(Statistics statistics) {
        history.addStatistics(statistics);
    }

    @Override
    public void setAnswerable(Answerable target, Answerable editedAnswerable) {
        requireAllNonNull(target, editedAnswerable);

        addressBook.setAnswerable(target, editedAnswerable);
    }

    //=========== Filtered Answerable List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Answerable} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Answerable> getFilteredAnswerableList() {
        return filteredAnswerables;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Statistics} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Statistics> getStatisticsList() {
        return filteredStatistics;
    }

    @Override
    public void updateFilteredAnswerableList(Predicate<Answerable> predicate) {
        requireNonNull(predicate);
        filteredAnswerables.setPredicate(predicate);
    }

    @Override
    public ObservableList<Answerable> getFilteredSortedAnswerableList(
            Predicate<Answerable> predicate, Comparator<Answerable> comparator) {
        requireNonNull(comparator);
        filteredAnswerables.setPredicate(predicate);
        return filteredAnswerables.sorted(comparator);
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
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && history.equals(other.history)
                && filteredAnswerables.equals(other.filteredAnswerables)
                && filteredStatistics.equals(other.filteredStatistics);
    }
}
