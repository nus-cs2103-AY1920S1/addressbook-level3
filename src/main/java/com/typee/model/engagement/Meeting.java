package com.typee.model.engagement;

import com.typee.model.person.Person;
import java.time.LocalDateTime;
import java.util.List;

public class Meeting extends Engagement {

    protected Meeting(LocalDateTime start, LocalDateTime end,
                      AttendeeList attendees, Location location, String description, Priority priority) {
        super(start, end, attendees, location, description, priority);
        this.startTime = start;
        this.endTime = end;
        this.attendees = attendees;
        this.location = location;
        this.description = description;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("Meeting of %s priority from %s to %s at %s.", priority.toString(),
                startTime.toString(), endTime.toString(), location.toString());
    }
}
