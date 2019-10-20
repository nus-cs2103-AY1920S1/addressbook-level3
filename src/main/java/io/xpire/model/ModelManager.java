package io.xpire.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.logging.Logger;

import io.xpire.commons.core.GuiSettings;
import io.xpire.commons.core.LogsCenter;
import io.xpire.commons.util.CollectionUtil;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.sort.MethodOfSorting;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the xpire data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Xpire xpire;
    private final UserPrefs userPrefs;
    private final FilteredList<Item> filteredItems;

    /**
     * Initializes a ModelManager with the given xpire and userPrefs.
     */
    public ModelManager(ReadOnlyXpire xpire, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(xpire, userPrefs);

        logger.fine("Initializing with xpire: " + xpire + " and user prefs " + userPrefs);

        this.xpire = new Xpire(xpire);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredItems = new FilteredList<>(this.xpire.getItemList());
    }

    public ModelManager() {
        this(new Xpire(), new UserPrefs());
    }

    //=========== UserPrefs =========================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return this.userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return this.userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getXpireFilePath() {
        return this.userPrefs.getXpireFilePath();
    }

    @Override
    public void setXpireFilePath(Path xpireFilePath) {
        requireNonNull(xpireFilePath);
        this.userPrefs.setXpireFilePath(xpireFilePath);
    }

    //=========== expiryDateTracker  ================================================================================

    @Override
    public void setXpire(ReadOnlyXpire xpire) {
        this.xpire.resetData(xpire);
    }

    @Override
    public ReadOnlyXpire getXpire() {
        return this.xpire;
    }

    @Override
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return this.xpire.hasItem(item);
    }

    @Override
    public void deleteItem(Item target) {
        this.xpire.removeItem(target);
    }

    @Override
    public void addItem(Item item) {
        this.xpire.addItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
    }

    @Override
    public void setItem(Item target, Item editedItem) {
        CollectionUtil.requireAllNonNull(target, editedItem);
        this.xpire.setItem(target, editedItem);
    }

    @Override
    public Set<Tag> getAllItemTags() {
        Set<Tag> tagSet = new TreeSet<>(new TagComparator());
        List<Item> itemList = this.xpire.getItemList();
        itemList.forEach(item -> tagSet.addAll(item.getTags()));
        return tagSet;
    }

    @Override
    public Set<Name> getAllItemNames() {
        Set<Name> nameSet = new TreeSet<>(Comparator.comparing(Name::toString));
        List<Item> itemList = this.xpire.getItemList();
        itemList.forEach(item -> nameSet.add(item.getName()));
        return nameSet;
    }


    //=========== Sorted Item List Accessors ========================================================================

    @Override
    public void sortItemList(MethodOfSorting method) {
        requireNonNull(method);
        this.xpire.setMethodOfSorting(method);
    }

    // =========== Filtered Item List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Item} backed by the internal list of
     * {@code versionedXpire}
     */
    @Override
    public ObservableList<Item> getFilteredItemList() {
        return this.filteredItems;
    }

    @Override
    public void updateFilteredItemList(Predicate<Item> predicate) {
        requireNonNull(predicate);
        Predicate<? super Item> p = this.filteredItems.getPredicate();
        if (predicate == PREDICATE_SHOW_ALL_ITEMS || p == null) {
            // a view command or first ever search command
            this.filteredItems.setPredicate(predicate);
        } else {
            // search commands have been executed before
            this.filteredItems.setPredicate(predicate.and(p));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ModelManager)) {
            return false;
        } else {
            ModelManager other = (ModelManager) obj;
            return this.xpire.equals(other.xpire)
                    && this.userPrefs.equals(other.userPrefs)
                    && this.filteredItems.equals(other.filteredItems);
        }
    }
}
