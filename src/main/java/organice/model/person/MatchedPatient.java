package organice.model.person;

import java.util.Objects;

/**
 * Represents a Person in ORGANice.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MatchedPatient extends Patient {

    private int numberOfMatches = 0;
    /**
     * Every field must be present and not null.
     */
    public MatchedPatient(Type type, Nric nric, Name name, Phone phone, Age age, Priority priority,
            BloodType bloodType, TissueType tissueType, Organ organ, DoctorInCharge doctorInCharge, Status status) {
        super(type, nric, name, phone, age, priority, bloodType, tissueType, organ, doctorInCharge, status);
    }

    /**
     * Alternative constructor which takes in a {@code Patient} and constructs a {@code MatchedPatient}
     */
    public MatchedPatient(Patient toAdd) {
        this(toAdd.getType(), toAdd.getNric(), toAdd.getName(), toAdd.getPhone(), toAdd.getAge(), toAdd.getPriority(),
                toAdd.getBloodType(), toAdd.getTissueType(), toAdd.getOrgan(), toAdd.getDoctorInCharge(),
                toAdd.getStatus());
    }

    /**
     * Sets the number of matches the represented {@code Patient} has with all {@code Donors}.
     */
    public void setNumberOfMatches(int numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public Name getName() {
        return this.name;
    }
    /**
     * Retrieves the number of matches the represented {@code Patient} has with all {@code Donors}.
     */
    public int getNumberOfMatches() {
        return numberOfMatches;
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

        if (!(other instanceof MatchedPatient)) {
            return false;
        }

        MatchedPatient otherPerson = (MatchedPatient) other;
        return otherPerson.getType().equals(getType())
                && otherPerson.getNric().equals(getNric())
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getAge().equals(getAge())
                && otherPerson.getPriority().equals(getPriority())
                && otherPerson.getBloodType().equals(getBloodType())
                && otherPerson.getTissueType().equals(getTissueType())
                && otherPerson.getOrgan().equals(getOrgan())
                && otherPerson.getDoctorInCharge().equals(getDoctorInCharge())
                && otherPerson.getStatus().equals(getStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(type, nric, name, phone);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
