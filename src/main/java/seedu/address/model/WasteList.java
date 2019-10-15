package seedu.address.model;

import static java.util.Objects.requireNonNull;

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

    private static TreeMap<WasteMonth, UniqueFoodList> wasteArchive;
    private final UniqueFoodList wasteList;

    {
        wasteList = new UniqueFoodList();
    }

    public WasteList() {}

    /**
     * Creates a WasteList using the Persons in the {@code toBeCopied}
     */
    public WasteList(ReadOnlyWasteList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * To initialize a waste list archive.
     */
    public static void initialiseWasteArchive() {
        wasteArchive = new TreeMap<>();
    }

    /**
     * Getter method for waste archive
     * @return
     */
    public static TreeMap<WasteMonth, UniqueFoodList> getWasteArchive() {
        return wasteArchive;
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
     * Adds a person to the waste list.
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

    public void addFoodItemToArchive(GroceryItem item, WasteMonth wm) {
        if (!wasteArchive.containsKey(wm)) {
            createNewWasteMonth(wm);
        }
        UniqueFoodList archivedWasteList = wasteArchive.get(wm);
        archivedWasteList.add(item);
    }

    public void createNewWasteMonth(WasteMonth wm) {
        wasteArchive.put(wm, new UniqueFoodList());
    }
}
