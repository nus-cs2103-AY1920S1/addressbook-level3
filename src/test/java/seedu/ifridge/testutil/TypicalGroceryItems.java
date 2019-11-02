package seedu.ifridge.testutil;

import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_TAG_CARBS;
import static seedu.ifridge.logic.commands.CommandTestUtil.VALID_TAG_VEGETABLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.food.GroceryItem;

/**
 * A utility class containing a list of {@code Food} objects to be used in tests.
 */
public class TypicalGroceryItems {

    public static final GroceryItem STRAWBERRIES = new GroceryItemBuilder().withName("Strawberries")
            .withAmount("300g").withExpiryDate("10/11/2019").withTags("berries", "fruits").build();
    public static final GroceryItem SPAGHETTI = new GroceryItemBuilder().withName("Spaghetti")
            .withAmount("3units").withExpiryDate("18/10/2020").withTags("dinner", "italian").build();
    public static final GroceryItem OLIVE_OIL = new GroceryItemBuilder().withName("Olive oil")
            .withAmount("1L").withExpiryDate("20/10/2015").withTags("healthy").build();
    public static final GroceryItem BROCCOLI = new GroceryItemBuilder().withName("Broccoli")
            .withAmount("1lbs").withExpiryDate("03/11/2019").withTags("greens", "healthy").build();
    public static final GroceryItem APPLES = new GroceryItemBuilder().withName("Apples")
            .withAmount("10units").withExpiryDate("02/12/2019").withTags("healthy", "fruits").build();
    public static final GroceryItem GREEN_TEA_LATTE = new GroceryItemBuilder().withName("Green tea latte")
            .withAmount("5units").withExpiryDate("05/11/2019").withTags("drink", "boba").build();
    public static final GroceryItem EGGS = new GroceryItemBuilder().withName("Eggs")
            .withAmount("12units").withExpiryDate("03/12/2019").build();

    // Manually added
    public static final GroceryItem RICE_WINE = (GroceryItem) new GroceryItemBuilder().withName("Rice wine")
            .withAmount("300ml").withExpiryDate("05/06/2021").build();
    public static final GroceryItem CAKE = (GroceryItem) new GroceryItemBuilder().withName("Cake")
            .withAmount("1units").withExpiryDate("05/10/2019").withTags("birthday").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final GroceryItem CARROTS = new GroceryItemBuilder().withName("carrots")
            .withAmount("2lbs").withExpiryDate("28/12/2019").withTags(VALID_TAG_VEGETABLE).build();
    public static final GroceryItem POTATOES = new GroceryItemBuilder().withName("potatoes")
            .withAmount("1.5lbs").withExpiryDate("27/11/2019").withTags(VALID_TAG_CARBS, VALID_TAG_VEGETABLE)
            .build();

    public static final String KEYWORD_MATCHING_WINE = "wine"; // A keyword that matches MEIER

    private TypicalGroceryItems() {} // prevents instantiation

    /**
     * Returns an {@code GroceryList} with all the typical persons.
     */
    public static GroceryList getTypicalGroceryList() {
        GroceryList glist = new GroceryList();
        for (GroceryItem food : getTypicalGroceryItems()) {
            glist.addGroceryItem(food);
        }
        return glist;
    }

    public static List<GroceryItem> getTypicalGroceryItems() {
        return new ArrayList<>(Arrays.asList(STRAWBERRIES, SPAGHETTI, OLIVE_OIL, BROCCOLI,
                APPLES, GREEN_TEA_LATTE, EGGS));
    }
}
