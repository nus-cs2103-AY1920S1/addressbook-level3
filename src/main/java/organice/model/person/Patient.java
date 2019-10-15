package organice.model.person;

import static organice.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Patient in ORGANice.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    //fields
    private final Age age;
    private final Priority priority;
    private final BloodType bloodType;
    private final TissueType tissueType;
    private final Organ organ;
    private final DoctorInCharge doctorInCharge;

    /**
     * Every field must be present and not null.
     */
    public Patient(Type type, Nric nric, Name name, Phone phone, Age age, Priority priority,
                   BloodType bloodType, TissueType tissueType, Organ organ, DoctorInCharge doctorInCharge) {
        super(type, nric, name, phone);
        requireAllNonNull(age, priority, bloodType, tissueType, organ, doctorInCharge);
        this.age = age;
        this.priority = priority;
        this.bloodType = bloodType;
        this.tissueType = tissueType;
        this.organ = organ;
        this.doctorInCharge = doctorInCharge;
    }

    public Age getAge() {
        return age;
    }

    public Priority getPriority() {
        return priority;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public TissueType getTissueType() {
        return tissueType;
    }

    public Organ getOrgan() {
        return organ;
    }

    public DoctorInCharge getDoctorInCharge() {
        return doctorInCharge;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString())
                .append(" Age: ")
                .append(getAge())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Blood Type: ")
                .append(getBloodType())
                .append(" Tissue Type: ")
                .append(getTissueType())
                .append(" Organ: ")
                .append(getOrgan())
                .append(" Doctor In Charge: ")
                .append(getDoctorInCharge());

        return builder.toString();
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPerson = (Patient) other;
        return otherPerson.getType().equals(getType())
                && otherPerson.getNric().equals(getNric())
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getAge().equals(getAge())
                && otherPerson.getPriority().equals(getPriority())
                && otherPerson.getBloodType().equals(getBloodType())
                && otherPerson.getTissueType().equals(getTissueType())
                && otherPerson.getOrgan().equals(getOrgan())
                && otherPerson.getDoctorInCharge().equals(getDoctorInCharge());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(type, nric, name, phone, age, bloodType, tissueType, organ, doctorInCharge);
    }

}
