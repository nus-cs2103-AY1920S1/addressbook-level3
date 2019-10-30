//@@author e0031374
package tagline.model.group;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tagline.commons.core.GuiSettings;
import tagline.commons.core.LogsCenter;
import tagline.model.ReadOnlyUserPrefs;
import tagline.model.UserPrefs;

/**
 * Represents the in-memory model of the address book data.
 */
public class GroupManager implements GroupModel {
    private static final Logger logger = LogsCenter.getLogger(GroupManager.class);

    private final GroupBook groupBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Group> filteredGroups;

    /**
     * Initializes a GroupManager with the given groupBook and userPrefs.
     */
    public GroupManager(ReadOnlyGroupBook groupBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(groupBook, userPrefs);

        logger.fine("Initializing with address book: " + groupBook + " and user prefs " + userPrefs);

        this.groupBook = new GroupBook(groupBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredGroups = new FilteredList<>(this.groupBook.getGroupList());
    }

    public GroupManager(ReadOnlyGroupBook groupBook) {
        this(groupBook, new UserPrefs());
    }

    public GroupManager(ReadOnlyUserPrefs userPrefs) {
        this(new GroupBook(), userPrefs);
    }

    public GroupManager() {
        this(new GroupBook(), new UserPrefs());
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
    public Path getGroupBookFilePath() {
        return userPrefs.getGroupBookFilePath();
    }

    @Override
    public void setGroupBookFilePath(Path groupBookFilePath) {
        requireNonNull(groupBookFilePath);
        userPrefs.setGroupBookFilePath(groupBookFilePath);
    }

    //=========== GroupBook ================================================================================

    @Override
    public void setGroupBook(ReadOnlyGroupBook groupBook) {
        this.groupBook.resetData(groupBook);
    }

    @Override
    public ReadOnlyGroupBook getGroupBook() {
        return groupBook;
    }

    @Override
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groupBook.hasGroup(group);
    }

    @Override
    public void deleteGroup(Group target) {
        groupBook.removeGroup(target);
    }

    @Override
    public void addGroup(Group group) {
        groupBook.addGroup(group);
    }

    @Override
    public void setGroup(Group target, Group editedGroup) {
        requireAllNonNull(target, editedGroup);
        groupBook.setGroup(target, editedGroup);
    }

    //=========== Filtered Group List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Group} backed by the internal list of
     * {@code versionedGroupBook}
     */
    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return filteredGroups;
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        requireNonNull(predicate);
        filteredGroups.setPredicate(predicate);
    }

    @Override
    public ObservableList<Group> getFilteredGroupListWithPredicate(Predicate<Group> predicate) {
        requireNonNull(predicate);

        FilteredList<Group> filteredGroupsCopy = new FilteredList<>(groupBook.getGroupList());
        filteredGroupsCopy.setPredicate(predicate);
        return filteredGroupsCopy;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof GroupManager)) {
            return false;
        }

        // state check
        GroupManager other = (GroupManager) obj;
        return groupBook.equals(other.groupBook)
                && userPrefs.equals(other.userPrefs)
                && filteredGroups.equals(other.filteredGroups);
    }

}
