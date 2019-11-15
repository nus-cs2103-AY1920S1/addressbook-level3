package seedu.revision.model;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
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

    private final RevisionTool revisionTool;
    private final UserPrefs userPrefs;
    private final FilteredList<Answerable> filteredAnswerables;
    private final FilteredList<Statistics> filteredStatistics;
    private final History history;

    /**
     * Initializes a ModelManager with the given revisionTool, userPrefs and quiz history.
     */
    public ModelManager(ReadOnlyRevisionTool revisionTool, ReadOnlyUserPrefs userPrefs, ReadOnlyHistory history) {
        super();
        requireAllNonNull(revisionTool, userPrefs, history);

        logger.fine("Initializing with revision tool: " + revisionTool + " and user prefs " + userPrefs
                + " and quiz history " + history);

        this.revisionTool = new RevisionTool(revisionTool);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredAnswerables = new FilteredList<>(this.revisionTool.getAnswerableList());
        this.history = new History(history);
        filteredStatistics = new FilteredList<>(this.history.getStatisticsList());
    }

    public ModelManager() {
        this(new RevisionTool(), new UserPrefs(), new History());
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
    public Path getRevisionToolFilePath() {
        return userPrefs.getRevisionToolFilePath();
    }

    @Override
    public Path getHistoryFilePath() {
        return userPrefs.getHistoryFilePath();
    }

    @Override
    public void setRevisionToolFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setRevisionToolFilePath(addressBookFilePath);
    }

    //=========== RevisionTool ================================================================================
    @Override
    public void setHistoryFilePath(Path historyFilePath) {
        requireNonNull(historyFilePath);
        userPrefs.setHistoryFilePath(historyFilePath);
    }

    @Override
    public void setRevisionTool(ReadOnlyRevisionTool revisionTool) {
        this.revisionTool.resetData(revisionTool);
    }

    @Override
    public void setHistory(ReadOnlyHistory history) {
        this.history.resetData(history);
    }

    @Override
    public ReadOnlyRevisionTool getRevisionTool() {
        return revisionTool;
    }

    @Override
    public ReadOnlyHistory getHistory() {
        return history;
    }

    @Override
    public boolean hasAnswerable(Answerable answerable) {
        requireNonNull(answerable);
        return revisionTool.hasAnswerable(answerable);
    }

    @Override
    public void deleteAnswerable(Answerable target) {
        revisionTool.removeAnswerable(target);
    }

    @Override
    public void addAnswerable(Answerable answerable) {
        revisionTool.addAnswerable(answerable);
        updateFilteredAnswerableList(PREDICATE_SHOW_ALL_ANSWERABLE);
    }

    @Override
    public void addStatistics(Statistics statistics) {
        history.addStatistics(statistics);
    }

    @Override
    public void setAnswerable(Answerable target, Answerable editedAnswerable) {
        requireAllNonNull(target, editedAnswerable);

        revisionTool.setAnswerable(target, editedAnswerable);
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
    public void removeFiltersFromAnswerableList() {
        filteredAnswerables.setPredicate(PREDICATE_SHOW_ALL_ANSWERABLE);
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
        return revisionTool.equals(other.revisionTool)
                && userPrefs.equals(other.userPrefs)
                && history.equals(other.history)
                && filteredAnswerables.equals(other.filteredAnswerables)
                && filteredStatistics.equals(other.filteredStatistics);
    }
}
