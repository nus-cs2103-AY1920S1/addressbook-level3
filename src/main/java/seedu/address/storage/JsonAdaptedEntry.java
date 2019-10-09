package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Amount;
import seedu.address.model.person.Description;
import seedu.address.model.person.Entry;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Entry}.
 */
class JsonAdaptedEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Entry's %s field is missing!";

    private final String desc;
//    private final String phone;
//    private final String email;
//    private final String address;
    private final double amt;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedEntry(@JsonProperty("desc") String desc, @JsonProperty("amt") double amt,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.desc = desc;
        this.amt = amt;
//        this.phone = phone;
//        this.email = email;
//        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedEntry(Entry source) {
        desc = source.getDesc().fullDesc;
//        phone = source.getPhone().value;
//        email = source.getEmail().value;
//        address = source.getAddress().value;
        amt = source.getAmount().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Entry toModelType() throws IllegalValueException {
        final List<Tag> entryTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            entryTags.add(tag.toModelType());
        }

        if (desc == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(desc)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDesc = new Description(desc);

        final Amount modelAmt = new Amount(amt);
//        if (phone == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
//        }
//        if (!Phone.isValidPhone(phone)) {
//            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
//        }
//        final Phone modelPhone = new Phone(phone);
//
//        if (email == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
//        }
//        if (!Email.isValidEmail(email)) {
//            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
//        }
//        final Email modelEmail = new Email(email);
//
//        if (address == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
//        }
//        if (!Address.isValidAddress(address)) {
//            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
//        }
//        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(entryTags);
        return new Entry(modelDesc, modelAmt, modelTags);
    }

}
