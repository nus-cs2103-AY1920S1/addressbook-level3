package seedu.deliverymans.storage.restaurant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.location.Location;
import seedu.deliverymans.model.location.LocationMap;
import seedu.deliverymans.model.restaurant.Rating;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Jackson-friendly version of {@link Restaurant}.
 */
public class JsonAdaptedRestaurant {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Restaurant's %s field is missing!";

    private final String name;
    private final String location;
    private final String rating;

    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    //private final List<JsonAdaptedOrder> order = new ArrayList<>();
    //private final List<JsonAdaptedFood> menu = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRestaurant} with the given restaurant details.
     */
    @JsonCreator
    public JsonAdaptedRestaurant(@JsonProperty("name") String name, @JsonProperty("location") String location,
                             @JsonProperty("rating") String rating,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Restaurant} into this class for Jackson use.
     */
    public JsonAdaptedRestaurant(Restaurant source) {
        name = source.getName().fullName;
        location = source.getLocation().name;
        rating = source.getRating().rating;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted restaurant object into the model's {@code Restaurant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted restaurant.
     */
    public Restaurant toModelType() throws IllegalValueException {
        final List<Tag> restaurantTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            restaurantTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!LocationMap.isValidLocation(location)) {
            throw new IllegalValueException(LocationMap.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = LocationMap.getLocation(location).get();

        if (rating == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName()));
        }
        if (!Rating.isValidRating(rating)) {
            throw new IllegalValueException(Rating.MESSAGE_CONSTRAINTS);
        }
        final Rating modelRating = new Rating(rating);

        final Set<Tag> modelTags = new HashSet<>(restaurantTags);
        return new Restaurant(modelName, modelLocation, modelRating, modelTags);
    }
}
