package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DataBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.customer.Customer;

/**
 * An Immutable Customer DataBook that is serializable to JSON format.
 */
@JsonRootName(value = "customerbook")
class JsonSerializableCustomerBook {

    public static final String MESSAGE_DUPLICATE_CUSTOMER = "Customers list contains duplicate customer(s).";

    private final List<JsonAdaptedCustomer> customers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCustomerBook} with the given customers.
     */
    @JsonCreator
    public JsonSerializableCustomerBook(@JsonProperty("customers") List<JsonAdaptedCustomer> customers) {
        this.customers.addAll(customers);
    }

    /**
     * Converts a given {@code ReadOnlyCustomerBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCustomerBook}.
     */
    public JsonSerializableCustomerBook(ReadOnlyDataBook<Customer> source) {
        customers.addAll(source.getList().stream().map(JsonAdaptedCustomer::new).collect(Collectors.toList()));
    }

    /**
     * Converts this customer book into the model's {@code DataBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DataBook<Customer> toModelType() throws IllegalValueException {
        DataBook<Customer> customerBook = new DataBook<>();
        for (JsonAdaptedCustomer jsonAdaptedCustomer : customers) {
            Customer customer = jsonAdaptedCustomer.toModelType();
            if (customerBook.has(customer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CUSTOMER);
            }
            customerBook.add(customer);
        }
        return customerBook;
    }

}
