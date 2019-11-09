package seedu.deliverymans.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.deliverymans.model.util.SampleDataUtil.getMenu;
import static seedu.deliverymans.model.util.SampleDataUtil.getTagSet;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.model.location.LocationMap;
import seedu.deliverymans.model.restaurant.Restaurant;

public class RestaurantTest {
    public static final Restaurant TEST_RESTAURANT = new Restaurant(new Name("TestRestaurant"),
            LocationMap.getLocation("Jurong").get(),
            getTagSet("TestTag1"),
            getMenu(new Food(new Name("TestFood"), new BigDecimal("6.5"), getTagSet())));

    @Test
    public void isSameRestaurant() {
        Restaurant editedTestRestaurant;

        // same object -> returns true
        assertTrue(TEST_RESTAURANT.isSameRestaurant(TEST_RESTAURANT));

        // null -> returns false
        assertFalse(TEST_RESTAURANT.isSameRestaurant(null));

        // different name -> returns false
        editedTestRestaurant = new Restaurant(new Name("NewTestRestaurant"),
                LocationMap.getLocation("Jurong").get(),
                getTagSet("TestTag1"),
                getMenu(new Food(new Name("TestFood"), new BigDecimal("6.5"), getTagSet())));
        assertFalse(TEST_RESTAURANT.isSameRestaurant(editedTestRestaurant));

        // same name, different attributes -> returns true
        editedTestRestaurant = new Restaurant(new Name("TestRestaurant"),
                LocationMap.getLocation("Changi").get(),
                getTagSet("TestTag2"),
                getMenu(new Food(new Name("NewTest Food"), new BigDecimal("6.6"), getTagSet())));
        assertTrue(TEST_RESTAURANT.isSameRestaurant(editedTestRestaurant));
    }

    @Test
    public void equals() {
        Restaurant editedTestRestaurant;

        // same values -> returns true
        editedTestRestaurant = new Restaurant(new Name("TestRestaurant"),
                LocationMap.getLocation("Jurong").get(),
                getTagSet("TestTag1"),
                getMenu(new Food(new Name("TestFood"), new BigDecimal("6.5"), getTagSet())));
        assertTrue(TEST_RESTAURANT.equals(editedTestRestaurant));

        // same object -> returns true
        assertTrue(TEST_RESTAURANT.equals(TEST_RESTAURANT));

        // null -> returns false
        assertFalse(TEST_RESTAURANT.equals(null));

        // different type -> returns false
        assertFalse(TEST_RESTAURANT.equals(5));

        // different name -> returns false
        editedTestRestaurant = new Restaurant(new Name("NewTestRestaurant"),
                LocationMap.getLocation("Jurong").get(),
                getTagSet("TestTag1"),
                getMenu(new Food(new Name("TestFood"), new BigDecimal("6.5"), getTagSet())));
        assertFalse(TEST_RESTAURANT.equals(editedTestRestaurant));

        // different location -> returns false
        editedTestRestaurant = new Restaurant(new Name("TestRestaurant"),
                LocationMap.getLocation("Changi").get(),
                getTagSet("TestTag1"),
                getMenu(new Food(new Name("TestFood"), new BigDecimal("6.5"), getTagSet())));
        assertFalse(TEST_RESTAURANT.equals(editedTestRestaurant));

        // different tags -> returns false
        editedTestRestaurant = new Restaurant(new Name("TestRestaurant"),
                LocationMap.getLocation("Jurong").get(),
                getTagSet("TestTag2"),
                getMenu(new Food(new Name("TestFood"), new BigDecimal("6.5"), getTagSet())));
        assertFalse(TEST_RESTAURANT.equals(editedTestRestaurant));

        // different menu -> returns false
        editedTestRestaurant = new Restaurant(new Name("TestRestaurant"),
                LocationMap.getLocation("Jurong").get(),
                getTagSet("TestTag1"),
                getMenu(new Food(new Name("NewTestFood"), new BigDecimal("6.5"), getTagSet())));
        assertFalse(TEST_RESTAURANT.equals(editedTestRestaurant));
    }
}
