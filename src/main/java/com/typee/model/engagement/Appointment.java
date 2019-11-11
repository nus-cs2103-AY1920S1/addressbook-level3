package com.typee.model.engagement;

/**
 * Represents an {@code Appointment}.
 */
public class Appointment extends Engagement {
    protected Appointment(TimeSlot timeSlot,
                      AttendeeList attendees, Location location, String description, Priority priority) {
        super(timeSlot, attendees, location, description, priority);
        this.timeSlot = timeSlot;
        this.attendees = attendees;
        this.location = location;
        this.description = description;
        this.priority = priority;
    }

    @Override
    public EngagementType getType() {
        return EngagementType.APPOINTMENT;
    }

    @Override
    public String toString() {
        return String.format("Appointment of %s priority from %s to %s at %s.", priority.toString(),
                timeSlot.getStartTime().toString(), timeSlot.getEndTime().toString(), location.toString());
    }
}
