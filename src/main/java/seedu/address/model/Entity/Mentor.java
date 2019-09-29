package seedu.address.model.Entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Mentor extends Entity {
    private Phone phone;
    private Email email;
    private List<Team> teams;
    private Name organization;
    private SubjectName subject;

    /**
     * Constructor.
     *
     * @param name
     * @param id
     * @param phone
     * @param email
     * @param teams
     * @param organization
     * @param subject
     */
    public Mentor(
            Name name,
            Id id,
            Phone phone,
            Email email,
            List<Team> teams,
            Name organization,
            SubjectName subject
    ) {
        super(id, name);
        this.phone = phone;
        this.email = email;
        this.teams = teams;
        this.organization = organization;
        this.subject = subject;
    }

    // Getters

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Name getOrganization() {
        return organization;
    }

    public SubjectName getSubject() {
        return subject;
    }

    // Setters

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void setOrganization(Name organization) {
        this.organization = organization;
    }

    public void setSubject(SubjectName subject) {
        this.subject = subject;
    }

    @Override
    public HashMap<String, String> viewMinimal() {
        HashMap<String, String> result = new HashMap<>();
        result.put("name", getName().toString());
        result.put("id", getId().toString());
        result.put("phone", getPhone().toString());
        result.put("email", getEmail().toString());
        return result;
    }

    @Override
    public HashMap<String, String> viewDetailed() {
        HashMap<String, String> result = new HashMap<>();
        Stream<String> teamStream = teams.stream().map(team -> team.toString());
        String teamsString = Arrays.toString(teamStream.toArray());

        result.put("name", getName().toString());
        result.put("id", getId().toString());
        result.put("phone", getPhone().toString());
        result.put("email", getEmail().toString());
        result.put("organization", getOrganization().toString());
        result.put("subject", getSubject().toString());
        result.put("teams", teamsString);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Stream<String> teamStream = teams.stream().map(team -> team.toString());
        String teamsString = Arrays.toString(teamStream.toArray());

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
                .append(getSubject())
                .append(" Teams: ")
                .append(teamsString);

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Mentor)) {
            return false;
        }

        Mentor otherMentor = ((Mentor) other);
        return otherMentor.getName() == this.getName()
                && otherMentor.getId() == this.getId()
                && otherMentor.getPhone() == this.getPhone()
                && otherMentor.getEmail() == this.getEmail()
                && otherMentor.getOrganization() == this.getOrganization()
                && otherMentor.getSubject() == this.getSubject()
                && otherMentor.getTeams() == this.getTeams();
    }

}
