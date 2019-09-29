package seedu.address.model.entity;

import java.util.HashMap;
import java.util.Objects;

public class Participant extends Entity {
    private Email email;
    private Phone phone;

    /**
     * Constructor without team.
     *
     * @param name
     * @param email
     * @param phone
     * @param id
     */
    public Participant(Name name, Id id, Email email, Phone phone) {
        super(id, name);
        this.email = email;
        this.phone = phone;
    }

    // Getters - Note the return types will be changed from phone and email to the respective types - Just for demo

    /**
     * Gets the email.
     *
     * @return Email
     */
    public Email getEmail() {
        return this.email;
    }

    /**
     * Gets the phone number.
     *
     * @return Phone
     */
    public Phone getPhone() {
        return this.phone;
    }

    // Setters - Argument types will be changed

    /**
     * Sets the phone.
     *
     * @param phone
     */
    public void setPhone(Phone phone) {
       this.phone = phone;
    }

    /**
     * Sets the email.
     *
     * @param email
     */
    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    public HashMap<String, String> viewMinimal() {
        HashMap<String, String> result = new HashMap<>();
        result.put("name", getName().toString());
        result.put("phone", getPhone().toString());
        return result;
    }

    @Override
    public HashMap<String, String> viewDetailed() {
        HashMap<String, String> result = new HashMap<>();
        result.put("name", getName().toString());
        result.put("phone", getPhone().toString());
        result.put("email", getEmail().toString());
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Participant)) {
            return false;
        }

        Participant otherParticipant = ((Participant) other);
        return otherParticipant.getName() == this.getName()
                && otherParticipant.getId() == this.getId()
                && otherParticipant.getPhone() == this.getPhone()
                && otherParticipant.getEmail() == this.getEmail();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
                .append(getName())
                .append(" ID: ")
                .append(getId())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail());

        return builder.toString();
    }
}
