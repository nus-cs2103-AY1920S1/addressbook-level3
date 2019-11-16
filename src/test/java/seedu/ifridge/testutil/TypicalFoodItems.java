package seedu.ifridge.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ifridge.model.food.Food;

/**
 * A utility class containing a list of {@code Food} objects to be used in tests.
 */
public class TypicalFoodItems {

    public static final Food OREO = new FoodBuilder().withName("Oreo")
            .withAmount("3units").build();
    public static final Food YAKITORI = new FoodBuilder().withName("Yakitori beef")
            .withAmount("5units").build();
    public static final Food DOUGHNUT = new FoodBuilder().withName("Krispy Kreme Doughnuts")
            .withAmount("8units").build();
    public static final Food CAI_PNG = new FoodBuilder().withName("Cai png")
            .withAmount("2units").build();
    public static final Food POKE = new FoodBuilder().withName("Poke")
            .withAmount("200g").build();
    public static final Food CROISSANT = new FoodBuilder().withName("Croissants")
            .withAmount("3units").build();
    public static final Food COKE = new FoodBuilder().withName("Coke")
            .withAmount("2L").build();

    // Manually added
    public static final Food CHEESECAKE = new FoodBuilder().withName("Cheesecake")
            .withAmount("5units").build();
    public static final Food MATCHA_LATTE = new FoodBuilder().withName("Matcha latte")
            .withAmount("1units").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Food MEATBALL = new FoodBuilder().withName("meatballs")
            .withAmount("200g").build();
    public static final Food MANGO_TOAST = new FoodBuilder().withName("mango toast")
            .withAmount("4units").build();

    public static final String KEYWORD_MATCHING_MEATBALL = "meatballs"; // A keyword that matches meatballs

    private TypicalFoodItems() {} // prevents instantiation

    /*
    public static Food getTypicalGroceryList() {
        GroceryList glist = new GroceryList();
        for (Food food : getTypicalFood()) {
            glist.addGroceryItem(food);
        }
        return glist;
    }*/

    public static List<Food> getTypicalFoodList() {
        return new ArrayList<>(Arrays.asList(OREO, YAKITORI, DOUGHNUT, CAI_PNG, POKE, CROISSANT, COKE));
    }
}
