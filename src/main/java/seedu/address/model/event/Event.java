package seedu.address.model.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DATETIME_RANGE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_EVENT_NAME;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.EventUtil.validateStartEndDateTime;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a event in the njoy assistant
 * Maps into a vEvent for UI (iCalendarAgenda) rendering and processing
 */
public class Event {
    public static final String INVALID_COLOR_CATEGORY = "Invalid color category."
            + " Should be of form groupXX where XX is a within range 00 - 23";
    public static final String COLOR_CATEGORY_VALIDATION_REGEX = "group[0-2][0-9]";

    private String eventName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String colorCategory;
    private String uniqueIdentifier;
    private RecurrenceType recurrenceType;

    public Event() {
    }

    public Event(String eventName, LocalDateTime startDateTime, LocalDateTime endDateTime,
                 String colorCategory, String uniqueIdentifier, RecurrenceType recurrenceType) {
        requireAllNonNull(eventName, startDateTime, endDateTime, colorCategory, uniqueIdentifier, recurrenceType);
        checkArgument(validateStartEndDateTime(startDateTime, endDateTime), MESSAGE_INVALID_EVENT_DATETIME_RANGE);
        checkArgument(isValidColorCategory(colorCategory), INVALID_COLOR_CATEGORY);
        checkArgument(isValidEventName(eventName), MESSAGE_MISSING_EVENT_NAME);
        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.colorCategory = colorCategory;
        this.uniqueIdentifier = uniqueIdentifier;
        this.recurrenceType = recurrenceType;
    }

    /**
     * Validates that the event name is not blank
     * @param eventName event name string to be validated
     * @return true if event name is not blank
     */
    public boolean isValidEventName(String eventName) {
        if (eventName.isBlank()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to validate color category
     * @param colorCategory color category to be validated
     * @return true if colorCategory is valid
     */
    private boolean isValidColorCategory(String colorCategory) {
        if (!colorCategory.matches(COLOR_CATEGORY_VALIDATION_REGEX)) {
            return false;
        } else {
            // might be values from 24 - 29
            Integer colorNumber = Integer.parseInt(colorCategory.substring(5));
            if (colorNumber > 23 || colorNumber < 0) {
                return false;
            }
        }
        return true;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getColorCategory() {
        return colorCategory;
    }

    public void setColorCategory(String colorCategory) {
        this.colorCategory = colorCategory;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public RecurrenceType getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(RecurrenceType rType) {
        this.recurrenceType = rType;
    }

    /**
     * Returns true if both notes have the same note title and description.
     * This defines a stronger notion of equality between two notes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Event)) {
            return false;
        }
        Event otherEvent = (Event) other;
        return otherEvent.getEventName().equals(getEventName())
                && otherEvent.getRecurrenceType().equals(getRecurrenceType())
                && otherEvent.getUniqueIdentifier().equals(getUniqueIdentifier())
                && otherEvent.getStartDateTime().equals(getStartDateTime())
                && otherEvent.getEndDateTime().equals(getEndDateTime())
                && otherEvent.getColorCategory().equals(getColorCategory());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, startDateTime, endDateTime, recurrenceType, colorCategory, uniqueIdentifier);
    }
}
