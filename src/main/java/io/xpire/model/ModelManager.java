package io.xpire.model;

import static io.xpire.model.tag.Tag.EXPIRED_TAG;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.logging.Logger;

import io.xpire.commons.core.GuiSettings;
import io.xpire.commons.core.LogsCenter;
import io.xpire.commons.util.CollectionUtil;
import io.xpire.model.item.Item;
import io.xpire.model.item.ListToView;
import io.xpire.model.item.Name;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.exceptions.DuplicateItemException;
import io.xpire.model.item.sort.XpireMethodOfSorting;
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
    private final ReplenishList replenishList;
    private final UserPrefs userPrefs;
    private final FilteredList<XpireItem> filteredXpireItems;
    private final FilteredList<Item> filteredReplenishItems;
    private final FilteredList<XpireItem> previousXpireItems;
    private final FilteredList<Item> previousReplenishItems;
    private FilteredList<? extends Item> currentFilteredItems;

    /**
     * Initializes a ModelManager with the given xpire and userPrefs.
     */
    public ModelManager(ReadOnlyListView<? extends Item>[] lists,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(lists, userPrefs);

        logger.fine("Initializing with xpire: " + lists + " and user prefs " + userPrefs);

        this.xpire = new Xpire(lists[0]);
        this.replenishList = new ReplenishList(lists[1]);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredXpireItems = new FilteredList<>(this.xpire.getItemList());
        this.previousXpireItems = new FilteredList<>(this.xpire.getItemList());
        this.filteredReplenishItems = new FilteredList<>(this.replenishList.getItemList());
        this.previousReplenishItems = new FilteredList<>(this.replenishList.getItemList());
        this.currentFilteredItems = this.filteredXpireItems;

    }

    public ModelManager() {
        this(new ReadOnlyListView[]{new Xpire(), new ReplenishList()}, new UserPrefs());
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
    public Path getListFilePath() {
        return this.userPrefs.getListFilePath();
    }

    @Override
    public void setListFilePath(Path xpireFilePath) {
        requireNonNull(xpireFilePath);
        this.userPrefs.setListFilePath(xpireFilePath);
    }

    @Override
    public ReadOnlyListView<? extends Item>[] getLists() {
        return new ReadOnlyListView[]{this.xpire, this.replenishList};
    }

    //=========== expiryDateTracker  ================================================================================

    @Override
    public void setXpire(ReadOnlyListView<XpireItem> xpire) {
        this.xpire.resetData(xpire);
    }

    @Override
    public ReadOnlyListView<XpireItem> getXpire() {
        return this.xpire;
    }


    @Override
    public boolean hasItem(XpireItem xpireItem) {
        requireNonNull(xpireItem);
        return this.xpire.hasItem(xpireItem);
    }

    @Override
    public void deleteItem(XpireItem target) {
        this.xpire.removeItem(target);
    }

    @Override
    public void addItem(XpireItem xpireItem) {
        this.xpire.addItem(xpireItem);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
    }

    @Override
    public void setItem(XpireItem target, XpireItem editedXpireItem) {
        CollectionUtil.requireAllNonNull(target, editedXpireItem);
        this.xpire.setItem(target, editedXpireItem);
    }

    @Override
    public Set<Tag> getAllItemTags() {
        Set<Tag> tagSet = new TreeSet<>(new TagComparator());
        this.previousXpireItems.forEach(item -> tagSet.addAll(item.getTags()));
        return tagSet;
    }

    @Override
    public Set<Name> getAllItemNames() {
        Set<Name> nameSet = new TreeSet<>(Comparator.comparing(Name::toString));
        this.previousXpireItems.forEach(item -> nameSet.add(item.getName()));
        return nameSet;
    }

    //=========== replenish list methods  ==============================================================================
    @Override
    public void setReplenishList(ReadOnlyListView<Item> replenishList) {
        this.replenishList.resetData(replenishList);
    }

    @Override
    public ReadOnlyListView<Item> getReplenishList() {
        return this.replenishList;
    }

    @Override
    public boolean hasReplenishItem(Item item) {
        requireNonNull(item);
        return this.replenishList.hasItem(item);
    }

    @Override
    public void deleteReplenishItem(Item target) {
        this.replenishList.removeItem(target);
    }

    @Override
    public void addReplenishItem(Item item) throws DuplicateItemException {
        this.replenishList.addItem(item);
        updateFilteredReplenishItemList(PREDICATE_SHOW_ALL_REPLENISH_ITEMS);
    }

    @Override
    public void setReplenishItem(Item target, Item editedItem) {
        CollectionUtil.requireAllNonNull(target, editedItem);
        this.replenishList.setItem(target, editedItem);
    }

    @Override
    public Set<Tag> getAllReplenishItemTags() {
        Set<Tag> tagSet = new TreeSet<>(new TagComparator());
        List<Item> replenishItemList = this.replenishList.getItemList();
        this.previousReplenishItems.forEach(item -> tagSet.addAll(item.getTags()));
        return tagSet;
    }

    @Override
    public Set<Name> getAllReplenishItemNames() {
        Set<Name> nameSet = new TreeSet<>(Comparator.comparing(Name::toString));
        List<Item> replenishItemList = this.replenishList.getItemList();
        this.previousReplenishItems.forEach(item -> nameSet.add(item.getName()));
        return nameSet;
    }

    @Override
    public void shiftItemToReplenishList(XpireItem xpireItem) throws DuplicateItemException {
        Item adaptedItem = adaptItemToReplenish(xpireItem);
        addReplenishItem(adaptedItem);
        deleteItem(xpireItem);
    }

    /**
     * Adapts item to replenish item.
     * @param xpireItem The xpire item to adapt to replenish item.
     * @return The replenish item created.
     */
    private Item adaptItemToReplenish(XpireItem xpireItem) {
        Name itemName = xpireItem.getName();
        Set<Tag> originalTags = xpireItem.getTags();
        Set<Tag> newTags = new TreeSet<>(new TagComparator());
        for (Tag tag: originalTags) {
            if (!newTags.contains(tag) && !tag.equals(EXPIRED_TAG)) {
                newTags.add(tag);
            }
        }
        return new Item(itemName, newTags);
    }

    //=========== Sorted XpireItem List Accessors ======================================================================

    @Override
    public void sortItemList(XpireMethodOfSorting method) {
        requireNonNull(method);
        this.xpire.setMethodOfSorting(method);
    }

    // =========== Filtered XpireItem List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code XpireItem} backed by the internal list of
     * {@code versionedXpire}
     */
    @Override
    public ObservableList<XpireItem> getFilteredXpireItemList() {
        return this.filteredXpireItems;
    }

    @Override
    public ObservableList<Item> getFilteredReplenishItemList() {
        return this.filteredReplenishItems;
    }


    @Override
    public ObservableList<? extends Item> getCurrentFilteredItemList() {
        return this.currentFilteredItems;
    }

    @Override
    public void setCurrentFilteredItemList(ListToView list) {
        if (list.equals(new ListToView("main"))) {
            this.currentFilteredItems = this.filteredXpireItems;
        } else {
            this.currentFilteredItems = this.filteredReplenishItems;
        }
    }

    @Override
    public void updateFilteredItemList(Predicate<? extends Item> predicate) {
        if (this.currentFilteredItems == this.filteredXpireItems) {
            updateFilteredXpireItemList((Predicate<XpireItem>) predicate);
        } else if (this.currentFilteredItems == this.filteredReplenishItems) {
            updateFilteredReplenishItemList((Predicate<Item>) predicate);
        }
    }

    @Override
    public void updateFilteredXpireItemList(Predicate<XpireItem> predicate) {
        requireNonNull(predicate);
        Predicate<? super XpireItem> p = this.filteredXpireItems.getPredicate();
        this.previousXpireItems.setPredicate(p);
        if (predicate == PREDICATE_SHOW_ALL_ITEMS || p == null) {
            // a view command or first ever search command
            this.filteredXpireItems.setPredicate(predicate);
        } else {
            // search commands have been executed before
            this.filteredXpireItems.setPredicate(predicate.and(p));
        }
    }


    @Override
    public void updateFilteredReplenishItemList(Predicate<Item> predicate) {
        requireNonNull(predicate);
        Predicate<? super Item> p = this.filteredReplenishItems.getPredicate();
        this.previousReplenishItems.setPredicate(p);
        if (predicate == PREDICATE_SHOW_ALL_ITEMS || p == null) {
            this.filteredReplenishItems.setPredicate(predicate);
        } else {
            this.filteredReplenishItems.setPredicate(predicate.and(p));
        }
    }

    // =========== Tag XpireItem List Accessors =============================================================

    @Override
    public List<XpireItem> getAllItemList() {
        return this.xpire.getItemList();
    }

    @Override
    public List<Item> getReplenishItemList() {
        return this.replenishList.getItemList();
    }

    // =========== Item Manager Methods =============================================================

    @Override
    public void updateItemTags() {
        Iterator<XpireItem> itr = this.xpire.getIterator();
        XpireItem item;
        while (itr.hasNext()) {
            item = itr.next();
            if (item.isItemExpired()) {
                xpire.updateItemTag(item);
                //addItemToReplenishList(item);
                //shiftItemToReplenishList(item);
            }
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
                    && this.filteredXpireItems.equals(other.filteredXpireItems);
        }
    }
}
