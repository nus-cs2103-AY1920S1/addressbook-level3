package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.WasteList;
import seedu.address.model.food.GroceryItem;

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
        WasteList ab = new WasteList();
        for (GroceryItem wasteItem: getTypicalWasteItems()) {
            ab.addWasteItem(wasteItem);
        }
        return ab;
    }

    public static List<GroceryItem> getTypicalWasteItems() {
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CAKE, DATES, EGGS, FRIES, GRAPES));
    }
}
