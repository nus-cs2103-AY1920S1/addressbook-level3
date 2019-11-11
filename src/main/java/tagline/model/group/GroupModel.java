//@@author e0031374
package tagline.model.group;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import tagline.commons.core.GuiSettings;
import tagline.model.ReadOnlyUserPrefs;

/**
 * The API of the GroupModel component.
 */
public interface GroupModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getGroupBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setGroupBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setGroupBook(ReadOnlyGroupBook addressBook);

    /** Returns the GroupBook */
    ReadOnlyGroupBook getGroupBook();

    /**
     * Returns true if a group with the same identity as {@code group} exists in the address book.
     */
    boolean hasGroup(Group group);

    /**
     * Returns a list of groups that contain the given contact id as one of their members.
     */
    public List<Group> findGroupsWithMember(MemberId memberId);

    /**
     * Returns true if a group with the same name as {@code groupName} exists in the address book.
     */
    boolean hasGroupName(GroupName groupName);

    /**
     * Deletes the given group.
     * The group must exist in the address book.
     */
    void deleteGroup(Group target);

    /**
     * Adds the given group.
     * {@code group} must not already exist in the address book.
     */
    void addGroup(Group group);

    /**
     * Replaces the given group {@code target} with {@code editedGroup}.
     * {@code target} must exist in the address book.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the address book.
     */
    void setGroup(Group target, Group editedGroup);

    /** Returns an unmodifiable view of the filtered group list */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Updates the filter of the filtered group list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Group> predicate);

    /**
     * Returns an unmodifiable copy of the filtered group list with a set predicate.
     * @throws NullPointerException if {@code predicate} is null.
     */
    ObservableList<Group> getFilteredGroupListWithPredicate(Predicate<Group> predicate);
}
