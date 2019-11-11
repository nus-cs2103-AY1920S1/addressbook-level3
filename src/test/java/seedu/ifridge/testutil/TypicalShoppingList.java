package seedu.ifridge.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ifridge.model.ShoppingList;
import seedu.ifridge.model.food.ShoppingItem;

/**
 * A Typical Waste List
 */
public class TypicalShoppingList {

    public static final ShoppingItem APPLE = new ShoppingItemBuilder().withName("Apple")
            .withAmount("3units").build();
    public static final ShoppingItem BANANA = new ShoppingItemBuilder().withName("Banana")
            .withAmount("5g").build();
    public static final ShoppingItem CAKE = new ShoppingItemBuilder().withName("Cake")
            .withAmount("10g").build();
    public static final ShoppingItem DATES = new ShoppingItemBuilder().withName("Dates")
            .withAmount("11g").build();
    public static final ShoppingItem EGGS = new ShoppingItemBuilder().withName("Eggs")
            .withAmount("12units").build();
    public static final ShoppingItem FRIES = new ShoppingItemBuilder().withName("Fries")
            .withAmount("20g").build();
    public static final ShoppingItem GRAPES = new ShoppingItemBuilder().withName("Grapes")
            .withAmount("25g").build();
    public static final ShoppingItem ORANGE = new ShoppingItemBuilder().withName("Oranges")
            .withAmount("15units").build();
    public static final ShoppingItem BOUGHT_EGGS = new ShoppingItemBuilder().withName("Eggs")
            .withAmount("12units").withBought(true).build();
    public static final ShoppingItem URGENT_EGGS = new ShoppingItemBuilder().withName("Eggs")
            .withAmount("15g").withUrgent(true).build();
    public static final ShoppingItem SPAGHETTI = new ShoppingItemBuilder().withName("Spaghetti")
            .withAmount("3units").build();

    private TypicalShoppingList() {} // prevents instantiation

    public static ShoppingList getTypicalShoppingList() {
        ShoppingList ab = new ShoppingList();
        for (ShoppingItem shoppingItem: getTypicalShoppingItems()) {
            ab.addShoppingItem(shoppingItem);
        }
        return ab;
    }

    public static List<ShoppingItem> getTypicalShoppingItems() {
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CAKE, DATES, EGGS, FRIES, GRAPES));
    }
}
