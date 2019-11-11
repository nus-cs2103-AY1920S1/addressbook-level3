package com.typee.model.engagement;

/**
 * Represents an {@code Interview}.
 */
public class Interview extends Engagement {
    protected Interview(TimeSlot timeSlot,
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
        return EngagementType.INTERVIEW;
    }

    @Override
    public String toString() {
        return String.format("Interview of %s priority from %s to %s at %s.", priority.toString(),
                timeSlot.getStartTime().toString(), timeSlot.getEndTime().toString(), location.toString());
    }
}
