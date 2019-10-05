package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;


/**
 * Jackson-friendly version of {@link Participant}.
 */
class JsonAdaptedParticipant {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Participant's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String prefixTypeStr;
    private final int idNum;

    /**
     * Constructs a {@code JsonAdapterParticipant} with the given participant details.
     */
    @JsonCreator
    public JsonAdaptedParticipant(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("prefixTypeStr") String prefixTypeStr,
                             @JsonProperty("idNum") int idNum) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.prefixTypeStr = prefixTypeStr;
        this.idNum = idNum;
    }

    /**
     * Converts a given {@code Participant} into this class for Jackson use.
     */
    public JsonAdaptedParticipant(Participant source) {
        name = source.getName().toStorageValue();
        phone = source.getPhone().toStorageValue();
        email = source.getEmail().toStorageValue();
        prefixTypeStr = source.getId().getPrefix().name();
        idNum = source.getId().getNumber();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Participant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Participant toModelType() throws IllegalValueException {
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

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (prefixTypeStr == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PrefixType.class.getSimpleName()));
        }
        if (!PrefixType.isValidPrefixType(prefixTypeStr)) {
            throw new IllegalValueException(PrefixType.MESSAGE_CONSTRAINTS);
        }
        final PrefixType modelPrefixType = PrefixType.valueOf(prefixTypeStr);

        if (!Id.isValidNumber(idNum)) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS_INVALID_NUMBER);
        }
        final int modelIdNum = idNum;
        final Id modelId = new Id(modelPrefixType, modelIdNum);

        return new Participant(modelName, modelId, modelEmail, modelPhone);
    }

}
