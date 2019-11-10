package seedu.scheduler.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.scheduler.model.tag.Tag;

/**
 * Represents an Interviewer in the scheduler.
 */
public class Interviewer extends Person {

    // Each availability is given as a string in this format DD/MM/YYYY HH:MM-HH:MM
    private final List<Slot> availabilities;
    private final List<IntervieweeSlot> allocatedSlots;
    private final Department department;
    private final Email email;

    /**
     * Every field must be present and not null.
     */
    private Interviewer(Name name, Phone phone, Set<Tag> tags,
                        Email email, Department department, List<Slot> availabilities) {
        super(name, phone, tags);
        this.department = department;
        this.email = email;
        this.availabilities = availabilities;
        this.allocatedSlots = new ArrayList<>();
    }

    /**
     * Static builder class for Interviewer.
     */
    public static class InterviewerBuilder {
        // Required parameters for Person
        private final Name name;
        private final Phone phone;
        private final Set<Tag> tags;

        // Optional parameters - initialised to default values
        private Department department = DefaultValues.DEFAULT_DEPARTMENT;
        private Email email = DefaultValues.DEFAULT_NUS_WORK_EMAIL;
        private List<Slot> availabilities = DefaultValues.DEFAULT_TIMESLOTS;

        public InterviewerBuilder(Name name, Phone phone, Set<Tag> tags) {
            this.name = name;
            this.phone = phone;
            this.tags = tags;
        }

        public InterviewerBuilder(Person p) {
            this(p.getName(), p.getPhone(), p.getTags());
        }

        /**
         * Sets the optional {@code Department} to create the Interviewer object.
         */
        public InterviewerBuilder department(Department val) {
            department = val;
            return this;
        }

        /**
         * Sets the optional {@code Email} to create the Interviewer object.
         */
        public InterviewerBuilder email(Email val) {
            email = val;
            return this;
        }

        /**
         * Sets the optional {@code List<Slot>} to create the Interviewer object.
         */
        public InterviewerBuilder availabilities(List<Slot> val) {
            availabilities = val;
            return this;
        }

        /**
         * Build and return the Interviewer object.
         */
        public Interviewer build() {
            return new Interviewer(name, phone, tags, email, department, availabilities);
        }
    }

    // Getters, setters and misc methods
    public Department getDepartment() {
        return department;
    }

    public List<Slot> getAvailabilities() {
        return availabilities;
    }

    public Email getEmail() {
        return email;
    }

    public void setAvailabilities(List<Slot> slots) {
        this.availabilities.clear();
        this.availabilities.addAll(slots);
    }

    public List<IntervieweeSlot> getAllocatedSlots() {
        Collections.sort(allocatedSlots);
        return allocatedSlots;
    }

    public void addAllocatedSlot(IntervieweeSlot slot) {
        this.allocatedSlots.add(slot);
    }

    public void clearAllocatedSlots() {
        this.allocatedSlots.clear();
    }

    /**
     * Checks if interviewer is available for the given slot.
     * @param slot
     * @return True if interviewer is available, false otherwise
     */
    public boolean isAvailable(Slot slot) {
        for (Slot availableSlot: availabilities) {
            if (availableSlot.equals(slot)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if both interviewers of the same name have the same name and phone number.
     * This defines a weaker notion of equality between two interviewers.
     */
    public boolean isSamePerson(Person interviewer) {
        if (interviewer == this) {
            return true;
        }

        if (!(interviewer instanceof Interviewer)) {
            return false;
        }

        return interviewer != null
                && interviewer.getName().equals(getName());
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

        if (!(other instanceof Interviewer)) {
            return false;
        }

        Interviewer otherInterviewer = (Interviewer) other;
        return super.equals(other)
                && otherInterviewer.getDepartment().equals(getDepartment())
                && otherInterviewer.getEmail().equals(getEmail())
                && otherInterviewer.getAvailabilities().equals(getAvailabilities());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(department, email, availabilities,
                getName(), getPhone(), getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Role: ")
                .append(getClass().getSimpleName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Department: ")
                .append(getDepartment())
                .append(" Email: ")
                .append(getEmail())
                .append(" Availabilities: ")
                .append(getAvailabilities())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
