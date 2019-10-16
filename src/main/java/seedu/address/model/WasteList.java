package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

import javafx.collections.ObservableList;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.UniqueFoodList;
import seedu.address.model.waste.WasteMonth;

/**
 * Wraps all data at the Waste List level
 */
public class WasteList implements ReadOnlyWasteList {

    private static TreeMap<WasteMonth, WasteList> wasteArchive;
    private static WasteMonth currentWasteMonth;
    private UniqueFoodList wasteList;

    public WasteList() {
        wasteList = new UniqueFoodList();
    }

    /**
     * Creates a WasteList using the Persons in the {@code toBeCopied}
     */
    public WasteList(ReadOnlyWasteList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// Waste List overwrite options

    /**
     * Replaces the contents of the waste list with {@code foods}.
     */
    public void setWasteList(List<GroceryItem> foods) {
        this.wasteList.setPersons(foods);
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
     * Adds a grocery item to the waste list.
     */
    public void addFoodItem(GroceryItem p) {
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


    //// Waste List Archive operations

    /**
     * To initialize a waste list archive.
     */
    public static void initialiseWasteArchive() {
        wasteArchive = new TreeMap<>();
        currentWasteMonth = new WasteMonth(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
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
        archivedWasteList.addFoodItem(item);
    }

    /**
     * Creates a new waste month, unique food list key-value pair in wasteArchive.
     *
     * @param wm the waste month to be created
     */
    public static void createNewWasteMonth(WasteMonth wm) {
        wasteArchive.put(wm, new WasteList());
    }

    /**
     * Returns the current waste list.
     *
     * @return the current waste list.
     */
    public static WasteList getCurrentWasteList() {
        return wasteArchive.get(new WasteMonth(LocalDate.now()));
    }
}
