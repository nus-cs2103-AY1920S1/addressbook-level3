package seedu.deliverymans.storage.customer;

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
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.storage.order.JsonAdaptedOrder;
import seedu.deliverymans.storage.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Customer}.
 */
public class JsonAdaptedCustomer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Customer's %s field is missing!";

    private final String name;
    private final String phone;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedOrder> orders = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedCustomer(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                               @JsonProperty("orders") List<JsonAdaptedOrder> orders) {
        this.name = name;
        this.phone = phone;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (orders != null) {
            this.orders.addAll(orders);
        }
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedCustomer(Customer source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        orders.addAll(source.getOrders().stream()
                .map(JsonAdaptedOrder::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted customer object into the model's {@code Customer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted customer.
     */
    public Customer toModelType() throws IllegalValueException {
        final List<Tag> customerTags = new ArrayList<>();
        final List<Order> customerOrders = new ArrayList<>();

        for (JsonAdaptedTag tag : tagged) {
            customerTags.add(tag.toModelType());
        }

        for (JsonAdaptedOrder order : orders) {
            customerOrders.add(order.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        final Set<Tag> modelTags = new HashSet<>(customerTags);
        final ObservableList<Order> modelOrders = FXCollections.observableArrayList();
        modelOrders.addAll(customerOrders);
        return new Customer(modelName, modelPhone, modelTags, modelOrders);
    }

}
