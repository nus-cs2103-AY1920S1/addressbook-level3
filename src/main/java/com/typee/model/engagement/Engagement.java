package com.typee.model.engagement;

import java.time.LocalDateTime;
import java.util.List;

import com.typee.model.person.Person;

/**
 * Represents a generalization of meetings, interviews and appointments.
 */
public abstract class Engagement {
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected List<Person> attendees;
    protected Location location;
    protected String description;
    protected Priority priority;

    protected Engagement(LocalDateTime start, LocalDateTime end,
                         List<Person> attendees, Location location, String description, Priority priority) {
        this.start = start;
        this.end = end;
        this.attendees = attendees;
        this.location = location;
        this.description = description;
        this.priority = priority;
    }

    public static Engagement of(EngagementType type,
                                LocalDateTime start, LocalDateTime end,
                                List<Person> attendees, Location location, String description,
                                Priority priority) {
        return new Meeting(start, end, attendees, location, description, priority);
    }

    public Engagement makeEngagement(String userInput) {
        return null;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public List<Person> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Person> attendees) {
        this.attendees = attendees;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isSameEngagement(Engagement engagement) {
        return engagement.end.equals(end)
                && engagement.start.equals(start)
                && engagement.location.equals(location);
    }
}
