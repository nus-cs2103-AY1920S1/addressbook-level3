package seedu.address.model.person;

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
    private final Emails emails; // personal, NUS emails etc

    /**
     * Every field must be present and not null.
     */
    private Interviewee(Faculty faculty, Integer yearOfStudy,
                       List<Department> departmentChoices, List<Slot> availableTimeslots, Emails emails,
                       Name name, Phone phone, Address address, Set<Tag> tags) {
        super(name, phone, address, tags);
        this.faculty = faculty;
        this.yearOfStudy = yearOfStudy;
        this.departmentChoices = departmentChoices;
        this.availableTimeslots = availableTimeslots;
        this.emails = emails;
    }

    /**
     * Builder class for Interviewee - allows certain fields to optionally be added.
     */
    public static class IntervieweeBuilder {
        // Required parameters for Person
        private final Name name;
        private final Phone phone;
        private final Address address;
        private final Set<Tag> tags;

        // Optional parameters - initialised to default values
        private Emails emails = DefaultValues.DEFAULT_EMAILS;
        private Faculty faculty = DefaultValues.DEFAULT_FACULTY;
        private Integer yearOfStudy = DefaultValues.DEFAULT_YEAR_OF_STUDY;
        private List<Department> departmentChoices = DefaultValues.DEFAULT_DEPARTMENTS;
        private List<Slot> availableTimeslots = DefaultValues.DEFAULT_TIMESLOTS;

        public IntervieweeBuilder(Name name, Phone phone, Address address, Set<Tag> tags) {
            this.name = name;
            this.phone = phone;
            this.address = address;
            this.tags = tags;
        }

        public IntervieweeBuilder(Person p) {
            this(p.getName(), p.getPhone(), p.getAddress(), p.getTags());
        }

        /**
         * Sets the optional {@code Emails} to create the Interviewee object.
         */
        public IntervieweeBuilder emails(Emails val) {
            emails = val;
            return this;
        }

        /**
         * Sets the optional {@code Faculty} to create the Interviewee object.
         */
        public IntervieweeBuilder faculty(Faculty val) {
            faculty = val;
            return this;
        }

        /**
         * Sets the optional {@code Integer} year of study to create the Interviewee object.
         */
        public IntervieweeBuilder yearOfStudy(Integer val) {
            yearOfStudy = val;
            return this;
        }

        /**
         * Sets the optional {@code List<Department>} to create the Interviewee object.
         */
        public IntervieweeBuilder departmentChoices(List<Department> val) {
            departmentChoices = val;
            return this;
        }

        /**
         * Sets the optional {@code List<Slot>} to create the Interviewee object.
         */
        public IntervieweeBuilder availableTimeslots(List<Slot> val) {
            availableTimeslots = val;
            return this;
        }

        /**
         * Builds the Interviewee object.
         */
        public Interviewee build() {
            return new Interviewee(faculty, yearOfStudy, departmentChoices,
                    availableTimeslots, emails, name, phone, address, tags);
        }
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
                getName(), getPhone(), getAddress(), getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Role: ")
                .append(getClass().getSimpleName().toLowerCase())
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
