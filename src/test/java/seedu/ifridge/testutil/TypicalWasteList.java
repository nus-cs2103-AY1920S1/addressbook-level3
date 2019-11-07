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
            .withAmount("5units").withExpiryDate("18/10/2019").build();
    public static final GroceryItem BANANA = new GroceryItemBuilder().withName("Banana")
            .withAmount("8units").withExpiryDate("12/10/2019").build();
    public static final GroceryItem CAKE = new GroceryItemBuilder().withName("Cake")
            .withAmount("1units").withExpiryDate("20/10/2019").build();
    public static final GroceryItem DATES = new GroceryItemBuilder().withName("Dates")
            .withAmount("200g").withExpiryDate("15/10/2019").build();
    public static final GroceryItem EGGS = new GroceryItemBuilder().withName("Eggs")
            .withAmount("5units").withExpiryDate("12/10/2019").build();
    public static final GroceryItem FRIES = new GroceryItemBuilder().withName("Fries")
            .withAmount("500g").withExpiryDate("12/10/2019").build();
    public static final GroceryItem GRAPES = new GroceryItemBuilder().withName("Grapes")
            .withAmount("400g").withExpiryDate("10/10/2019").build();
    public static final GroceryItem HOT_SAUCE = new GroceryItemBuilder().withName("Hot Sauce")
            .withAmount("400ml").withExpiryDate("20/10/2019").build();
    public static final GroceryItem INDOMIE = new GroceryItemBuilder().withName("Indomie")
            .withAmount("1units").withExpiryDate("25/10/2019").build();
    public static final GroceryItem JUICE = new GroceryItemBuilder().withName("Juice")
            .withAmount("2L").withExpiryDate("24/10/2019").build();
    public static final GroceryItem KOKO_KRUNCH = new GroceryItemBuilder().withName("Koko Krunch")
            .withAmount("500g").withExpiryDate("12/10/2019").build();
    public static final GroceryItem LOBSTER = new GroceryItemBuilder().withName("Lobster")
            .withAmount("1units").withExpiryDate("13/10/2019").build();
    public static final GroceryItem MILO = new GroceryItemBuilder().withName("Milo")
            .withAmount("200ml").withExpiryDate("28/10/2019").build();

    // Manually Added
    public static final GroceryItem COFFEE = new GroceryItemBuilder().withName("Coffee")
            .withAmount("100ml").withExpiryDate("27/10/2019").build();
    public static final GroceryItem COOKIES = new GroceryItemBuilder().withName("Cookies")
            .withAmount("200g").withExpiryDate("20/10/2019").build();

    private TypicalWasteList() {} // prevents instantiation

    public static WasteList getTypicalWasteList() {
        WasteList wasteList = new WasteList(new WasteMonth(LocalDate.now()));
        for (GroceryItem item : getTypicalWasteItems()) {
            wasteList.addWasteItem(item);
        }
        return wasteList;
    }

    public static List<GroceryItem> getTypicalWasteItems() {
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CAKE, DATES, EGGS, FRIES, GRAPES, HOT_SAUCE,
                INDOMIE, JUICE, KOKO_KRUNCH, LOBSTER, MILO));
    }
}
