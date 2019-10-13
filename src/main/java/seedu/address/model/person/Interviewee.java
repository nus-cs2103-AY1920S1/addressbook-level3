package seedu.address.model.person;

import static seedu.address.model.person.EmailType.NUS;
import static seedu.address.model.person.EmailType.PERSONAL;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Interviewee in the scheduler.
 */
public class Interviewee extends Person {

    private final Faculty faculty;
    private final Integer yearOfStudy;
    private final List<Department> departmentChoices; // choice of departments
    private final List<Slot> availableTimeslots; // allocated interview time slots
    private final Emails emails = new Emails(); // personal, NUS emails etc

    /**
     * Every field must be present and not null.
     */
    public Interviewee(Email personalEmail, Email nusEmail, Faculty faculty, Integer yearOfStudy,
                       List<Department> departmentChoices, List<Slot> availableTimeslots,
                       Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        // TODO: Remove dependency on emails here and in test cases
        super(name, phone, email, address, tags);
        this.faculty = faculty;
        this.yearOfStudy = yearOfStudy;
        this.departmentChoices = departmentChoices;
        this.availableTimeslots = availableTimeslots;
        emails.addEmail(PERSONAL, personalEmail);
        emails.addEmail(NUS, nusEmail);
    }

    // Getters and misc methods
    public Faculty getFaculty() {
        return faculty;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public List<Department> getDepartmentChoices() {
        return departmentChoices;
    }

    public List<Slot> getAvailableTimeslots() {
        return availableTimeslots;
    }

    public Emails getEmails() {
        return emails;
    }

    /**
     * Returns true if both interviewees have the same identity and data fields.
     * This defines a stronger notion of equality between two interviewees.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Interviewee)) {
            return false;
        }

        Interviewee otherInterviewee = (Interviewee) other;
        return super.equals(other)
                && otherInterviewee.getEmails().equals(getEmails())
                && otherInterviewee.getFaculty().equals(getFaculty())
                && otherInterviewee.getYearOfStudy().equals(getYearOfStudy())
                && otherInterviewee.getDepartmentChoices().equals(getDepartmentChoices())
                && otherInterviewee.getAvailableTimeslots().equals(getAvailableTimeslots());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(emails, faculty, yearOfStudy, departmentChoices, availableTimeslots,
                getName(), getPhone(), getAddress(), getEmail(), getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Emails: ")
                .append(getEmails())
                .append(" Address: ")
                .append(getAddress())
                .append(" Faculty: ")
                .append(getFaculty())
                .append(" Year of study: ")
                .append(getYearOfStudy())
                .append(" Choice of departments: ")
                .append(getDepartmentChoices())
                .append(" Time slots: ")
                .append(getAvailableTimeslots())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
