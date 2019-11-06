package seedu.deliverymans.storage.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.order.Order;

/**
 * Jackson-friendly version of {@link Order}.
 */
public class JsonAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    // private final String order;
    private final String orderName;
    private final String customer;
    private final String restaurant;
    private final String deliveryman;
    private final String isCompleted;
    private final List<JsonAdaptedFoodOrder> foodList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("orderName") String orderName,
                            @JsonProperty("customer") String customer,
                            @JsonProperty("restaurant") String restaurant,
                            @JsonProperty("deliveryman") String deliveryman,
                            @JsonProperty("foodList") List<JsonAdaptedFoodOrder> foodList,
                            @JsonProperty("status") String isCompleted) {
        this.orderName = orderName;
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
        orderName = source.getOrderName().fullName;
        customer = source.getCustomer().fullName;
        restaurant = source.getRestaurant().fullName;
        deliveryman = source.getDeliveryman().fullName;
        isCompleted = String.valueOf(source.isCompleted());
        foodList.addAll(source.getFoodList().entrySet().stream()
                .map(JsonAdaptedFoodOrder::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Order toModelType() throws IllegalValueException {
        final Map<Name, Integer> modelFoodMap = new HashMap<>();

        for (JsonAdaptedFoodOrder food : foodList) {
            Map.Entry<Name, Integer> entry = food.toModelType();
            modelFoodMap.put(entry.getKey(), entry.getValue());
        }

        if (orderName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(orderName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelOrderName = new Name(orderName);

        if (customer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(customer)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelCustomer = new Name(customer);

        if (restaurant == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(restaurant)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelRestaurant = new Name(restaurant);
        if (deliveryman == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(deliveryman)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelDeliveryman = new Name(deliveryman);

        final Boolean bool = Boolean.valueOf(isCompleted);
        if (bool == null) {
            throw new IllegalValueException("Invalid boolean detected for completion status of Order object");
        }

        Order order = new Order.OrderBuilder().setOrderName(modelOrderName).setCustomer(modelCustomer)
                .setRestaurant(modelRestaurant).setDeliveryman(modelDeliveryman).setFood(modelFoodMap)
                .setCompleted(bool).completeOrder();
        return order;
    }
}
