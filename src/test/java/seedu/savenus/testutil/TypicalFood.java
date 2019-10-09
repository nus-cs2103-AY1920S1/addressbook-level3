package seedu.savenus.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.savenus.model.Menu;
import seedu.savenus.model.food.Food;

import static seedu.savenus.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Food} objects to be used in tests.
 */
public class TypicalFood {

    public static final Food CHICKEN_RICE = new FoodBuilder().withName("Chicken Rice")
            .withDescription("Chicken with Rice.")
            .withPrice("5.80")
            .withTags("friends").build();
    public static final Food NASI_LEMAK = new FoodBuilder().withName("Nasi Lemak")
            .withDescription("Coconut rice with cucumber").withPrice("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Food CARL = new FoodBuilder().withName("Carl Kurz").withPrice("95352563")
            .withDescription("Description!@$%^&*()").build();
    public static final Food DANIEL = new FoodBuilder().withName("Daniel Meier").withPrice("87652533")
            .withDescription("Daniel's Description?").withTags("friends").build();
    public static final Food ELLE = new FoodBuilder().withName("Elle Meyer").withPrice("9482224")
            .withDescription("description").build();
    public static final Food FIONA = new FoodBuilder().withName("Fiona Kunz").withPrice("9482427")
            .withDescription("another description lydia@example.com").build();
    public static final Food GEORGE = new FoodBuilder().withName("George Best").withPrice("9482442")
            .withDescription("last description").build();

    // Manually added
    public static final Food HOON = new FoodBuilder().withName("Hoon Meier").withPrice("8482424")
            .withDescription("new description").build();
    public static final Food IDA = new FoodBuilder().withName("Ida Mueller").withPrice("8482131")
            .withDescription("new description 2").build();

    // Manually added - Food's details found in {@code CommandTestUtil}
    public static final Food AMY = new FoodBuilder().withName(VALID_NAME_CHICKEN_RICE).withPrice(VALID_PRICE_CHICKEN_RICE)
            .withDescription(VALID_DESCRIPTION_CHICKEN_RICE).withTags(VALID_TAG_FRIEND).build();
    public static final Food BOB = new FoodBuilder().withName(VALID_NAME_NASI_LEMAK).withPrice(VALID_PRICE_NASI_LEMAK)
            .withDescription(VALID_DESCRIPTION_NASI_LEMAK).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalFood() {} // prevents instantiation

    /**
     * Returns an {@code Menu} with all the typical foods.
     */
    public static Menu getTypicalMenu() {
        Menu ab = new Menu();
        for (Food food : getTypicalFood()) {
            ab.addFood(food);
        }
        return ab;
    }

    public static List<Food> getTypicalFood() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
