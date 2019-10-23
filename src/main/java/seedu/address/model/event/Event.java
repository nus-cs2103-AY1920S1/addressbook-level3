package seedu.address.model.event;

import java.time.LocalDateTime;

/**
 * Represents a event in the njoy assistant
 * Maps into a vEvent for UI (iCalendarAgenda) rendering and processing
 */
public class Event {
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
        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.colorCategory = colorCategory;
        this.uniqueIdentifier = uniqueIdentifier;
        this.recurrenceType = recurrenceType;
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
}
