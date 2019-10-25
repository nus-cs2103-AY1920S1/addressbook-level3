package seedu.ifridge.testutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.waste.WasteMonth;

/**
 * A Typical Waste List
 */
public class TypicalWasteList {

    public static final GroceryItem APPLE = new GroceryItemBuilder().withName("Apple")
            .withTags("friends").build();
    public static final GroceryItem BANANA = new GroceryItemBuilder().withName("Banana")
            .withTags("owesMoney", "friends").build();
    public static final GroceryItem CAKE = new GroceryItemBuilder().withName("Cake").build();
    public static final GroceryItem DATES = new GroceryItemBuilder().withName("Dates")
            .withTags("friends").build();
    public static final GroceryItem EGGS = new GroceryItemBuilder().withName("Eggs")
            .build();
    public static final GroceryItem FRIES = new GroceryItemBuilder().withName("Fries")
            .build();
    public static final GroceryItem GRAPES = new GroceryItemBuilder().withName("Grapes")
            .build();

    private TypicalWasteList() {} // prevents instantiation

    public static WasteList getTypicalWasteList() {
        WasteList wasteList = new WasteList(new WasteMonth(LocalDate.now()));
        for (GroceryItem item : getTypicalWasteItems()) {
            wasteList.addWasteItem(item);
        }
        return wasteList;
    }

    public static List<GroceryItem> getTypicalWasteItems() {
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CAKE, DATES, EGGS, FRIES, GRAPES));
    }
}
