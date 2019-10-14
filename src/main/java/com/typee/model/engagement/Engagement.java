package com.typee.model.engagement;

import java.time.LocalDateTime;
import java.util.List;

import com.typee.model.person.Person;

/**
 * Represents a generalization of meetings, interviews and appointments.
 */
public class Engagement {
    protected LocalDateTime start;
    protected LocalDateTime end;
    //future modification of Person class is required -- Ko Gi Hun
    protected List<Person> managers;
    protected List<Person> attendees;
    protected Location location;
    protected String description;
    protected Priority priority;

    protected Engagement(LocalDateTime start, LocalDateTime end, List<Person> managers,
                      List<Person> attendees, Location location, String description, Priority priority) {
        this.start = start;
        this.end = end;
        this.managers = managers;
        this.attendees = attendees;
        this.location = location;
        this.description = description;
        this.priority = priority;
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

    public List<Person> getManagers() {
        return managers;
    }

    public void setManagers(List<Person> managers) {
        this.managers = managers;
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
}
