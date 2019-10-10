package seedu.savenus.testutil;

import static seedu.savenus.logic.commands.CommandTestUtil.VALID_CATEGORY_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_CATEGORY_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_DESCRIPTION_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_NAME_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_NAME_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_OPENING_HOURS_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_OPENING_HOURS_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_PRICE_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_PRICE_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_RESTRICTIONS_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_RESTRICTIONS_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_CHICKEN;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_RICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.savenus.model.Menu;
import seedu.savenus.model.food.Food;

/**
 * A utility class containing a list of {@code Food} objects to be used in tests.
 */
public class TypicalFood {

    public static final Food CARBONARA = new FoodBuilder().withName("Carbonara")
            .withDescription("Italian noodle with cheese and meat")
            .withPrice("5.00").withCategory("Italian")
            .withTags("Noodle").withOpeningHours("0800 1700")
            .withRestrictions("Contains dairy and pork").build();
    public static final Food TONKATSU_RAMEN = new FoodBuilder().withName("Tonkotsu Ramen")
            .withPrice("11.00").withDescription("Japanese noodle with pork")
            .withCategory("Japanese")
            .withTags("Noodle", "Asian").withOpeningHours("0800 1800")
            .withRestrictions("Contains pork").build();
    public static final Food BAK_KUT_TEH = new FoodBuilder().withName("Bak Kut Teh").withPrice("10.00")
            .withCategory("Chinese")
            .withDescription("Chinese pork soup with spices").withTags("Asian")
            .withOpeningHours("0800 1800").withRestrictions("Contains pork").build();
    public static final Food TEH_PING = new FoodBuilder().withName("Teh Ping").withPrice("1.50")
            .withCategory("Chinese")
            .withDescription("Really good tea").withTags("Tea", "Leaves")
            .withOpeningHours("0000 2359").withRestrictions("May cause cancer").build();
    public static final Food MALA_XIANG_GUO = new FoodBuilder().withName("Mala Xiang Guo").withPrice("15.00")
            .withCategory("Chinese")
            .withDescription("Spicy chinese food").withTags("Spicy", "Vegetables")
            .withOpeningHours("0800 1930").withRestrictions("Not halal").build();
    public static final Food WAGYU_DONBURI = new FoodBuilder().withName("Wagyu Donburi with Teh").withPrice("17.99")
            .withCategory("Japanese")
            .withDescription("Japanese rice with wagyu beef, with tea").withTags("Expensive", "Beef", "Luxury")
            .withOpeningHours("1000 1700").withRestrictions("Expensive").build();
    public static final Food NASI_AYAM = new FoodBuilder().withName("Nasi Ayam").withPrice("4.80")
            .withCategory("Malay")
            .withDescription("Rice with fried chicken")
            .withTags("Chicken", "Roasted")
            .withOpeningHours("0900 1700").withRestrictions("Halal").build();
    // Manually added
    public static final Food BEE_HOON = new FoodBuilder().withName("Bee Hoon").withPrice("3.20")
            .withCategory("Chinese")
            .withDescription("Thin chinese noodles").build();
    public static final Food FISHBALL_NOODLES = new FoodBuilder().withName("Fishball noodles").withPrice("2.99")
            .withCategory("Chinese")
            .withDescription("Chinese square noodles with fishballs").build();

    // Manually added - Food's details found in {@code CommandTestUtil}
    public static final Food CHICKEN_RICE = new FoodBuilder().withName(VALID_NAME_CHICKEN_RICE)
            .withPrice(VALID_PRICE_CHICKEN_RICE)
            .withDescription(VALID_DESCRIPTION_CHICKEN_RICE).withCategory(VALID_CATEGORY_CHICKEN_RICE)
            .withTags(VALID_TAG_RICE).withOpeningHours(VALID_OPENING_HOURS_CHICKEN_RICE)
            .withRestrictions(VALID_RESTRICTIONS_CHICKEN_RICE).build();
    public static final Food NASI_LEMAK = new FoodBuilder().withName(VALID_NAME_NASI_LEMAK)
            .withPrice(VALID_PRICE_NASI_LEMAK)
            .withDescription(VALID_DESCRIPTION_NASI_LEMAK).withCategory(VALID_CATEGORY_NASI_LEMAK)
            .withTags(VALID_TAG_CHICKEN, VALID_TAG_RICE).withOpeningHours(VALID_OPENING_HOURS_NASI_LEMAK)
            .withRestrictions(VALID_RESTRICTIONS_NASI_LEMAK).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalFood() {} // prevents instantiation

    /**
     * Returns an {@code Menu} with all the typical foods.
     */
    public static Menu getTypicalMenu() {
        Menu menu = new Menu();
        for (Food food : getTypicalFood()) {
            menu.addFood(food);
        }
        return menu;
    }

    public static List<Food> getTypicalFood() {
        return new ArrayList<>(Arrays
        .asList(CARBONARA, TONKATSU_RAMEN, BAK_KUT_TEH, TEH_PING, MALA_XIANG_GUO, WAGYU_DONBURI, NASI_AYAM));
    }
}
