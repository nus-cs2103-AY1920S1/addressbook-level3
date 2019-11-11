package seedu.ifridge.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

import javafx.collections.ObservableList;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.UniqueWasteList;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.model.waste.WasteStatistic;

/**
 * Wraps all data at the Waste List level
 */
public class WasteList implements ReadOnlyWasteList {

    private static TreeMap<WasteMonth, WasteList> wasteArchive;
    private final UniqueWasteList wasteList;

    public WasteList(WasteMonth wasteMonth) {
        wasteList = new UniqueWasteList(wasteMonth);
    }

    /**
     * Creates a WasteList using the Persons in the {@code toBeCopied}
     */
    public WasteList(ReadOnlyWasteList toBeCopied, WasteMonth wasteMonth) {
        this(wasteMonth);
        resetData(toBeCopied);
    }

    //// Waste List overwrite options

    /**
     * Replaces the contents of the waste list with {@code foods}.
     */
    public void setWasteList(List<GroceryItem> foods) {
        this.wasteList.setGroceryList(foods);
    }

    /**
     * Resets the existing data of this {@code WasteList} with {@code newData}.
     */
    public void resetData(ReadOnlyWasteList newData) {
        requireNonNull(newData);

        setWasteList(newData.getWasteList());
    }

    //// Food-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasWasteItem(GroceryItem food) {
        requireNonNull(food);
        return wasteList.contains(food);
    }

    /**
     * Adds a grocery item to the waste list.
     */
    public void addWasteItem(GroceryItem p) {
        wasteList.add(p);
    }

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    @Override
    public ObservableList<GroceryItem> getWasteList() {
        return wasteList.asUnmodifiableObservableList();
    }

    /**
     * Returns the {@link WasteStatistic} for the waste list.
     */
    @Override
    public WasteStatistic getWasteStatistic() {
        return WasteStatistic.getWasteStatistic(this.wasteList);
    }

    /**
     * Returns the {@link WasteMonth} of the waste list.
     */
    @Override
    public WasteMonth getWasteMonth() {
        return wasteList.getWasteMonth();
    }

    /**
     * Returns the {@link UniqueWasteList} of the waste list.
     */
    @Override
    public UniqueWasteList getIterableWasteList() {
        return wasteList;
    }

    //// Waste List Archive operations

    /**
     * To initialize a waste list archive.
     */
    public static void initialiseWasteArchive() {
        if (wasteArchive != null) {
            return;
        }
        wasteArchive = new TreeMap<>();
    }

    /**
     * Getter method for waste archive.
     *
     * @return the waste archive.
     */
    public static TreeMap<WasteMonth, WasteList> getWasteArchive() {
        return wasteArchive;
    }

    /**
     * Adds a {@link WasteList} to the waste archive.
     */
    public static void addWastelistToArchive(WasteMonth wm, WasteList wasteList) {
        wasteArchive.put(wm, wasteList);
    }

    public static void addWasteArchive(TreeMap<WasteMonth, WasteList> existingWasteArchive) {
        wasteArchive.putAll(existingWasteArchive);
    }
    /**
     * Returns the current waste list.
     *
     * @return the current waste list.
     */
    public static WasteList getCurrentWasteList() {
        WasteMonth currentWasteMonth = new WasteMonth(LocalDate.now());
        if (wasteArchive.containsKey(currentWasteMonth)) {
            return wasteArchive.get(currentWasteMonth);
        } else {
            WasteList wasteList = new WasteList(currentWasteMonth);
            addWastelistToArchive(currentWasteMonth, wasteList);
            return wasteList;
        }
    }

    /**
     * Given a {@link WasteMonth}, returns the waste list for the month.
     */
    public static WasteList getWasteListByMonth(WasteMonth wm) {
        return wasteArchive.get(wm);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WasteList // instanceof handles nulls
                && wasteList.equals(((WasteList) other).wasteList));
    }
}
