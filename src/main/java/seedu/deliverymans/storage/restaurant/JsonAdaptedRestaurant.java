package seedu.deliverymans.storage.restaurant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.model.location.Location;
import seedu.deliverymans.model.location.LocationMap;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Rating;
import seedu.deliverymans.model.restaurant.Restaurant;
import seedu.deliverymans.storage.order.JsonAdaptedOrder;

/**
 * Jackson-friendly version of {@link Restaurant}.
 */
public class JsonAdaptedRestaurant {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Restaurant's %s field is missing!";

    private final String name;
    private final String location;
    private final JsonAdaptedRating rating;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedFood> menu = new ArrayList<>();
    private final List<JsonAdaptedOrder> orders = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRestaurant} with the given restaurant details.
     */
    @JsonCreator
    public JsonAdaptedRestaurant(@JsonProperty("name") String name, @JsonProperty("location") String location,
                             @JsonProperty("rating") JsonAdaptedRating rating,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                             @JsonProperty("menu") List<JsonAdaptedFood> menu,
                             @JsonProperty("orders") List<JsonAdaptedOrder> orders) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (menu != null) {
            this.menu.addAll(menu);
        }
        if (orders != null) {
            this.orders.addAll(orders);
        }
    }

    /**
     * Converts a given {@code Restaurant} into this class for Jackson use.
     */
    public JsonAdaptedRestaurant(Restaurant source) {
        name = source.getName().fullName;
        location = source.getLocation().name;
        rating = new JsonAdaptedRating(source.getRating());
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        menu.addAll(source.getMenu().stream()
                .map(JsonAdaptedFood::new)
                .collect(Collectors.toList()));
        orders.addAll(source.getOrders().stream()
                .map(JsonAdaptedOrder::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted restaurant object into the model's {@code Restaurant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted restaurant.
     */
    public Restaurant toModelType() throws IllegalValueException {
        final List<Tag> restaurantTags = new ArrayList<>();
        final List<Food> restaurantMenu = new ArrayList<>();
        final List<Order> restaurantOrders = new ArrayList<>();

        for (JsonAdaptedTag tag : tagged) {
            restaurantTags.add(tag.toModelType());
        }

        for (JsonAdaptedFood food : menu) {
            restaurantMenu.add(food.toModelType());
        }

        for (JsonAdaptedOrder order : orders) {
            restaurantOrders.add(order.toModelType());
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

        final Rating modelRating = rating.toModelType();

        final Set<Tag> modelTags = new HashSet<>(restaurantTags);
        final ObservableList<Food> modelMenu = FXCollections.observableArrayList();
        modelMenu.addAll(restaurantMenu);
        final ObservableList<Order> modelOrders = FXCollections.observableArrayList();
        modelOrders.addAll(restaurantOrders);
        return new Restaurant(modelName, modelLocation, modelRating, modelTags, modelMenu, modelOrders);
    }
}
