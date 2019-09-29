package seedu.address.model.Entity;

import java.util.HashMap;
import java.util.Objects;

public class Participant extends Entity {
    private Email email;
    private Phone phone;
    private Team team;

    /**
     * Constructor without team.
     *
     * @param name
     * @param email
     * @param phone
     * @param id
     */
    public Participant(Name name, Email email, Phone phone, Id id) {
        super(id, name);
        this.email = email;
        this.phone = phone;
        // TODO: Make this case impossible? because participants cannot exist w/o team
        this.team = null;
    }

    public Participant(Name name, Email email, Phone phone, Id id, Team team) {
        super(id, name);
        this.email = email;
        this.phone = phone;
        this.team = team;
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

    /**
     * Gets the team.
     *
     * @return team
     */
    public Team getTeam() {
        return this.team;
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
        result.put("team", getTeam().toString());
        return result;
    }

    @Override
    public HashMap<String, String> viewDetailed() {
        HashMap<String, String> result = new HashMap<>();
        result.put("name", getName().toString());
        result.put("phone", getPhone().toString());
        result.put("email", getEmail().toString());
        result.put("team", getTeam().toString());
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone, team);
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
                && otherParticipant.getEmail() == this.getEmail()
                && otherParticipant.getTeam() == this.getTeam();
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
                .append(getEmail())
                .append(" Team: ")
                .append(getTeam());

        return builder.toString();
    }
}
