package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CustomerManager;
import seedu.address.model.person.Customer;

/**
 * A Task Manager that is serializable to JSON format.
 */
@JsonRootName(value = "customermanager")
public class JsonSerializableCustomerManager {

    public static final String MESSAGE_DUPLICATE_CUSTOMER = "Customer list contains duplicate customer(s).";

    private final List<JsonAdaptedCustomer> customers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCustomerManager} with the given customers.
     */
    @JsonCreator
    public JsonSerializableCustomerManager(@JsonProperty("customers") List<JsonAdaptedCustomer> customers) {
        this.customers.addAll(customers);
    }

    /**
     * Converts a given {@code CustomerManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCustomerManager}.
     */
    public JsonSerializableCustomerManager(CustomerManager source) {
        customers.addAll(source.getPersonList().stream().map(JsonAdaptedCustomer::new).collect(Collectors.toList()));
    }

    /**
     * Converts this task manager into the model's {@code TaskManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CustomerManager toModelType() throws IllegalValueException {
        CustomerManager customerManager = new CustomerManager();
        for (JsonAdaptedCustomer jsonAdaptedCustomer : customers) {
            Customer customer = jsonAdaptedCustomer.toModelType();
            if (customerManager.hasPerson(customer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CUSTOMER);
            }
            customerManager.addPerson(customer);
        }
        return customerManager;
    }
}
