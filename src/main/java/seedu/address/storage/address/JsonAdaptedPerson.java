package seedu.address.storage.address;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.ReferenceId;
import seedu.address.model.person.Person;
import seedu.address.model.person.parameters.Address;
import seedu.address.model.person.parameters.Email;
import seedu.address.model.person.parameters.Name;
import seedu.address.model.person.parameters.Phone;
import seedu.address.model.person.parameters.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String id;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("id") String id, @JsonProperty("name") String name,
            @JsonProperty("phone") String phone, @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        id = source.getReferenceId().toString();
        name = source.getName().toString();
        phone = source.getPhone().toString();
        email = source.getEmail().toString();
        address = source.getAddress().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType(boolean isStaff) throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ReferenceId.class.getSimpleName()));
        }
        final ReferenceId modelReferenceId = isStaff
                ? ParserUtil.parseStaffReferenceId(id)
                : ParserUtil.parsePatientReferenceId(id);

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

        Phone modelPhone;
        if (phone.isEmpty()) {
            modelPhone = Phone.EMPTY_PHONE_DETAILS;
        } else if (Phone.isValidPhone(phone)) {
            modelPhone = new Phone(phone);
        } else {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }

        Email modelEmail;
        if (email.isEmpty()) {
            modelEmail = Email.EMPTY_EMAIL_DETAILS;
        } else if (Email.isValidEmail(email)) {
            modelEmail = new Email(email);
        } else {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }

        Address modelAddress;
        if (address.isEmpty()) {
            modelAddress = Address.EMPTY_ADDRESS_DETAILS;
        } else if (Address.isValidAddress(address)) {
            modelAddress = new Address(address);
        } else {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelReferenceId, modelName, modelPhone, modelEmail, modelAddress, modelTags);
    }

}
