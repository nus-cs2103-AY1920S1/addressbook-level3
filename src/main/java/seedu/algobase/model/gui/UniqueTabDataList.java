package seedu.algobase.model.gui;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.gui.exceptions.DuplicateTabDataException;
import seedu.algobase.model.gui.exceptions.TabDataNotFoundException;

/**
 * A list of TabsData that enforces uniqueness between its elements and does not allow nulls.
 * A TabData is considered unique by comparing using {@code TabData#isSameTabData(TabData)}. As such, adding and
 * updating of tabData uses TabData#isSameTabData(TabData) for equality so as to ensure that the TabData being added
 * or updated is unique in terms of identity in the UniqueTabDataList. However, the removal of a TabData uses
 * TabData#equals(Object) so as to ensure that the TabData with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see TabData#isSameTabData(TabData)
 */
public class UniqueTabDataList implements Iterable<TabData> {

    private final ObservableList<TabData> internalList = FXCollections.observableArrayList();
    private final ObservableList<TabData> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent TabData as the given argument.
     */
    public boolean contains(TabData toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTabData);
    }

    /**
     * Returns the index of a TabData as the given argument.
     */
    public Index indexOf(TabData toCheck) throws NoSuchElementException {
        requireNonNull(toCheck);
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).isSameTabData(toCheck)) {
                return Index.fromZeroBased(i);
            }
        }
        throw new NoSuchElementException("The TabData does not exist");
    }

    /**
     * Adds a TabData to the list.
     * The TabData must not already exist in the list.
     */
    public void add(TabData toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTabDataException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds a list of TabsData to the list.
     * Each individual TabData must not already exist in the list.
     */
    public void addAll(TabData... tabsData) {
        requireNonNull(tabsData);
        for (TabData tabData : tabsData) {
            if (contains(tabData)) {
                throw new DuplicateTabDataException();
            }
        }
        for (TabData tabData : tabsData) {
            add(tabData);
        }
    }

    /**
     * Removes the equivalent TabData from the list.
     * The TabData must exist in the list.
     */
    public void remove(TabData toRemove) {
        requireNonNull(toRemove);
        for (TabData tabData : internalList) {
            if (tabData.isSameTabData(toRemove)) {
                internalList.remove(tabData);
                return;
            }
        }
        throw new TabDataNotFoundException();
    }

    /**
     * Replaces the TabData {@code target} in the list with {@code editedTabData}.
     * {@code target} must exist in the list.
     * The TabData identity of {@code editedTabData} must not be the same as another existing TabData in the list.
     */
    public void setTabData(TabData target, TabData editedTabData) {
        requireAllNonNull(target, editedTabData);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TabDataNotFoundException();
        }

        if (!target.isSameTabData(editedTabData) && contains(editedTabData)) {
            throw new DuplicateTabDataException();
        }

        internalList.set(index, editedTabData);
    }

    /**
     * Refreshes the {@code UniqueTabDataList}
     */
    public void refresh() {
        List<TabData> temp = new ArrayList<>(internalList);
        internalList.clear();
        internalList.setAll(temp);
    }

    public void setTabsData(UniqueTabDataList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tabsData}.
     * {@code tabsData} must not contain duplicate tabsData.
     */
    public void setTabsData(List<TabData> tabsData) {
        requireAllNonNull(tabsData);
        if (!tabsDataAreUnique(tabsData)) {
            throw new DuplicateTabDataException();
        }

        internalList.setAll(tabsData);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<TabData> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * @return the size of the UniqueTabDataList
     */
    public int size() {
        return internalList.size();
    }

    @Override
    public Iterator<TabData> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueTabDataList // instanceof handles nulls
            && internalList.equals(((UniqueTabDataList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code tabsData} contains only unique tabsData.
     */
    private boolean tabsDataAreUnique(List<TabData> tabsData) {
        for (int i = 0; i < tabsData.size() - 1; i++) {
            for (int j = i + 1; j < tabsData.size(); j++) {
                if (tabsData.get(i).isSameTabData(tabsData.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
