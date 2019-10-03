package io.xpire.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import io.xpire.commons.core.GuiSettings;
import io.xpire.commons.core.LogsCenter;
import io.xpire.commons.util.CollectionUtil;
import io.xpire.model.item.Item;
import io.xpire.model.item.sort.MethodOfSorting;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * Represents the in-memory model of the expiryDateTracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ExpiryDateTracker expiryDateTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Item> filteredItems;
    private SortedList<Item> sortedItems;


    /**
     * Initializes a ModelManager with the given expiryDateTracker and userPrefs.
     */
    public ModelManager(ReadOnlyExpiryDateTracker expiryDateTracker, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(expiryDateTracker, userPrefs);

        logger.fine("Initializing with address book: " + expiryDateTracker + " and user prefs " + userPrefs);

        this.expiryDateTracker = new ExpiryDateTracker(expiryDateTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredItems = new FilteredList<>(this.expiryDateTracker.getItemList());
        sortedItems = new SortedList<>(this.expiryDateTracker.getItemList());
    }

    public ModelManager() {
        this(new ExpiryDateTracker(), new UserPrefs());
    }

    //=========== UserPrefs =========================================================================================

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
    public Path getExpiryDateTrackerFilePath() {
        return userPrefs.getExpiryDateTrackerFilePath();
    }

    @Override
    public void setExpiryDateTrackerFilePath(Path expiryDateTrackerFilePath) {
        requireNonNull(expiryDateTrackerFilePath);
        userPrefs.setExpiryDateTrackerFilePath(expiryDateTrackerFilePath);
    }

    //=========== expiryDateTracker  ================================================================================

    @Override
    public void setExpiryDateTracker(ReadOnlyExpiryDateTracker expiryDateTracker) {
        this.expiryDateTracker.resetData(expiryDateTracker);
    }

    @Override
    public ReadOnlyExpiryDateTracker getExpiryDateTracker() {
        return expiryDateTracker;
    }

    @Override
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return expiryDateTracker.hasItem(item);
    }

    @Override
    public void deleteItem(Item target) {
        expiryDateTracker.removeItem(target);
    }

    @Override
    public void addItem(Item item) {
        expiryDateTracker.addItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
    }

    @Override
    public void setItem(Item target, Item editedPerson) {
        CollectionUtil.requireAllNonNull(target, editedPerson);
        expiryDateTracker.setItem(target, editedPerson);
    }

    //=========== Sorted Item List Accessors ========================================================================


    @Override
    public void sortItemList(MethodOfSorting method) {
        requireNonNull(method);
        Comparator<Item> nameSorter = Comparator.comparing(l->l.getName().toString(),
                String.CASE_INSENSITIVE_ORDER);
        Comparator<Item> dateSorter = Comparator.comparing(l->l.getExpiryDate().getDate(),
                Comparator.nullsFirst(Comparator.naturalOrder()));

        switch (method.getValue()) {
        case "name":
            sortedItems = new SortedList<>(expiryDateTracker.getItemList() , nameSorter);
            break;
        case "date":
            sortedItems = new SortedList<>(expiryDateTracker.getItemList() , dateSorter);
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + method);
        }
        expiryDateTracker.setItems(sortedItems);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Item> getSortedItemList() {
        return sortedItems;
    }


    // =========== Filtered Item List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Item> getFilteredItemList() {
        return filteredItems;
    }

    @Override
    public void updateFilteredItemList(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
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
        return expiryDateTracker.equals(other.expiryDateTracker)
                && userPrefs.equals(other.userPrefs)
                && filteredItems.equals(other.filteredItems);
    }

}
