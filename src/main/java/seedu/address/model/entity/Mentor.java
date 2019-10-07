package seedu.address.model.entity;

import java.util.HashMap;

/**
 * Represents a {@code Mentor} in the address book.
 */
public class Mentor extends Entity {

    // Data fields
    private Phone phone;
    private Email email;
    private Name organization;
    private SubjectName subject;

    /**
     * Constructs an {@code Mentor}.
     *
     * @param name Name of {@code Mentor}.
     * @param id Idenfication number of (@code Mentor}.
     * @param phone Phone number.
     * @param email Email address.
     * @param organization Namme of organization.
     */
    public Mentor(
            Name name,
            Id id,
            Phone phone,
            Email email,
            Name organization,
            SubjectName subject
    ) {
        super(id, name);
        this.phone = phone;
        this.email = email;
        this.organization = organization;
        this.subject = subject;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Name getOrganization() {
        return organization;
    }

    public SubjectName getSubject() {
        return subject;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setOrganization(Name organization) {
        this.organization = organization;
    }

    public void setSubject(SubjectName subject) {
        this.subject = subject;
    }

    /*
    @Override
    public HashMap<String, String> viewMinimal() {
        HashMap<String, String> result = new HashMap<>();
        result.put("name", getName().toString());
        result.put("id", getId().toString());
        result.put("phone", getPhone().toString());
        result.put("email", getEmail().toString());
        return result;
    }
    */


    /**
     * Returns the full details of the Mentor, in the HashMap format.
     *
     * @return HashMap Maps String to a String, each key is mapped to a specific data field.
     */
    @Override
    public HashMap<String, String> viewDetailed() {
        HashMap<String, String> result = new HashMap<>();

        result.put("name", getName().toString());
        result.put("id", getId().toString());
        result.put("phone", getPhone().toString());
        result.put("email", getEmail().toString());
        result.put("organization", getOrganization().toString());
        result.put("subject", getSubject().toString());
        return result;
    }

    /**
     * Returns string representation of object.
     *
     * @return Mentor in string format.
     */
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
                .append(getEmail())
                .append(" Organization: ")
                .append(getOrganization())
                .append(" Subject: ")
                .append(getSubject());

        return builder.toString();
    }

    /**
     * Returns true if both Mentor objects have the same data fields.
     * This defines a stronger notion of equality between two Mentor object.
     *
     * @param other Other Mentor object.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Mentor)) {
            return false;
        }

        Mentor otherMentor = ((Mentor) other);
        return otherMentor.getName().equals(this.getName())
                && otherMentor.getId().equals(this.getId())
                && otherMentor.getPhone().equals(this.getPhone())
                && otherMentor.getEmail().equals(this.getEmail())
                && otherMentor.getOrganization().equals(this.getOrganization())
                && otherMentor.getSubject().equals(this.getSubject());
    }

}
