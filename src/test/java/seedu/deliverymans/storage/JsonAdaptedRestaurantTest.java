package seedu.deliverymans.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.deliverymans.storage.restaurant.JsonAdaptedRestaurant.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.deliverymans.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.location.LocationMap;
import seedu.deliverymans.model.restaurant.Restaurant;
import seedu.deliverymans.storage.restaurant.JsonAdaptedFood;
import seedu.deliverymans.storage.restaurant.JsonAdaptedRating;
import seedu.deliverymans.storage.restaurant.JsonAdaptedRestaurant;
import seedu.deliverymans.storage.restaurant.JsonAdaptedTag;

public class JsonAdaptedRestaurantTest {
    private static final String INVALID_NAME = "T@@ties";
    private static final String INVALID_TAG = "FastF@@d";

    private static final Restaurant VALID_RESTAURANT = new Restaurant(new Name("Restaurant"),
            LocationMap.getLocation("Marina").get(),
            new HashSet<Tag>(Arrays.asList(new Tag[]{new Tag("Tag")})));
    private static final String VALID_NAME = VALID_RESTAURANT.getName().toString();
    private static final String VALID_LOCATION = VALID_RESTAURANT.getLocation().toString();
    private static final JsonAdaptedRating VALID_RATING = new JsonAdaptedRating(VALID_RESTAURANT.getRating());
    private static final List<JsonAdaptedTag> VALID_TAGS = VALID_RESTAURANT.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedFood> VALID_MENU = VALID_RESTAURANT.getMenu().stream()
            .map(JsonAdaptedFood::new)
            .collect(Collectors.toList());
    private static final int VALID_QUANTITY = VALID_RESTAURANT.getQuantityOrdered();

    @Test
    public void toModelType_validRestaurantDetails_returnsRestaurant() throws Exception {
        JsonAdaptedRestaurant adaptedRestaurant = new JsonAdaptedRestaurant(VALID_RESTAURANT);
        assertEquals(VALID_RESTAURANT, adaptedRestaurant.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(INVALID_NAME, VALID_LOCATION, VALID_RATING,
                VALID_TAGS, VALID_MENU, VALID_QUANTITY);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(null, VALID_LOCATION, VALID_RATING,
                VALID_TAGS, VALID_MENU, VALID_QUANTITY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(INVALID_NAME, VALID_LOCATION, VALID_RATING,
                invalidTags, VALID_MENU, VALID_QUANTITY);
        assertThrows(IllegalValueException.class, restaurant::toModelType);
    }
}
