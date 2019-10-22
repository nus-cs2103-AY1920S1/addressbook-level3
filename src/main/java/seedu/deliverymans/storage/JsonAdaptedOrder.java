package seedu.deliverymans.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.order.Order;

/**
 * Jackson-friendly version of {@link Order}.
 */
class JsonAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    private final String order;
    private final String customer;
    private final String restaurant;
    private final String deliveryman;
    private final String isCompleted;
    private final List<JsonAdaptedTag> foodList = new ArrayList<>(); //implement food class

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("order") String order,
                            @JsonProperty("customer") String customer,
                            @JsonProperty("restaurant") String restaurant,
                            @JsonProperty("deliveryman") String deliveryman,
                            @JsonProperty("food") List<JsonAdaptedTag> foodList,
                            @JsonProperty("status") String isCompleted) {
        this.order = order;
        this.customer = customer;
        this.restaurant = restaurant;
        this.deliveryman = deliveryman;
        this.isCompleted = isCompleted;
        if (foodList != null) {
            this.foodList.addAll(foodList);
        }
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order source) {
        order = source.getOrderName().fullName;
        customer = source.getCustomer().fullName;
        restaurant = source.getRestaurant().fullName;
        deliveryman = source.getDeliveryman().fullName;
        isCompleted = String.valueOf(source.isCompleted());
        foodList.addAll(source.getFood().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Order toModelType() throws IllegalValueException {
        final List<Tag> foodTags = new ArrayList<>();
        for (JsonAdaptedTag tag : foodList) {
            foodTags.add(tag.toModelType());
        }

        if (order == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(order)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name orderName = new Name(order);
        if (customer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(customer)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name customerName = new Name(customer);

        if (restaurant == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(restaurant)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name restaurantName = new Name(restaurant);
        if (deliveryman == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(deliveryman)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name deliveryName = new Name(deliveryman);

        final Set<Tag> modelFoodTags = new HashSet<>(foodTags);
        Order order = new Order(orderName, customerName, restaurantName, modelFoodTags);
        order.setDeliveryman(deliveryName);

        if (Boolean.parseBoolean(isCompleted)) {
            order.completeOrder();
        }
        return order;
    }
}
