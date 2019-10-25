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
    private static WasteMonth currentWasteMonth;
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

    @Override
    public WasteStatistic getWasteStatistic() {
        return WasteStatistic.getWasteStatistic(this.wasteList);
    }

    @Override
    public WasteMonth getWasteMonth() {
        return wasteList.getWasteMonth();
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
        /*******************************************************************
         * REMOVE THIS BOTTOM LINE LATER
         */
        currentWasteMonth = new WasteMonth(LocalDate.now());
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
     * Adds a food item to the waste list archive for a given waste month.
     *
     * @param item the food item to be added
     * @param wm the waste month in concern
     */
    public static void addFoodItemToArchive(GroceryItem item, WasteMonth wm) {
        if (!wasteArchive.containsKey(wm)) {
            createNewWasteMonth(wm);
        }
        WasteList archivedWasteList = wasteArchive.get(wm);
        archivedWasteList.addWasteItem(item);
    }

    /**
     * Creates a new waste month, unique food list key-value pair in wasteArchive.
     *
     * @param wm the waste month to be created
     */
    public static void createNewWasteMonth(WasteMonth wm) {
        wasteArchive.put(wm, new WasteList(wm));
    }

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

    public static WasteList getWasteListByMonth(WasteMonth wm) {
        return wasteArchive.get(wm);
    }

    public static WasteMonth getEarliestExistingWasteMonth() {
        return wasteArchive.firstKey();
    }

    public static WasteMonth getLatestExistingWasteMonth() {
        return wasteArchive.lastKey();
    }

    public static WasteStatistic getCurrentMonthPredictedWasteStatistic() {
        LocalDate today = LocalDate.now();
        WasteStatistic currentMonthStatistic = getCurrentWasteList().getWasteStatistic();
        WasteStatistic previousOneMonth = getWasteListByMonth(new WasteMonth(today.minusMonths(1)))
                .getWasteStatistic();
        WasteStatistic previousTwoMonth = getWasteListByMonth(new WasteMonth(today.minusMonths(2)))
                .getWasteStatistic();
        WasteStatistic previousThreeMonth = getWasteListByMonth(new WasteMonth(today.minusMonths(3)))
                .getWasteStatistic();
        return WasteStatistic.getPredictedWasteStatistic(currentMonthStatistic,
                previousOneMonth, previousTwoMonth, previousThreeMonth);
    }
}
