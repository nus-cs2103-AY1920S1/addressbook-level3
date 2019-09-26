package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Mentor}.
 */
class JsonAdaptedMentor {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Mentor's %s field is missing!";
    private static final PrefixType[] prefixTypes = PrefixType.values();

    private final String name;
    private final String phone;
    private final String email;
    private final String organization;
    private final String subject;
    private final PrefixType prefixType;
    private final int idNum;

    /**
     * Constructs a {@code JsonAdapterMentor} with the given participant details.
     */
    @JsonCreator
    public JsonAdaptedMentor(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("organization") String organization,
                             @JsonProperty("subject") String subject, @JsonProperty("prefixType") PrefixType prefixType,
                             @JsonProperty("idNum") int idNum) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.organization = organization;
        this.subject = subject;
        this.prefixType = prefixType;
        this.idNum = idNum;
    }

    /**
     * Converts a given {@code Mentor} into this class for Jackson use.
     */
    public JsonAdaptedMentor(Mentor source) {
        name = source.getName().toString();
        phone = source.getPhone().toString();
        email = source.getEmail().toString();
        organization = source.getOrganization().toString();
        subject = source.getSubject().toString();
        prefixType = source.getId().getPrefix();
        idNum = source.getId().getNumber();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Mentor} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Mentor toModelType() throws IllegalValueException {
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()) + "(Organization)");
        }
        if (!Name.isValidName(organization)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelOrganization = new Name(organization);

        if (subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, SubjectName.class.getSimpleName()));
        }
        if (!SubjectName.isValidName(subject)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final SubjectName modelSubjectName = new SubjectName(subject);

        if (prefixType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PrefixType.class.getSimpleName()));
        }
        if (!PrefixType.isValidPrefixType(prefixType)) {
            throw new IllegalValueException(PrefixType.MESSAGE_CONSTRAINTS);
        }
        final PrefixType modelPrefixType = prefixType;

//        if (idNum == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ID Number in ID object");
//        }
        //Must find a way to validate idNum
        final int modelIdNum = idNum;
        final Id modelId = new Id(modelPrefixType, modelIdNum);

        return new Mentor(modelName, modelId, modelPhone, modelEmail, modelOrganization, modelSubjectName);
    }

}
