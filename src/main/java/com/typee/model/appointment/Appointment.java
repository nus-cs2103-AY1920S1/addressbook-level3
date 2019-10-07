package com.typee.model.appointment;

import java.time.LocalDateTime;
import java.util.List;

import com.typee.model.person.Person;

/**
 * Appointment class
 */
public class Appointment {
    private LocalDateTime start;
    private LocalDateTime end;
    //future modification of Person class is required -- Ko Gi Hun
    private List<Person> managers;
    private List<Person> attendees;
    private Location location;
    private String description;
    private Priority priority;

    public Appointment(LocalDateTime start, LocalDateTime end, List<Person> managers,
                       List<Person> attendees, Location location, String description, Priority priority) {
        this.start = start;
        this.end = end;
        this.managers = managers;
        this.attendees = attendees;
        this.location = location;
        this.description = description;
        this.priority = priority;
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
