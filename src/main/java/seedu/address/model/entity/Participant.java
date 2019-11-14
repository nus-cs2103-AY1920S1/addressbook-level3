package seedu.address.model.entity;

import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a {@code Participant} in the address book.
 */
public class Participant extends Entity {
    private Email email;
    private Phone phone;

    /**
     * Constructs an {@code Pariticipant}.
     *
     * @param name Name of Entity.
     * @param id Identification number of Entity.
     * @param email Email address of Participant.
     * @param phone Phone number of Participant.
     */
    public Participant(Name name, Id id, Email email, Phone phone) {
        super(id, name);
        this.email = email;
        this.phone = phone;
    }

    public Email getEmail() {
        return this.email;
    }

    public Phone getPhone() {
        return this.phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }


    public void setEmail(Email email) {
        this.email = email;
    }

    /*
    @Override
    public HashMap<String, String> viewMinimal() {
        HashMap<String, String> result = new HashMap<>();
        result.put("name", getName().toString());
        result.put("phone", getPhone().toString());
        return result;
    }
     */

    /**
     * Returns the full details of the Participant, in the HashMap format.
     *
     * @return HashMap Maps String to a String, each key is mapped to a data field.
     */
    @Override
    public HashMap<String, String> viewDetailed() {
        HashMap<String, String> result = new HashMap<>();
        result.put("name", getName().toString());
        result.put("id", getId().toString());
        result.put("phone", getPhone().toString());
        result.put("email", getEmail().toString());
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone);
    }

    /**
     * Returns true if both Participant objects have the same data fields.
     * This defines a stronger notion of equality between two Participant object.
     *
     * @param other Other Participant object.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Participant)) {
            return false;
        }

        Participant otherParticipant = ((Participant) other);
        return otherParticipant.getName().equals(this.getName())
                && otherParticipant.getId().equals(this.getId())
                && otherParticipant.getPhone().equals(this.getPhone())
                && otherParticipant.getEmail().equals(this.getEmail());
    }

    /**
     * Returns string representation of object.
     *
     * @return Participant in string format.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
                .append(getName())
                .append(", ID: ")
                .append(getId())
                .append(", Phone: ")
                .append(getPhone())
                .append(", Email: ")
                .append(getEmail());

        return builder.toString();
    }

    /**
     * This implements a looser definition of equality for Participants.
     *
     * @param otherParticipant
     * @return boolean
     */
    public boolean isSameParticipant(Participant otherParticipant) {
        if (otherParticipant == this) {
            return true;
        }

        return this.name.equals(otherParticipant.getName())
                && (this.phone.equals(otherParticipant.getPhone())
                    || this.email.equals(otherParticipant.getEmail()));
    }

    /**
     * Returns a deep copy of the Participant object
     * @return Deep copy of the Participant object
     */
    public Participant copy() {
        Participant copy = new Participant(this.name.copy(),
                                           this.id.copy(),
                                           this.email.copy(),
                                           this.phone.copy());
        return copy;
    }

    @Override
    public PrefixType getPrefix() {
        return PrefixType.P;
    }

}
