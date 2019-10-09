package seedu.address.testutil;

import static seedu.savenus.logic.commands.CommandTestUtil.VALID_CATEGORY_AMY;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_CATEGORY_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_OPENING_HOURS_AMY;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_OPENING_HOURS_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_PRICE_AMY;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_RESTRICTIONS_AMY;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_RESTRICTIONS_BOB;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

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

    public static final Food ALICE = new FoodBuilder().withName("Carbonara")
            .withDescription("Italian noodle with cheese and meat")
            .withPrice("5.00").withCategory("Italian")
            .withTags("Noodle").withOpeningHours("0800 1700")
            .withRestrictions("Contains dairy and pork").build();
    public static final Food BENSON = new FoodBuilder().withName("Tonkotsu Ramen")
            .withPrice("11.00").withDescription("Japanese noodle with pork")
            .withCategory("Japanese")
            .withTags("Noodle", "Asian").withOpeningHours("0800 1800")
            .withRestrictions("Contains pork").build();
    public static final Food CARL = new FoodBuilder().withName("Bak Kut Teh").withPrice("10.00")
            .withCategory("Chinese")
            .withDescription("Chinese pork soup with spices").withTags("Asian")
            .withOpeningHours("0800 1800").withRestrictions("Contains pork").build();
    public static final Food DANIEL = new FoodBuilder().withName("Indomie").withPrice("1.25")
            .withCategory("Indonesian")
            .withDescription("Really good indonesian noodles").withTags("Noodle", "Instant")
            .withOpeningHours("0000 2359").withRestrictions("May cause cancer").build();
    public static final Food ELLE = new FoodBuilder().withName("Bak Mala Xiang Guo").withPrice("15.00")
            .withCategory("Chinese")
            .withDescription("Spicy chinese salad").withTags("Spicy", "Vegetables")
            .withOpeningHours("0800 1930").withRestrictions("Not halal").build();
    public static final Food FIONA = new FoodBuilder().withName("Bak Wagyu Donburi").withPrice("17.99")
            .withCategory("Japanese")
            .withDescription("Japanese rice with wagyu beef").withTags("Expensive", "Beef", "Luxury")
            .withOpeningHours("1000 1700").withRestrictions("Expensive").build();
    public static final Food GEORGE = new FoodBuilder().withName("Chicken Rice").withPrice("4.80")
            .withCategory("Chinese")
            .withDescription("Fatty rice with chicken")
            .withTags("Chicken", "Roasted", "Steamed")
            .withOpeningHours("0900 1700").withRestrictions("Not halal").build();
    // Manually added
    public static final Food HOON = new FoodBuilder().withName("Bee Hoon").withPrice("3.20")
            .withCategory("Chinese")
            .withDescription("Thin chinese noodles").build();
    public static final Food IDA = new FoodBuilder().withName("Fishball noodles").withPrice("2.99")
            .withCategory("Chinese")
            .withDescription("Chinese square noodles with fishballs").build();

    // Manually added - Food's details found in {@code CommandTestUtil}
    public static final Food AMY = new FoodBuilder().withName(VALID_NAME_AMY).withPrice(VALID_PRICE_AMY)
            .withDescription(VALID_DESCRIPTION_AMY).withCategory(VALID_CATEGORY_AMY)
            .withTags(VALID_TAG_FRIEND).withOpeningHours(VALID_OPENING_HOURS_AMY)
            .withRestrictions(VALID_RESTRICTIONS_AMY).build();
    public static final Food BOB = new FoodBuilder().withName(VALID_NAME_BOB).withPrice(VALID_PRICE_BOB)
            .withDescription(VALID_DESCRIPTION_BOB).withCategory(VALID_CATEGORY_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withOpeningHours(VALID_OPENING_HOURS_BOB)
            .withRestrictions(VALID_RESTRICTIONS_BOB).build();

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
