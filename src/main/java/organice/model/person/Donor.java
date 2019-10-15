package organice.model.person;

import static organice.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Donor in ORGANice.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Donor extends Person {
    //fields
    private final Age age;
    private final BloodType bloodType;
    private final TissueType tissueType;
    private final Organ organ;
    private final OrganExpiryDate organExpiryDate;

    /**
     * Every field must be present and not null.
     */
    public Donor(Type type, Nric nric, Name name, Phone phone, Age age,
                 BloodType bloodType, TissueType tissueType, Organ organ, OrganExpiryDate organExpiryDate) {
        super(type, nric, name, phone);
        requireAllNonNull(age, bloodType, tissueType, organ, organExpiryDate);
        this.age = age;
        this.bloodType = bloodType;
        this.tissueType = tissueType;
        this.organ = organ;
        this.organExpiryDate = organExpiryDate;
    }

    public Age getAge() {
        return age;
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

    public OrganExpiryDate getOrganExpiryDate() {
        return organExpiryDate;
    }

    /**
     * Returns true if both donors have the same identity and data fields.
     * This defines a stronger notion of equality between two donors.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Donor)) {
            return false;
        }

        Donor otherPerson = (Donor) other;
        return otherPerson.getNric().equals(getNric())
            && otherPerson.getName().equals(getName())
            && otherPerson.getPhone().equals(getPhone())
            && otherPerson.getType().equals(getType())
            && otherPerson.getAge().equals(getAge())
            && otherPerson.getBloodType().equals(getBloodType())
            && otherPerson.getTissueType().equals(getTissueType())
            && otherPerson.getOrgan().equals(getOrgan())
            && otherPerson.getOrganExpiryDate().equals(getOrganExpiryDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(type, nric, name, phone, age, bloodType, tissueType, organ, organExpiryDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString())
            .append(" Age: ")
            .append(getAge())
            .append(" Blood Type: ")
            .append(getBloodType())
            .append(" Tissue Type: ")
            .append(getTissueType())
            .append(" Organ: ")
            .append(getOrgan())
            .append(" Organ Expiry Date: ")
            .append(getOrganExpiryDate());

        return builder.toString();
    }

}
