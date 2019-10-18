package com.typee.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.typee.commons.core.GuiSettings;
import com.typee.commons.core.LogsCenter;
import com.typee.commons.util.CollectionUtil;
import com.typee.logic.commands.exceptions.NullRedoableActionException;
import com.typee.logic.commands.exceptions.NullUndoableActionException;
import com.typee.model.engagement.Engagement;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final HistoryManager historyManager;
    private final UserPrefs userPrefs;
    private final FilteredList<Engagement> filteredEngagements;

    /**
     * Initializes a ModelManager with the given engagement list and userPrefs.
     */
    public ModelManager(ReadOnlyEngagementList engagementList, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(engagementList, userPrefs);

        logger.fine("Initializing with address book: " + engagementList + " and user prefs " + userPrefs);

        this.historyManager = new HistoryManager(engagementList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredEngagements = new FilteredList<>(this.historyManager.getEngagementList());
    }

    public ModelManager() {
        this(new EngagementList(), new UserPrefs());
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
    public Path getEngagementListFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setEngagementListFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== EngagementList ================================================================================

    @Override
    public void setHistoryManager(ReadOnlyEngagementList historyManager) {
        this.historyManager.resetData(historyManager);
    }

    @Override
    public ReadOnlyEngagementList getHistoryManager() {
        return historyManager;
    }

    @Override
    public boolean hasEngagement(Engagement engagement) {
        requireNonNull(engagement);
        return historyManager.hasEngagement(engagement);
    }

    public void deleteEngagement(Engagement target) {
        historyManager.removeEngagement(target);
    }

    @Override
    public void addEngagement(Engagement engagement) {
        historyManager.addEngagement(engagement);
        updateFilteredEngagementList(PREDICATE_SHOW_ALL_ENGAGEMENTS);
    }

    @Override
    public void setEngagement(Engagement target, Engagement editedEngagement) {
        CollectionUtil.requireAllNonNull(target, editedEngagement);
        historyManager.setEngagement(target, editedEngagement);
    }

    //=========== Filtered Engagement List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Engagement} backed by the internal list of
     * {@code typee}
     */
    @Override
    public ObservableList<Engagement> getFilteredEngagementList() {
        return filteredEngagements;
    }


    @Override
    public void updateFilteredEngagementList(Predicate<Engagement> predicate) {
        requireNonNull(predicate);
        filteredEngagements.setPredicate(predicate);
    }

    //=========== Undo ================================================================================

    @Override
    public boolean hasNoUndoableCommand() {
        return !historyManager.isUndoable();
    }

    @Override
    public void undoEngagementList() throws NullUndoableActionException {
        historyManager.undo();
    }

    //=========== Redo ================================================================================

    @Override
    public boolean hasNoRedoableCommand() {
        return !historyManager.isRedoable();
    }

    @Override
    public void redoEngagementList() throws NullRedoableActionException {
        historyManager.redo();
    }

    @Override
    public void saveEngagementList() {
        historyManager.saveState();
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
        return historyManager.equals(other.historyManager)
                && userPrefs.equals(other.userPrefs)
                && filteredEngagements.equals(other.filteredEngagements);
    }

}
