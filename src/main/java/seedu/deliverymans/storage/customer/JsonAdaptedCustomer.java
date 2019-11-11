package seedu.deliverymans.storage.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.customer.Address;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.storage.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Customer}.
 */
public class JsonAdaptedCustomer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Customer's %s field is missing!";

    private final String userName;
    private final String name;
    private final String phone;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedTotalTags> totalTags = new ArrayList<>();
    private final String noOfOrders;

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedCustomer(@JsonProperty("userName") String userName,
                               @JsonProperty("name") String name,
                               @JsonProperty("phone") String phone,
                               @JsonProperty("address") String address,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                               @JsonProperty("noOfOrders") String noOfOrders) {
        this.userName = userName;
        this.name = name;
        this.phone = phone;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.noOfOrders = noOfOrders;
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedCustomer(Customer source) {
        userName = source.getUserName().fullName;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        totalTags.addAll(source.getTotalTags().entrySet().stream()
                .map(JsonAdaptedTotalTags::new)
                .collect(Collectors.toList()));
        noOfOrders = String.valueOf(source.getNoOfOrders());
    }

    /**
     * Converts this Jackson-friendly adapted customer object into the model's {@code Customer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted customer.
     */
    public Customer toModelType() throws IllegalValueException {
        final List<Tag> customerTags = new ArrayList<>();
        final List<Order> customerOrders = new ArrayList<>();
        final Map<Tag, Integer> customerTotalTags = new HashMap<>();

        for (JsonAdaptedTag tag : tagged) {
            customerTags.add(tag.toModelType());
        }

        for (JsonAdaptedTotalTags totalTag : totalTags) {
            Map.Entry<Tag, Integer> entry = totalTag.toModelType();
            customerTotalTags.put(entry.getKey(), entry.getValue());
        }

        if (userName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(userName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelUserName = new Name(userName);

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

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        final Address modelAddress = new Address(address);

        if (noOfOrders == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }
        final int modelNoOfOrders = Integer.parseInt(noOfOrders);

        final Set<Tag> modelTags = new HashSet<>(customerTags);
        final ObservableList<Order> modelOrders = FXCollections.observableArrayList();
        modelOrders.addAll(customerOrders);
        final ObservableMap<Tag, Integer> modelTotalTags = FXCollections.observableHashMap();
        modelTotalTags.putAll(customerTotalTags);
        return new Customer(modelUserName, modelName, modelPhone, modelAddress, modelTags, modelTotalTags,
                modelNoOfOrders);
    }

}
