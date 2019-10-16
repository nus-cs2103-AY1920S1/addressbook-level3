package seedu.deliverymans.storage.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.database.CustomerDatabase;
import seedu.deliverymans.model.database.ReadOnlyCustomerDatabase;

/**
 * An Immutable CustomerDatabase that is serializable to JSON format.
 */

@JsonRootName(value = "customerdatabase")
public class JsonSerializableCustomerDatabase {

    public static final String MESSAGE_DUPLICATE_CUSTOMER = "Customers list contains duplicate customer(s).";

    private final List<JsonAdaptedCustomer> customers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCustomerDatabase} with the given persons.
     */
    @JsonCreator
    public JsonSerializableCustomerDatabase(@JsonProperty("persons") List<JsonAdaptedCustomer> customers) {
        this.customers.addAll(customers);
    }

    /**
     * Converts a given {@code ReadOnlyCustomerDatabase} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCustomerDatabase}.
     */
    public JsonSerializableCustomerDatabase(ReadOnlyCustomerDatabase source) {
        customers.addAll(source.getCustomerList().stream().map(JsonAdaptedCustomer::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code CustomerDatabase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CustomerDatabase toModelType() throws IllegalValueException {
        CustomerDatabase addressBook = new CustomerDatabase();
        for (JsonAdaptedCustomer jsonAdaptedCustomer : customers) {
            Customer customer = jsonAdaptedCustomer.toModelType();
            if (addressBook.hasCustomer(customer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CUSTOMER);
            }
            addressBook.addCustomer(customer);
        }
        return addressBook;
    }

}
