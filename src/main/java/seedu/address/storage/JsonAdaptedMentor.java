package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.SubjectName;

/**
 * Jackson-friendly version of {@link Mentor}.
 */
class JsonAdaptedMentor {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Mentor's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String organization;
    private final String subject;
    private final String prefixTypeStr;
    private final int idNum;

    /**
     * Constructs a {@code JsonAdapterMentor} with the given participant details.
     */
    @JsonCreator
    public JsonAdaptedMentor(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("organization") String organization,
                             @JsonProperty("subject") String subject,
                             @JsonProperty("prefixTypeStr") String prefixTypeStr,
                             @JsonProperty("idNum") int idNum) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.organization = organization;
        this.subject = subject;
        this.prefixTypeStr = prefixTypeStr;
        this.idNum = idNum;
    }

    /**
     * Converts a given {@code Mentor} into this class for Jackson use.
     */
    public JsonAdaptedMentor(Mentor source) {
        if (source == null) {
            //Handles the case for Optional<Mentor>
            name = null;
            phone = null;
            email = null;
            organization = null;
            subject = null;
            prefixTypeStr = null;
            idNum = -1;
        } else {
            //Handles actual Mentor object
            name = source.getName().toStorageValue();
            phone = source.getPhone().toStorageValue();
            email = source.getEmail().toStorageValue();
            organization = source.getOrganization().toStorageValue();
            subject = source.getSubject().toStorageValue();
            prefixTypeStr = source.getId().getPrefix().name();
            idNum = source.getId().getNumber();
        }
    }

    /**
     * Checks that the current object represents a Optional Mentor to be saved in Storage.
     * @return boolean indicating whether the JsonAdaptedMentor object represents an Optional mentor.
     */
    private boolean isOptionalMentor() {
        return name == null
                && phone == null
                && email == null
                && organization == null
                && subject == null
                && prefixTypeStr == null
                && idNum == -1;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Mentor} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Mentor toModelType() throws IllegalValueException {
        //Handles the case when the JsonAdaptedMentor object represents an Optional<Mentor>
        if (isOptionalMentor()) {
            return null;
        }

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

        if (organization == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()) + "(Organization)");
        }
        if (!Name.isValidName(organization)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelOrganization = new Name(organization);

        if (subject == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, SubjectName.class.getSimpleName()));
        }
        if (!SubjectName.isValidSubjectName(subject)) {
            throw new IllegalValueException(SubjectName.MESSAGE_CONSTRAINTS);
        }
        final SubjectName modelSubjectName = SubjectName.valueOf(subject);

        if (prefixTypeStr == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, PrefixType.class.getSimpleName()));
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

        return new Mentor(modelName, modelId, modelPhone, modelEmail, modelOrganization, modelSubjectName);
    }

}
