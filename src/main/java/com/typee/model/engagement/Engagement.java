package com.typee.model.engagement;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import com.typee.model.engagement.exceptions.InvalidTimeException;

/**
 * Represents a generalization of meetings, interviews and appointments.
 */
public abstract class Engagement {

    public static final String MESSAGE_INVALID_TIME = "The end time has to occur after the start time.";

    protected TimeSlot timeSlot;
    protected AttendeeList attendees;
    protected Location location;
    protected String description;
    protected Priority priority;

    /**
     * Constructs an engagement.
     *
     * @param timeSlot time slot of the engagement.
     * @param attendees list of people attending.
     * @param location location of the engagement.
     * @param description description of the engagement.
     * @param priority priority level of the engagement.
     */
    protected Engagement(TimeSlot timeSlot,
                         AttendeeList attendees, Location location, String description, Priority priority) {
        this.timeSlot = timeSlot;
        this.attendees = attendees;
        this.location = location;
        this.description = description;
        this.priority = priority;
    }

    /**
     * Returns a {@code Meeting}, {@code Interview}, or {@code Appointment} with the given fields.
     * @param type type of engagement.
     * @param timeSlot time slot.
     * @param attendees list of people attending.
     * @param location location of engagement.
     * @param description description of the engagement.
     * @param priority priority level of the engagement.
     *
     * @return an {@code Engagement} with the corresponding fields.
     * @throws InvalidTimeException if {@code LocalDateTime startTime} occurs
     * after or during {@code LocalDateTime endTime}.
     */
    public static Engagement of(EngagementType type,
                                TimeSlot timeSlot,
                                AttendeeList attendees, Location location, String description,
                                Priority priority) throws InvalidTimeException {
        if (timeSlot.isStartAfterEnd()) {
            throw new InvalidTimeException(MESSAGE_INVALID_TIME);
        }

        return makeEngagement(type, timeSlot, attendees, location, description, priority);
    }

    /**
     * Returns a {@code Meeting}, {@code Interview}, or {@code Appointment} with the given fields.
     * @param type type of engagement.
     * @param timeSlot {@code TimeSlot} of the engagement.
     * @param attendees list of people attending.
     * @param location location of engagement.
     * @param description description of the engagement.
     * @param priority priority level of the engagement.
     *
     * @return an {@code Engagement} with the corresponding fields.
     */
    private static Engagement makeEngagement(EngagementType type, TimeSlot timeSlot,
                                             AttendeeList attendees, Location location,
                                             String description, Priority priority) {
        if (type.name().equalsIgnoreCase("meeting")) {
            return new Meeting(timeSlot, attendees, location, description, priority);
        } else if (type.name().equalsIgnoreCase("interview")) {
            return new Interview(timeSlot, attendees, location, description, priority);
        } else {
            return new Appointment(timeSlot, attendees, location, description, priority);
        }
    }

    /**
     * Checks if the start time occurs after or during the end time.
     * @return true if the start time is during or after the end time.
     */
    private static boolean isStartAfterEnd(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.isAfter(endTime) || startTime.isEqual(endTime);
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        requireNonNull(timeSlot);
        this.timeSlot = timeSlot;
    }

    public AttendeeList getAttendees() {
        return attendees.copy();
    }

    public void setAttendees(AttendeeList attendees) {
        requireNonNull(attendees);
        this.attendees = attendees;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        requireNonNull(location);
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        requireNonNull(description);
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        requireNonNull(priority);
        this.priority = priority;
    }

    public abstract EngagementType getType();

    /**
     * Checks if this {@code Engagement} clashes with another one.
     *
     * @param engagement the {@code Engagement} to check for a clash.
     * @return true if there is a clash.
     */
    public boolean isConflictingWith(Engagement engagement) {
        return EngagementConflictChecker.areConflicting(this, engagement);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            // short-circuit.
            return true;
        } else if (o instanceof Engagement) {
            // type-cast Object and check for field equality.
            Engagement otherEngagement = (Engagement) o;
            return isSameEngagement(otherEngagement);
        } else {
            return false;
        }
    }

    /**
     * Checks if the given {@code Engagement} is logically equal to the caller {@code Engagement}.
     *
     * @param otherEngagement {@code Engagement} to check equality with.
     * @return true if the engagements are equal.
     */
    public boolean isSameEngagement(Engagement otherEngagement) {
        if (otherEngagement == this) {
            return true;
        }
        return otherEngagement != null
                && otherEngagement.location.equals(location)
                && otherEngagement.attendees.equals(attendees)
                && otherEngagement.description.equals(description)
                && otherEngagement.timeSlot.equals(timeSlot)
                && otherEngagement.priority.equals(priority)
                && otherEngagement.getType().equals(this.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSlot, location, description, attendees, priority);
    }
}
