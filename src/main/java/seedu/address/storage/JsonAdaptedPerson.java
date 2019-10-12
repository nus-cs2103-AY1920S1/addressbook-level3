package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Age;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Donor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Type;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    protected final String name;
    protected final String phone;
    protected final String nric;
    protected final String type;

    //Data fields of Patient
    protected final String age;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("type") String type, @JsonProperty("nric") String nric,
            @JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("age") String age) {
        this.type = type;
        this.nric = nric;
        this.name = name;
        this.phone = phone;

        this.age = age;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        type = source.getType().value;
        nric = source.getNric().value;
        name = source.getName().fullName;
        phone = source.getPhone().value;

        if (source instanceof Doctor) {
            age = "";
        } else if (source instanceof Donor) {
            age = ((Donor) source).getAge().value;
        } else if (source instanceof Patient) {
            age = ((Patient) source).getAge().value;
        } else {
            age = "";
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }

        final Type modelType = new Type(type);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

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

        if (modelType.isDoctor()) {
            return new Doctor(modelType, modelNric, modelName, modelPhone);
        } else if (modelType.isDonor()) {
            if (age == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName()));
            }

            if (!Age.isValidAge(age)) {
                throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
            }
            final Age modelAge = new Age(age);

            return new Donor(modelType, modelNric, modelName, modelPhone, modelAge);
        } else if (modelType.isPatient()) {
            if (age == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName()));
            }

            if (!Age.isValidAge(age)) {
                throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
            }
            final Age modelAge = new Age(age);

            return new Patient(modelType, modelNric, modelName, modelPhone, modelAge);
        }

        return new Person(modelType, modelNric, modelName, modelPhone);
    }

}
