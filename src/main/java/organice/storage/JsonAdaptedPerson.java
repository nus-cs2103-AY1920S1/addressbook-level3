package organice.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import organice.commons.exceptions.IllegalValueException;
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.Doctor;
import organice.model.person.DoctorInCharge;
import organice.model.person.Donor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.TissueType;
import organice.model.person.Type;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    protected final String name;
    protected final String phone;
    protected final String nric;
    protected final String type;

    protected final String age;
    protected final String priority;
    protected final String bloodType;
    protected final String tissueType;
    protected final String organ;
    protected final String doctorInCharge;
    protected final String organExpiryDate;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("type") String type, @JsonProperty("nric") String nric,
            @JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("age") String age, @JsonProperty("priority") String priority,
            @JsonProperty("bloodType") String bloodType, @JsonProperty("tissueType") String tissueType,
            @JsonProperty("organ") String organ, @JsonProperty("doctorInCharge") String doctorInCharge,
            @JsonProperty("organExpiryDate") String organExpiryDate) {
        this.type = type;
        this.nric = nric;
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.priority = priority;
        this.bloodType = bloodType;
        this.tissueType = tissueType;
        this.organ = organ;
        this.doctorInCharge = doctorInCharge;
        this.organExpiryDate = organExpiryDate;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        type = source.getType().value;
        nric = source.getNric().value;
        name = source.getName().fullName;
        phone = source.getPhone().value;

        if (source instanceof Patient) {
            age = ((Patient) source).getAge().value;
            priority = ((Patient) source).getPriority().value;
            bloodType = ((Patient) source).getBloodType().value;
            tissueType = ((Patient) source).getTissueType().value;
            organ = ((Patient) source).getOrgan().value;
            doctorInCharge = ((Patient) source).getDoctorInCharge().value;
            organExpiryDate = "";
        } else if (source instanceof Donor) {
            age = ((Donor) source).getAge().value;
            priority = "";
            bloodType = ((Donor) source).getBloodType().value;
            tissueType = ((Donor) source).getTissueType().value;
            organ = ((Donor) source).getOrgan().value;
            doctorInCharge = "";
            organExpiryDate = ((Donor) source).getOrganExpiryDate().value;
        } else if (source instanceof Doctor) {
            age = "";
            priority = "";
            bloodType = "";
            tissueType = "";
            organ = "";
            doctorInCharge = "";
            organExpiryDate = "";
        } else {
            age = "";
            priority = "";
            bloodType = "";
            tissueType = "";
            organ = "";
            doctorInCharge = "";
            organExpiryDate = "";
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

            if (bloodType == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        BloodType.class.getSimpleName()));
            }

            if (!BloodType.isValidBloodType(bloodType)) {
                throw new IllegalValueException(BloodType.MESSAGE_CONSTRAINTS);
            }
            final BloodType modelBloodType = new BloodType(bloodType);

            if (tissueType == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        TissueType.class.getSimpleName()));
            }

            if (!TissueType.isValidTissueType(tissueType)) {
                throw new IllegalValueException(TissueType.MESSAGE_CONSTRAINTS);
            }
            final TissueType modelTissueType = new TissueType(tissueType);

            if (organ == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Organ.class.getSimpleName()));
            }

            if (!Organ.isValidOrgan(organ)) {
                throw new IllegalValueException(Organ.MESSAGE_CONSTRAINTS);
            }
            final Organ modelOrgan = new Organ(organ);

            if (organExpiryDate == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        OrganExpiryDate.class.getSimpleName()));
            }

            if (!OrganExpiryDate.isValidExpiryDate(organExpiryDate)) {
                throw new IllegalValueException(OrganExpiryDate.MESSAGE_CONSTRAINTS);
            }
            final OrganExpiryDate modelOrganExpiryDate = new OrganExpiryDate(organExpiryDate);

            return new Donor(modelType, modelNric, modelName, modelPhone, modelAge, modelBloodType, modelTissueType,
                    modelOrgan, modelOrganExpiryDate);
        } else if (modelType.isPatient()) {
            if (age == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Age.class.getSimpleName()));
            }

            if (!Age.isValidAge(age)) {
                throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
            }
            final Age modelAge = new Age(age);

            if (priority == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Priority.class.getSimpleName()));
            }

            if (!Priority.isValidPriority(priority)) {
                throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
            }
            final Priority modelPriority = new Priority(priority);

            if (bloodType == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        BloodType.class.getSimpleName()));
            }

            if (!BloodType.isValidBloodType(bloodType)) {
                throw new IllegalValueException(BloodType.MESSAGE_CONSTRAINTS);
            }
            final BloodType modelBloodType = new BloodType(bloodType);

            if (tissueType == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        TissueType.class.getSimpleName()));
            }

            if (!TissueType.isValidTissueType(tissueType)) {
                throw new IllegalValueException(TissueType.MESSAGE_CONSTRAINTS);
            }
            final TissueType modelTissueType = new TissueType(tissueType);

            if (organ == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Organ.class.getSimpleName()));
            }

            if (!Organ.isValidOrgan(organ)) {
                throw new IllegalValueException(Organ.MESSAGE_CONSTRAINTS);
            }
            final Organ modelOrgan = new Organ(organ);

            if (doctorInCharge == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        DoctorInCharge.class.getSimpleName()));
            }

            if (!DoctorInCharge.isValidDoctorInCharge(doctorInCharge)) {
                throw new IllegalValueException(DoctorInCharge.MESSAGE_CONSTRAINTS);
            }
            final DoctorInCharge modelDoctorInCharge = new DoctorInCharge(doctorInCharge);

            return new Patient(modelType, modelNric, modelName, modelPhone, modelAge, modelPriority,
                    modelBloodType, modelTissueType, modelOrgan, modelDoctorInCharge);
        } else {
            return new Person(modelType, modelNric, modelName, modelPhone);
        }
    }

}
