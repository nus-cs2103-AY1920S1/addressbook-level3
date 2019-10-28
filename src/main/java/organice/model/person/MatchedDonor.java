package organice.model.person;

import java.util.Objects;

/**
 * Represents a {@code Donor} that is a match with a specified {@code Patient}.
 * It is a temporary instance and will be removed once a {@code MatchCommand} is executed.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MatchedDonor extends Donor {
    private String successRate;

    /**
     * Every field must be present and not null.
     */
    public MatchedDonor(Type type, Nric nric, Name name, Phone phone, Age age,
            BloodType bloodType, TissueType tissueType, Organ organ, OrganExpiryDate organExpiryDate, Status status) {
        super(type, nric, name, phone, age, bloodType, tissueType, organ, organExpiryDate, status);
        successRate = "";
    }

    /**
     * Alternative constructor which takes in a {@code Donor} and constructs a {@code MatchedDonor}
     */
    public MatchedDonor(Donor toAdd) {
        this(toAdd.getType(), toAdd.getNric(), toAdd.getName(), toAdd.getPhone(), toAdd.getAge(), toAdd.getBloodType(),
                toAdd.getTissueType(), toAdd.getOrgan(), toAdd.getOrganExpiryDate(), toAdd.getStatus());
        successRate = toAdd.getSuccessRate();
    }

    /**
     * Returns a {@code String} detailing the success rate to be displayed in the {@code DonorCard}.
     */
    public String getSuccessRate() {
        return successRate;
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

        if (!(other instanceof MatchedDonor)) {
            return false;
        }

        MatchedDonor otherPerson = (MatchedDonor) other;
        return otherPerson.getNric().equals(getNric())
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getType().equals(getType())
                && otherPerson.getAge().equals(getAge())
                && otherPerson.getBloodType().equals(getBloodType())
                && otherPerson.getTissueType().equals(getTissueType())
                && otherPerson.getOrgan().equals(getOrgan())
                && otherPerson.getOrganExpiryDate().equals(getOrganExpiryDate())
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
