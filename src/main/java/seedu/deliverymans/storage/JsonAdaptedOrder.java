package seedu.deliverymans.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.addressbook.person.Email;
import seedu.deliverymans.model.addressbook.person.Name;
import seedu.deliverymans.model.addressbook.person.Phone;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Jackson-friendly version of {@link Order}.
 */
class JsonAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    private final String customer;
    private final String restaurant;
    private final String deliveryman;
    //    private final List<JsonAdaptedFood> foods = new ArrayList<>(); //implement food class

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("customer") String customer,
                            @JsonProperty("restaurant") String restaurant,
                            @JsonProperty("deliveryman") String deliveryman) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.deliveryman = deliveryman;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order source) {
        customer = source.getCustomer();
        restaurant = source.getRestaurant();
        deliveryman = source.getDeliveryman();
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Order toModelType() throws IllegalValueException {
        if (customer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(customer)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(customer);

        if (restaurant == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Restaurant.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(restaurant)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(restaurant);

        if (deliveryman == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deliveryman.class.getSimpleName()));
        }
        if (!Email.isValidEmail(deliveryman)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(deliveryman);

        return new Order("", customer, restaurant, deliveryman);
    }
}
