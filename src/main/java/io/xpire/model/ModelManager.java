package io.xpire.model;

import static io.xpire.model.tag.Tag.EXPIRED_TAG;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Arrays;
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
import io.xpire.model.state.State;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of sthe xpire data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Xpire xpire;
    private final ReplenishList replenishList;
    private final UserPrefs userPrefs;
    private FilteredList<XpireItem> filteredXpireItems;
    private FilteredList<Item> filteredReplenishItems;
    private final FilteredList<XpireItem> previousXpireItems;
    private final FilteredList<Item> previousReplenishItems;
    private FilteredList<? extends Item> currentFilteredItems;
    private ListToView listToView;

    /**
     * Initializes a ModelManager with the given xpire and userPrefs.
     */
    public ModelManager(ReadOnlyListView<? extends Item>[] lists,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(lists, userPrefs);

        logger.fine("Initializing with xpire: " + Arrays.toString(lists) + " and user prefs " + userPrefs);

        this.xpire = new Xpire(lists[0]);
        this.replenishList = new ReplenishList(lists[1]);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredXpireItems = new FilteredList<>(this.xpire.getItemList());
        this.previousXpireItems = new FilteredList<>(this.xpire.getItemList());
        this.filteredReplenishItems = new FilteredList<>(this.replenishList.getItemList());
        this.previousReplenishItems = new FilteredList<>(this.replenishList.getItemList());
        this.currentFilteredItems = this.filteredXpireItems;
        this.listToView = new ListToView("main");

    }

    public ModelManager() {
        this(new ReadOnlyListView<?>[]{new Xpire(), new ReplenishList()}, new UserPrefs());
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
        return new ReadOnlyListView<?>[]{this.xpire, this.replenishList};
    }

    //=========== expiryDateTracker  ================================================================================

    @Override
    public void setXpire(ReadOnlyListView<XpireItem> xpire) {
        this.xpire.resetData(xpire);
    }

    @Override
    public Xpire getXpire() {
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
        update(this.listToView);
    }

    @Override
    public void addItem(XpireItem xpireItem) {
        this.xpire.addItem(xpireItem);
        update(this.listToView);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
    }

    @Override
    public void setItem(XpireItem target, XpireItem editedXpireItem) {
        CollectionUtil.requireAllNonNull(target, editedXpireItem);
        update(this.listToView);
        this.xpire.setItem(target, editedXpireItem);
    }

    //@@author febee99
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

    //@@author liawsy
    //=========== replenish list methods  ==============================================================================
    @Override
    public void setReplenishList(ReadOnlyListView<Item> replenishList) {
        this.replenishList.resetData(replenishList);
    }

    @Override
    public ReplenishList getReplenishList() {
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
        update(this.listToView);
    }

    @Override
    public void addReplenishItem(Item item) throws DuplicateItemException {
        this.replenishList.addItem(item);
        update(this.listToView);
        updateFilteredReplenishItemList(PREDICATE_SHOW_ALL_REPLENISH_ITEMS);
    }

    @Override
    public void setReplenishItem(Item target, Item editedItem) {
        CollectionUtil.requireAllNonNull(target, editedItem);
        this.replenishList.setItem(target, editedItem);
        update(this.listToView);
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
        update(this.listToView);
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

    //@@author febee99
    //=========== Sorted XpireItem List Accessors ======================================================================

    @Override
    public void sortItemList(XpireMethodOfSorting method) {
        requireNonNull(method);
        this.xpire.setMethodOfSorting(method);
        update(this.listToView);
    }

    //@@author liawsy
    // =========== Filtered XpireItem List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code XpireItem} backed by the internal list of
     * {@code versionedXpire}
     */
    @Override
    public FilteredList<XpireItem> getFilteredXpireItemList() {
        return this.filteredXpireItems;
    }

    @Override
    public FilteredList<Item> getFilteredReplenishItemList() {
        return this.filteredReplenishItems;
    }

    //@@author febee99
    @Override
    public FilteredList<? extends Item> getCurrentFilteredItemList() {
        return this.currentFilteredItems;
    }

    @Override
    public void setCurrentFilteredItemList(ListToView list) {
        this.listToView = list;
        if (list.equals(new ListToView("main"))) {
            this.currentFilteredItems = this.filteredXpireItems;
        } else {
            this.currentFilteredItems = this.filteredReplenishItems;
        }
    }

    @Override
    public void setCurrentFilteredItemList(FilteredList<? extends Item> list) {
        this.currentFilteredItems = list;
    }

    @Override
    public void setFilteredXpireItems(FilteredList<XpireItem> list) {
        this.filteredXpireItems = list;
    }

    @Override
    public void setFilteredReplenishItems(FilteredList<Item> list) {
        this.filteredReplenishItems = list;
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

    //@@author liawsy
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

    //@@author Kalsyc
    // =========== Tag XpireItem List Accessors =============================================================

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

    // =========== Undo/Redo Methods =============================================================

    @Override
    public void update(State state) {
        CloneModel clone = state.getCloneModel();
        this.setUserPrefs(clone.getUserPrefs());
        this.setXpire(clone.getXpire());
        this.xpire.setMethodOfSorting(state.getMethod());
        this.setReplenishList(clone.getReplenishList());
        this.setFilteredReplenishItems(clone.getFilteredReplenishItemList());
        this.setFilteredXpireItems(clone.getFilteredXpireItemList());
        this.setCurrentFilteredItemList(clone.getListToView());
    }

    @Override
    public void update(ListToView listToView) {
        FilteredList<XpireItem> filteredXpireList = new FilteredList<>(getXpire().getItemList());
        FilteredList<Item> filteredReplenishList = new FilteredList<>(getReplenishList().getItemList());
        filteredXpireList.setPredicate(this.filteredXpireItems.getPredicate());
        filteredReplenishList.setPredicate(this.filteredReplenishItems.getPredicate());
        setFilteredXpireItems(filteredXpireList);
        setFilteredReplenishItems(filteredReplenishList);
        setCurrentFilteredItemList(listToView);
    }

    @Override
    public ListToView getListToView() {
        return this.listToView;
    }
}
