package com.typee.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.typee.commons.exceptions.IllegalValueException;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.EngagementType;
import com.typee.model.engagement.Location;
import com.typee.model.engagement.Priority;
import com.typee.model.person.Name;
import com.typee.model.person.Person;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    public static final String EMPTY_LIST = "[]";

    private final String engagementType;
    private final String startTime;
    private final String endTime;
    private final String location;
    private final String attendees;
    private final String description;
    private final String priority;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("type") String type, @JsonProperty("startTime") String startTime,
                             @JsonProperty("endTime") String endTime, @JsonProperty("location") String location,
                             @JsonProperty("attendees") String attendees,
                             @JsonProperty("description") String description,
                             @JsonProperty("priority") String priority) {
        this.engagementType = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.attendees = attendees;
        this.description = description;
        this.priority = priority;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Engagement source) {
        engagementType = source.getClass().getSimpleName();
        startTime = source.getStart().toString();
        endTime = source.getEnd().toString();
        location = source.getLocation().toString();
        description = source.getDescription();
        attendees = source.getAttendees().toString();
        priority = source.getPriority().name();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Engagement toModelType() throws IllegalValueException {
        // TODO : Date validation.
        final EngagementType modelType = validateAndGetEngagementType();
        final Location modelLocation = validateAndGetLocation();
        final List<Person> modelAttendees = validateAndGetAttendees();
        final Priority modelPriority = validateAndGetPriority();
        final String modelDescription = validateAndGetDescription();

        return Engagement.of(modelType, LocalDateTime.parse(startTime), LocalDateTime.parse(endTime), modelAttendees,
                modelLocation, modelDescription, modelPriority);
    }

    private String validateAndGetDescription() {
        return description;
    }

    private Priority validateAndGetPriority() throws IllegalValueException {
        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName()));
        }
        if (!Priority.isValid(priority)) {
            throw new IllegalValueException(Priority.getMessageConstraints());
        }
        return Priority.of(priority);
    }

    private List<Person> validateAndGetAttendees() throws IllegalValueException {
        if (attendees == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, List.class.getSimpleName()));
        }
        if (attendees.isBlank() || attendees.equalsIgnoreCase(EMPTY_LIST)) {
            throw new IllegalValueException("Invalid person list!");
        }
        return new ArrayList<>();
    }

    private Location validateAndGetLocation() throws IllegalValueException {
        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValid(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(location);
    }

    private EngagementType validateAndGetEngagementType() throws IllegalValueException {
        if (engagementType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EngagementType.class.getSimpleName()));
        }
        if (!EngagementType.isValid(engagementType)) {
            throw new IllegalValueException(EngagementType.getMessageConstraints());
        }
        return EngagementType.of(engagementType);
    }
}
