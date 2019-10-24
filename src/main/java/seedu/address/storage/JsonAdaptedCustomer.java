package seedu.address.storage;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Customer}.
 */
public class JsonAdaptedCustomer extends JsonAdaptedPerson {

    public static final String INVALID_INTEGER_ID = "Customer has a invalid integer id.";

    private final String id;

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedCustomer(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                             @JsonProperty("customerId") String id) {
        super(name, phone, email, address, tagged);
        this.id = id;
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedCustomer(Customer source) {
        super(source);
        id = String.valueOf(source.getId());
    }

    /**
     * Converts this Jackson-friendly adapted customer object into the model's {@code Customer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    @Override
    public Customer toModelType() throws IllegalValueException {
        Person person = super.toModelType();
        final Name modelName = person.getName();
        final Phone modelPhone = person.getPhone();
        final Email modelEmail = person.getEmail();
        final Address modelAddress = person.getAddress();
        final Set<Tag> modelTags = person.getTags();

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"));
        }
        if (!Customer.isValidId(id)) {
            throw new IllegalValueException(String.format(INVALID_INTEGER_ID));
        }
        final int customerId = Integer.parseInt(id);

        return new Customer(customerId, modelName, modelPhone, modelEmail, modelAddress, modelTags);
    }
}
