package seedu.deliverymans.storage.deliveryman;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.deliveryman.Deliveryman;


/**
 * Jackson-friendly version of {@link Deliveryman}.
 */
public class JsonAdaptedDeliveryman {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deliveryman's %s field is missing!";

    private final String name;
    private final String phone;

    /**
     * Constructs a {@code JsonAdaptedDeliveryman} with the given deliveryman details.
     */
    @JsonCreator
    public JsonAdaptedDeliveryman(@JsonProperty("name") String name, @JsonProperty("phone") String phone) {
        this.name = name;
        this.phone = phone;
    }

    /**
     * Converts a given {@code Deliveryman} into this class for Jackson use.
     */
    public JsonAdaptedDeliveryman(Deliveryman source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
    }

    /**
     * Converts this Jackson-friendly adapted deliveryman object into the model's {@code Deliveryman} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted deliveryman.
     */
    public Deliveryman toModelType() throws IllegalValueException {
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

        return new Deliveryman(modelName, modelPhone);
    }

}
