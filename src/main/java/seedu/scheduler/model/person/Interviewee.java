package seedu.scheduler.model.person;

import static seedu.scheduler.model.person.Slot.EMPTY_SLOT;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.scheduler.model.tag.Tag;

/**
 * Represents an Interviewee in the scheduler.
 */
public class Interviewee extends Person {

    private final Faculty faculty;
    private final Integer yearOfStudy;
    private final List<Department> departmentChoices; // choice of departments
    private final List<Slot> availableTimeslots;
    private final Emails emails; // personal, NUS emails etc
    private Slot allocatedSlot;
    private boolean emailSent;

    /**
     * Every field must be present and not null.
     */
    private Interviewee(Faculty faculty, Integer yearOfStudy,
                       List<Department> departmentChoices, List<Slot> availableTimeslots, Emails emails,
                       Name name, Phone phone, Set<Tag> tags) {
        super(name, phone, tags);
        this.faculty = faculty;
        this.yearOfStudy = yearOfStudy;
        this.departmentChoices = departmentChoices;
        this.availableTimeslots = availableTimeslots;
        this.allocatedSlot = null;
        this.emails = emails;
        this.emailSent = false;
    }

    /**
     * Builder class for Interviewee - allows certain fields to optionally be added.
     */
    public static class IntervieweeBuilder {
        // Required parameters for Person
        private final Name name;
        private final Phone phone;
        private final Set<Tag> tags;

        // Optional parameters - initialised to default values
        private Emails emails = DefaultValues.DEFAULT_EMAILS;
        private Faculty faculty = DefaultValues.DEFAULT_FACULTY;
        private Integer yearOfStudy = DefaultValues.DEFAULT_YEAR_OF_STUDY;
        private List<Department> departmentChoices = DefaultValues.DEFAULT_DEPARTMENTS;
        private List<Slot> availableTimeslots = DefaultValues.DEFAULT_TIMESLOTS;

        public IntervieweeBuilder(Name name, Phone phone, Set<Tag> tags) {
            this.name = name;
            this.phone = phone;
            this.tags = tags;
        }

        public IntervieweeBuilder(Person p) {
            this(p.getName(), p.getPhone(), p.getTags());
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
                    availableTimeslots, emails, name, phone, tags);
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
        Collections.sort(availableTimeslots);
        return availableTimeslots;
    }

    /**
     * Returns the allocated slot of the interviewee if any, otherwise returns an empty Optional.
     */
    public Slot getAllocatedSlot() {
        if (allocatedSlot != null) {
            return allocatedSlot;
        } else {
            return EMPTY_SLOT;
        }
    }

    public void setAllocatedSlot(Slot slot) {
        this.allocatedSlot = slot;
    }

    public void clearAllocatedSlot() {
        this.allocatedSlot = null;
    }

    public Emails getEmails() {
        return emails;
    }

    public boolean getEmailSent() {
        return this.emailSent;
    }

    public void setEmailSent(boolean flag) {
        this.emailSent = flag;
    }

    /**
     * Returns true if both interviewees of the same name have the same name and phone number.
     * This defines a weaker notion of equality between two interviewees.
     */
    public boolean isSamePerson(Person interviewee) {
        if (interviewee == this) {
            return true;
        }

        if (!(interviewee instanceof Interviewee)) {
            return false;
        }

        return interviewee != null
                && interviewee.getName().equals(getName());
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
                getName(), getPhone(), getTags());
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
