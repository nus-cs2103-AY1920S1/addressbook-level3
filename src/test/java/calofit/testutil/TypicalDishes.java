package calofit.testutil;

import static calofit.logic.commands.CommandTestUtil.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import calofit.model.dish.Dish;
import calofit.model.dish.DishDatabase;

/**
 * A utility class containing a list of {@code Dish} objects to be used in tests.
 */
public class TypicalDishes {

    public static final Dish ALICE = new DishBuilder().withName("Alice Pauline")
            .withTags("friends").build();
    public static final Dish BENSON = new DishBuilder().withName("Benson Meier")
            .withTags("owesMoney", "friends").build();
    public static final Dish CARL = new DishBuilder().withName("Carl Kurz").build();
    public static final Dish DANIEL = new DishBuilder().withName("Daniel Meier").withTags("friends").build();
    public static final Dish ELLE = new DishBuilder().withName("Elle Meyer").build();
    public static final Dish FIONA = new DishBuilder().withName("Fiona Kunz").build();
    public static final Dish GEORGE = new DishBuilder().withName("George Best").build();

    // Manually added
    public static final Dish HOON = new DishBuilder().withName("Hoon Meier").build();
    public static final Dish IDA = new DishBuilder().withName("Ida Mueller").build();

    // Manually added - Dish's details found in {@code CommandTestUtil}
    public static final Dish AMY = new DishBuilder().withName(VALID_NAME_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Dish BOB = new DishBuilder().withName(VALID_NAME_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalDishes() {} // prevents instantiation

    /**
     * Returns an {@code DishDatabase} with all the typical persons.
     */
    public static DishDatabase getTypicalDishDatabase() {
        DishDatabase ab = new DishDatabase();
        for (Dish dish : getTypicalDishes()) {
            ab.addDish(dish);
        }
        return ab;
    }

    public static List<Dish> getTypicalDishes() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
