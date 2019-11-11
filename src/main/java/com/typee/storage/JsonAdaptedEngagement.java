package com.typee.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.typee.commons.exceptions.IllegalValueException;
import com.typee.model.engagement.AttendeeList;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.EngagementType;
import com.typee.model.engagement.Location;
import com.typee.model.engagement.Priority;
import com.typee.model.engagement.TimeSlot;
import com.typee.model.engagement.exceptions.InvalidTimeException;

/**
 * Jackson-friendly version of {@link Engagement}.
 */
class JsonAdaptedEngagement {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Engagement's %s field is missing!";
    public static final String EMPTY_LIST = "[]";

    private static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Description cannot be blank!";

    private final String engagementType;
    private final String timeSlot;
    private final String location;
    private final String attendees;
    private final String description;
    private final String priority;

    /**
     * Constructs a {@code JsonAdaptedEngagement} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedEngagement(@JsonProperty("type") String type, @JsonProperty("timeSlot") String timeSlot,
                                 @JsonProperty("location") String location,
                                 @JsonProperty("description") String description,
                                 @JsonProperty("attendees") String attendees,
                                 @JsonProperty("priority") String priority) {
        this.engagementType = type;
        this.timeSlot = timeSlot;
        this.location = location;
        this.attendees = attendees;
        this.description = description;
        this.priority = priority;
    }

    /**
     * Converts a given {@code Engagement} into this class for Jackson use.
     */
    public JsonAdaptedEngagement(Engagement source) {
        engagementType = source.getClass().getSimpleName();
        timeSlot = source.getTimeSlot().toString();
        location = source.getLocation().toString();
        description = source.getDescription();
        attendees = source.getAttendees().toString();
        priority = source.getPriority().name();
    }

    /**
     * Converts this Jackson-friendly adapted engagement object into the model's {@code Engagement} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted engagement.
     */
    public Engagement toModelType() throws IllegalValueException {

        final EngagementType modelType = validateAndGetEngagementType();
        final TimeSlot modelTimeSlot = validateAndGetTimeSlot();
        final Location modelLocation = validateAndGetLocation();
        final AttendeeList modelAttendees = validateAndGetAttendees();
        final Priority modelPriority = validateAndGetPriority();
        final String modelDescription = validateAndGetDescription();

        try {
            return Engagement.of(modelType, modelTimeSlot,
                    modelAttendees, modelLocation, modelDescription, modelPriority);
        } catch (InvalidTimeException e) {
            throw new IllegalValueException(e.getMessage());
        }
    }

    /**
     * Returns the description of an {@code Engagement} following validation.
     *
     * @return description.
     */
    private String validateAndGetDescription() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    String.class.getSimpleName()));
        }
        if (description.isBlank()) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        return description;
    }

    /**
     * Returns the {@code Priority} of an {@code Engagement} following validation.
     *
     * @return the {@code Priority}
     * @throws IllegalValueException if the json's priority field is invalid.
     */
    private Priority validateAndGetPriority() throws IllegalValueException {
        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        if (!Priority.isValid(priority)) {
            throw new IllegalValueException(Priority.getMessageConstraints());
        }
        return Priority.of(priority);
    }

    /**
     * Returns the {@code AttendeeList} of an {@code Engagement} following validation.
     *
     * @return the {@code AttendeeList}
     * @throws IllegalValueException if the json's attendees field is invalid.
     */
    private AttendeeList validateAndGetAttendees() throws IllegalValueException {
        if (attendees == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AttendeeList.class.getSimpleName()));
        }
        if (!AttendeeList.isValid(attendees)) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AttendeeList.MESSAGE_CONSTRAINTS));
        }
        return AttendeeList.getListGivenValidInput(attendees);
    }

    /**
     * Returns the {@code Location} of an {@code Engagement} following validation.
     *
     * @return the {@code Location} of the {@code Engagement}.
     * @throws IllegalValueException if the json's location field is invalid.
     */
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

    /**
     * Returns the {@code EngagementType} of an {@code Engagement} following validation.
     *
     * @return the {@code EngagementType}
     * @throws IllegalValueException if the json's engagementType field is invalid.
     */
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

    /**
     * Returns the {@code TimeSlot} of an {@code Engagement} following validation.
     * @return the {@code TimeSlot}
     * @throws IllegalValueException if the times are invalid.
     */
    private TimeSlot validateAndGetTimeSlot() throws IllegalValueException {
        if (timeSlot == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TimeSlot.class.getSimpleName()));
        }
        if (!TimeSlot.isValid(timeSlot)) {
            throw new IllegalValueException(TimeSlot.MESSAGE_CONSTRAINTS);
        }
        return TimeSlot.of(timeSlot);
    }
}
