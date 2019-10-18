package com.typee.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.typee.model.engagement.AttendeeList;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.EngagementType;
import com.typee.model.engagement.Location;
import com.typee.model.engagement.Priority;
import com.typee.model.engagement.exceptions.InvalidTimeException;
import com.typee.model.person.Person;

/**
 * A utility class to help with building Engagement objects.
 */
public class EngagementBuilder {

    private static final int DEFAULT_START_YEAR = 2020;
    private static final int DEFAULT_START_MONTH = 1;
    private static final int DEFAULT_START_DAY = 1;
    private static final int DEFAULT_START_HOUR = 1;
    private static final int DEFAULT_START_MINUTE = 0;
    private static final int DEFAULT_END_YEAR = 2020;
    private static final int DEFAULT_END_MONTH = 1;
    private static final int DEFAULT_END_DAY = 1;
    private static final int DEFAULT_END_HOUR = 2;
    private static final int DEFAULT_END_MINUTE = 0;
    private static final String DEFAULT_LOCATION_NAME = "University Town";
    private static final String DEFAULT_DESCRIPTION = "Tea party";
    private static final Priority DEFAULT_PRIORITY = Priority.LOW;

    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected AttendeeList attendees;
    protected Location location;
    protected String description;
    protected Priority priority;

    /**
     * Constructs the EngagementBuilder with default details.
     */
    public EngagementBuilder() {
        startTime = LocalDateTime.of(DEFAULT_START_YEAR,
                DEFAULT_START_MONTH, DEFAULT_START_DAY, DEFAULT_START_HOUR, DEFAULT_START_MINUTE);
        endTime = LocalDateTime.of(DEFAULT_END_YEAR,
                DEFAULT_END_MONTH, DEFAULT_END_DAY, DEFAULT_END_HOUR, DEFAULT_END_MINUTE);
        List<Person> defaultAttendees = new ArrayList<>();
        PersonBuilder personBuilder = new PersonBuilder();
        defaultAttendees.add(personBuilder.build());
        attendees = new AttendeeList(defaultAttendees);
        location = new Location(DEFAULT_LOCATION_NAME);
        description = DEFAULT_DESCRIPTION;
        priority = DEFAULT_PRIORITY;
    }

    /**
     * Initializes the EngagementBuilder with the data of {@code engagementToCopy}.
     */
    public EngagementBuilder(Engagement engagementToCopy) {
        startTime = engagementToCopy.getStartTime();
        endTime = engagementToCopy.getEndTime();
        attendees = engagementToCopy.getAttendees();
        location = engagementToCopy.getLocation();
        description = engagementToCopy.getDescription();
        priority = engagementToCopy.getPriority();
    }

    /**
     * Returns an appointment, which is a type of engagement, with default the details.
     * @return An appointment with the default details.
     */
    public Engagement buildAsAppointment() {
        Engagement engagement = null;
        try {
            engagement = Engagement.of(EngagementType.APPOINTMENT, startTime, endTime, attendees, location,
                    description, priority);
        } catch (InvalidTimeException e) {
            // Exception should not be thrown when using the default times.
        }
        return engagement;
    }

    /**
     * Returns an appointment, which is a type of engagement, with default the details.
     * @return An interview with the default details.
     */
    public Engagement buildAsInterview() {
        Engagement engagement = null;
        try {
            engagement = Engagement.of(EngagementType.INTERVIEW, startTime, endTime, attendees, location,
                    description, priority);
        } catch (InvalidTimeException e) {
            // Exception should not be thrown when using the default times.
        }
        return engagement;
    }

    /**
     * Returns an appointment, which is a type of engagement, with default details.
     * @return A meeting with the default details.
     */
    public Engagement buildAsMeeting() {
        Engagement engagement = null;
        try {
            engagement = Engagement.of(EngagementType.MEETING, startTime, endTime, attendees, location,
                    description, priority);
        } catch (InvalidTimeException e) {
            // Exception should not be thrown when using the default times.
        }
        return engagement;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    /*
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public Person build() {
        return new Person(name);
    }
     */

}
