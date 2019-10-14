package com.typee.model.engagement;

import com.typee.model.person.Person;
import java.time.LocalDateTime;
import java.util.List;

public class Meeting extends Engagement {
    protected Meeting(LocalDateTime start, LocalDateTime end, List<Person> managers,
                      List<Person> attendees, Location location, String description, Priority priority) {
        super(start, end, attendees, location, description, priority);
        this.start = start;
        this.end = end;
        this.attendees = attendees;
        this.location = location;
        this.description = description;
        this.priority = priority;
    }
}
