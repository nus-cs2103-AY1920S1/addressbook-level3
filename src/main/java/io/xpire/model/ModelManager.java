package io.xpire.model;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;
import static io.xpire.model.ListType.XPIRE;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import io.xpire.commons.core.GuiSettings;
import io.xpire.commons.core.LogsCenter;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.sort.XpireMethodOfSorting;
import io.xpire.model.state.State;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

//@@author JermyTan
/**
 * Represents the in-memory model of Xpire app data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Xpire xpire;
    private final ReplenishList replenishList;
    private final UserPrefs userPrefs;
    private FilteredList<? extends Item> currentList;
    private ListType currentView;

    /**
     * Initializes a ModelManager with the given xpire and userPrefs.
     */
    public ModelManager(ReadOnlyListView[] lists,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(lists, userPrefs);

        logger.fine("Initializing with xpire: " + Arrays.toString(lists) + " and user prefs " + userPrefs);

        this.xpire = new Xpire(lists[0]);
        this.replenishList = new ReplenishList(lists[1]);
        this.userPrefs = new UserPrefs(userPrefs);
        this.setCurrentList(XPIRE);
    }

    public ModelManager() {
        this(new ReadOnlyListView<?>[]{new Xpire(), new ReplenishList()}, new UserPrefs());
    }

    //=========== UserPrefs =========================================================================================

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    /**
     * Returns the user prefs.
     */
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return this.userPrefs;
    }

    /**
     * Returns the user prefs' GUI settings.
     */
    @Override
    public GuiSettings getGuiSettings() {
        return this.userPrefs.getGuiSettings();
    }

    /**
     * Sets the user prefs' GUI settings.
     */
    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.userPrefs.setGuiSettings(guiSettings);
    }

    /**
     * Returns the user prefs' xpire file path.
     */
    @Override
    public Path getListFilePath() {
        return this.userPrefs.getListFilePath();
    }

    /**
     * Sets the user prefs' xpire file path.
     */
    @Override
    public void setListFilePath(Path xpireFilePath) {
        requireNonNull(xpireFilePath);
        this.userPrefs.setListFilePath(xpireFilePath);
    }

    /**
     * Returns an array containing xpire and replenish list.
     */
    @Override
    public ReadOnlyListView<? extends Item>[] getLists() {
        return new ReadOnlyListView<?>[]{this.getXpire(), this.getReplenishList()};
    }

    /**
     * Returns the {@code Xpire}.
     */
    @Override
    public Xpire getXpire() {
        return this.xpire;
    }

    /**
     * Returns the {@code ReplenishList}
     */
    @Override
    public ReplenishList getReplenishList() {
        return this.replenishList;
    }

    /**
     * Overrides the current {@code Xpire}'s data with another existing {@code Xpire}.
     *
     * @param xpire Another existing {@code Xpire}.
     */
    @Override
    public void setXpire(ReadOnlyListView<XpireItem> xpire) {
        this.xpire.resetData(xpire);
    }

    /**
     * Overrides the current {@code ReplenishList}'s data with another existing {@code ReplenishList}.
     *
     * @param replenishList Another existing {@code ReplenishList}.
     */
    @Override
    public void setReplenishList(ReadOnlyListView<Item> replenishList) {
        this.replenishList.resetData(replenishList);
    }

    /**
     * Returns the corresponding item list given the {@code ListType}.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     * @return Item list belonging to either {@code Xpire} or {@code ReplenishList}.
     */
    @Override
    public ObservableList<? extends Item> getItemList(ListType listType) {
        requireNonNull(listType);

        switch(listType) {
        case XPIRE:
            return this.xpire.getItemList();
        case REPLENISH:
            return this.replenishList.getItemList();
        default:
            logger.warning("Unknown list type");
            assert false;
            return null;
        }
    }

    /**
     * Checks if an item exists in the corresponding item list given the {@code ListType}.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     * @param item Item to be checked.
     * @return {@code true} if item already exists in the list else {@code false}.
     */
    @Override
    public boolean hasItem(ListType listType, Item item) {
        requireAllNonNull(listType, item);

        switch(listType) {
        case XPIRE:
            try {
                return this.xpire.hasItem((XpireItem) item);
            } catch (ClassCastException e) {
                logger.warning("Wrong item type for Xpire");
                return false;
            }
        case REPLENISH:
            return this.replenishList.hasItem(item);
        default:
            logger.warning("Unknown list type");
            assert false;
            return false;
        }
    }

    /**
     * Deletes an item in the corresponding item list given the {@code ListType}.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     * @param item Item to be deleted.
     */
    @Override
    public void deleteItem(ListType listType, Item item) {
        requireAllNonNull(listType, item);

        switch(listType) {
        case XPIRE:
            try {
                this.xpire.removeItem((XpireItem) item);
            } catch (ClassCastException e) {
                logger.warning("Wrong item type for Xpire");
            }
            break;
        case REPLENISH:
            this.replenishList.removeItem(item);
            break;
        default:
            logger.warning("Unknown list type");
            assert false;
        }
    }

    /**
     * Adds an item to the corresponding item list given the {@code ListType}.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     * @param item Item to be added.
     */
    @Override
    public void addItem(ListType listType, Item item) {
        requireAllNonNull(listType, item);

        switch(listType) {
        case XPIRE:
            try {
                this.xpire.addItem((XpireItem) item);
            } catch (ClassCastException e) {
                logger.warning("Wrong item type for Xpire");
            }
            break;
        case REPLENISH:
            this.replenishList.addItem(item);
            break;
        default:
            logger.warning("Unknown list type");
            assert false;
            return;
        }
        this.setCurrentList(this.currentView);
    }

    /**
     * Replaces an existing item with the new item in the corresponding item list given the {@code ListType}.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     * @param currentItem Existing item in the list to be replaced.
     * @param newItem New item to replace the existing item.
     */
    @Override
    public void setItem(ListType listType, Item currentItem, Item newItem) {
        requireAllNonNull(listType, currentItem, newItem);

        switch(listType) {
        case XPIRE:
            try {
                this.xpire.setItem((XpireItem) currentItem, (XpireItem) newItem);
            } catch (ClassCastException e) {
                logger.warning("Wrong item type for Xpire");
            }
            break;
        case REPLENISH:
            this.replenishList.setItem(currentItem, newItem);
            break;
        default:
            logger.warning("Unknown list type");
            assert false;
        }
    }

    /**
     * Updates the sorting method of the list in {@code Xpire}.
     *
     * @param method Sorting method.
     */
    @Override
    public void sortXpire(XpireMethodOfSorting method) {
        requireNonNull(method);
        this.xpire.setMethodOfSorting(method);
    }

    /**
     * Returns the current view list of {@code Item} backed by the internal list of
     * {@code Xpire} or {@code ReplenishList}.
     */
    @Override
    public FilteredList<? extends Item> getCurrentList() {
        return this.currentList;
    }

    /**
     * Updates the current view list to the corresponding item list given the {@code ListType}.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     */
    @Override
    public void setCurrentList(ListType listType) {
        requireAllNonNull(listType);

        switch(listType) {
        case XPIRE:
            this.currentList = new FilteredList<>(this.xpire.getItemList());
            break;
        case REPLENISH:
            this.currentList = new FilteredList<>(this.replenishList.getItemList());
            break;
        default:
            logger.warning("Unknown list type");
            assert false;
            return;
        }
        this.currentView = listType;
        this.currentList.setPredicate(PREDICATE_SHOW_ALL_ITEMS);
    }

    /**
     * Adds a new filtering constraint to the existing current view list.
     *
     * @param listType Either {@code XPIRE} or {@code REPLENISH}.
     * @param predicate Filtering constraint.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void filterCurrentList(ListType listType, Predicate<? extends Item> predicate) {
        requireAllNonNull(listType, predicate);

        try {
            switch (listType) {
            case XPIRE:
                FilteredList<XpireItem> xpireTemp = (FilteredList<XpireItem>) this.currentList;
                Predicate<XpireItem> newXpirePredicate = ((Predicate<XpireItem>) predicate)
                        .and(xpireTemp.getPredicate());
                xpireTemp.setPredicate(newXpirePredicate);
                break;
            case REPLENISH:
                FilteredList<Item> replenishTemp = ((FilteredList<Item>) this.currentList);
                Predicate<Item> newReplenishPredicate = ((Predicate<Item>) predicate)
                        .and(replenishTemp.getPredicate());
                replenishTemp.setPredicate(newReplenishPredicate);
                break;
            default:
                logger.warning("Unknown list type");
                assert false;
            }
        } catch (ClassCastException e) {
            logger.warning("List type and predicate type mismatch");
        }
    }

    /**
     * Update the entire state of Xpire.
     *
     * @param state New state.
     */
    @Override
    public void update(State state) {
        CloneModel clone = state.getCloneModel();
        this.setXpire(clone.getXpire());
        this.xpire.setMethodOfSorting(state.getMethod());
        this.setReplenishList(clone.getReplenishList());
        this.setUserPrefs(clone.getUserPrefs());
        this.setCurrentList(state.getListType());
        this.filterCurrentList(this.currentView, state.getPredicate());
    }

    /**
     * Returns the identifier of which list the current view list is showing.
     *
     * @return Either {@code XPIRE} or {@code REPLENISH}.
     */
    @Override
    public ListType getCurrentView() {
        return this.currentView;
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
                    && this.replenishList.equals(other.replenishList)
                    && this.userPrefs.equals(other.userPrefs)
                    && this.currentList.equals(other.currentList)
                    && this.currentView.equals(other.currentView);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.xpire, this.replenishList, this.userPrefs, this.currentList, this.currentView);
    }

}
