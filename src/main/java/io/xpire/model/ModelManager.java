package io.xpire.model;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;
import static io.xpire.model.ListType.XPIRE;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Arrays;
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

/**
 * Represents the in-memory model of Xpire data.
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
    public ModelManager(ReadOnlyListView<? extends Item>[] lists,
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

    //=========== Model methods ================================================================================

    @Override
    public Xpire getXpire() {
        return this.xpire;
    }

    @Override
    public ReadOnlyListView<Item> getReplenishList() {
        return this.replenishList;
    }

    @Override
    public void setXpire(ReadOnlyListView<XpireItem> xpire) {
        this.xpire.resetData(xpire);
    }

    @Override
    public void setReplenishList(ReadOnlyListView<Item> replenishList) {
        this.replenishList.resetData(replenishList);
    }

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
            return;
        }
    }

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
            return;
        }
    }

    @Override
    public void sortXpire(XpireMethodOfSorting method) {
        requireNonNull(method);
        this.xpire.setMethodOfSorting(method);
    }

    /**
     * Returns an unmodifiable view of the current viewing list of {@code Item} backed by the internal list of
     * {@code Xpire} or {@code ReplenishList}.
     */
    @Override
    public FilteredList<? extends Item> getCurrentList() {
        return this.currentList;
    }

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
        this.currentList.setPredicate(PREDICATE_SHOW_ALL_ITEMS);
        this.currentView = listType;
    }

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
            this.logger.warning("List type and predicate type mismatch");
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
                    && this.currentList.equals(other.currentList);
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
        this.currentList = clone.getCurrentList();
    }

    @Override
    public ListType getCurrentView() {
        return this.currentView;
    }
}
