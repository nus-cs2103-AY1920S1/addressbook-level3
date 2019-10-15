package organice.testutil;

import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.DoctorInCharge;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.Patient;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.TissueType;
import organice.model.person.Type;

/**
 * A utility class to build Patient objects.
 */
public class PatientBuilder extends PersonBuilder {
    public static final String DEFAULT_AGE = "20";
    public static final String DEFAULT_PRIORITY = "high";
    public static final String DEFAULT_BLOOD_TYPE = "A";
    public static final String DEFAULT_TISSUE_TYPE = "1,2,3,4,5,6";
    public static final String DEFAULT_ORGAN = "kidney";
    public static final String DEFAULT_DOCTOR_IN_CHARGE = "S1111111A";


    private Age age;
    private Priority priority;
    private BloodType bloodType;
    private TissueType tissueType;
    private Organ organ;
    private DoctorInCharge doctorInCharge;

    public PatientBuilder() {
        super();
        age = new Age(DEFAULT_AGE);
        type = new Type("patient");
        priority = new Priority(DEFAULT_PRIORITY);
        bloodType = new BloodType(DEFAULT_BLOOD_TYPE);
        tissueType = new TissueType(DEFAULT_TISSUE_TYPE);
        organ = new Organ(DEFAULT_ORGAN);
        doctorInCharge = new DoctorInCharge(DEFAULT_DOCTOR_IN_CHARGE);
    }

    /**
     * Initialises the PatientBuilder with the data of {@code patientToCopy}
     */
    public PatientBuilder(Patient patientToCopy) {
        super(patientToCopy);
        age = patientToCopy.getAge();
        priority = patientToCopy.getPriority();
        bloodType = patientToCopy.getBloodType();
        tissueType = patientToCopy.getTissueType();
        organ = patientToCopy.getOrgan();
        doctorInCharge = patientToCopy.getDoctorInCharge();
    }

    /**
     * Sets the {@code Age} of the {@code Patient} we are building
     */
    public PatientBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Patient} that we are building.
     */
    @Override
    public PatientBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }
    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    @Override
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    @Override
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    /**
     * Sets the {@code BloodType} of the {@code Patient} that we are building.
     */
    public PatientBuilder withBloodType(String bloodType) {
        this.bloodType = new BloodType(bloodType);
        return this;
    }

    /**
     * Sets the {@code TissueType} of the {@code Patient} that we are building.
     */
    public PatientBuilder withTissueType(String tissueType) {
        this.tissueType = new TissueType(tissueType);
        return this;
    }

    /**
     * Sets the {@code Organ} of the {@code Patient} that we are building.
     */
    public PatientBuilder withOrgan(String organ) {
        this.organ = new Organ(organ);
        return this;
    }

    /**
     * Sets the {@code DoctorInCharge} of the {@code Patient} that we are building.
     */
    public PatientBuilder withDoctorInCharge(String doctorInCharge) {
        this.doctorInCharge = new DoctorInCharge(doctorInCharge);
        return this;
    }

    public Patient build() {
        return new Patient(type, nric, name, phone, age, priority, bloodType, tissueType, organ, doctorInCharge);
    }
}
