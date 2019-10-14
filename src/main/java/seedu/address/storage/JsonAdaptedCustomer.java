package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.customer.ContactNumber;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.CustomerName;
import seedu.address.model.customer.Email;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Customer}.
 */
class JsonAdaptedCustomer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Customer's %s field is missing!";

    private final CustomerName customerName;
    private final ContactNumber contactNumber;
    private final Email email;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedCustomer(@JsonProperty("customerName") CustomerName customerName,
                               @JsonProperty("contactNumber") ContactNumber contactNumber,
                             @JsonProperty("email") Email email,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.email = email;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedCustomer(Customer source) {
        customerName = source.getCustomerName();
        contactNumber = source.getContactNumber();
        email = source.getEmail();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted customer object into the model's {@code Customer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted customer.
     */
    public Customer toModelType() throws IllegalValueException {
        final List<Tag> customerTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            customerTags.add(tag.toModelType());
        }

        if (customerName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CustomerName.class.getSimpleName()));
        }
        if (!CustomerName.isValidCustomerName(customerName.toString())) {
            throw new IllegalValueException(CustomerName.MESSAGE_CONSTRAINTS);
        }
        final CustomerName modelCustomerName = new CustomerName(customerName.toString());

        if (contactNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ContactNumber.class.getSimpleName()));
        }
        if (!ContactNumber.isValidContactNumber(contactNumber.toString())) {
            throw new IllegalValueException(ContactNumber.MESSAGE_CONSTRAINTS);
        }
        final ContactNumber modelContactNumber = new ContactNumber(contactNumber.toString());

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email.toString())) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email.toString());

        final Set<Tag> modelTags = new HashSet<>(customerTags);
        return new Customer(modelCustomerName, modelContactNumber, modelEmail, modelTags);
    }

}
