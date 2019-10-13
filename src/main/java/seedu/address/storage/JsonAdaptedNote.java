package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.note.Note;

/**
 * Jackson-friendly version of {@link Note}.
 */
public class JsonAdaptedNote {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String note;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedNote(@JsonProperty("note") String note, @JsonProperty("description") String description) {
        this.note = note;
        this.description = description;
    }

//    /**
//     * Converts a given {@code Person} into this class for Jackson use.
//     */
//    public JsonAdaptedNote(Person source) {
//        name = source.getName().fullName;
//        phone = source.getPhone().value;
//        email = source.getEmail().value;
//        address = source.getAddress().value;
//        tagged.addAll(source.getTags().stream()
//                .map(JsonAdaptedTag::new)
//                .collect(Collectors.toList()));
//    }

//    /**
//     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
//     *
//     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
//     */
//    public Person toModelType() throws IllegalValueException {
//        final List<Tag> personTags = new ArrayList<>();
//        for (JsonAdaptedTag tag : tagged) {
//            personTags.add(tag.toModelType());
//        }
//
//        if (name == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
//        }
//        if (!Name.isValidName(name)) {
//            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
//        }
//        final Name modelName = new Name(name);
//
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
//            throw new IllegalValueException(
//              String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
//        }
//        if (!Address.isValidAddress(address)) {
//            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
//        }
//        final Address modelAddress = new Address(address);
//
//        final Set<Tag> modelTags = new HashSet<>(personTags);
//        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags);
//    }
}
