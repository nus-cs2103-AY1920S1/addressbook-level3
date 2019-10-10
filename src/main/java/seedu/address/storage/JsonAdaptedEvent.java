package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventHoursNeeded;
import seedu.address.model.event.EventId;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String eventId;
    private final String eventName;
    private final String eventVenue;
    private final String hoursNeeded;
    private final String manpowerNeeded;
    //private final String startDate;
    //private final String endDate;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    //public JsonAdaptedEvent(@JsonProperty("eventId") String eventId,
    //@JsonProperty("eventName") String eventName,
    //@JsonProperty("eventVenue") String eventVenue,
    //@JsonProperty("hoursNeeded") String hoursNeeded,
    //@JsonProperty("manpowerNeeded") String manpowerNeeded,
    //@JsonProperty("startDate") String startDate,
    //@JsonProperty("endDate") String endDate,
    // @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
    public JsonAdaptedEvent(@JsonProperty("eventId") String eventId, @JsonProperty("eventName") String eventName,
                            @JsonProperty("eventVenue") String eventVenue,
                            @JsonProperty("hoursNeeded") String hoursNeeded,
                            @JsonProperty("manpowerNeeded") String manpowerNeeded,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventVenue = eventVenue;
        this.hoursNeeded = hoursNeeded;
        this.manpowerNeeded = manpowerNeeded;
        //this.startDate = startDate;
        //this.endDate = endDate;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        eventId = source.getEventId().id;
        eventName = source.getName().eventName;
        eventVenue = source.getVenue().venue;
        hoursNeeded = source.getHoursNeeded().toString();
        manpowerNeeded = source.getManpowerNeeded().toString();
        //startDate = source.getStartDate().toString();
        //endDate = source.getEndDate().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        final List<Tag> eventTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            eventTags.add(tag.toModelType());
        }

        if (eventId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, EventId.class.getSimpleName()));
        }
        if (!EventId.isValidId(eventId)) {
            throw new IllegalValueException(EventId.MESSAGE_CONSTRAINTS);
        }
        final EventId modelId = new EventId();

        if (eventName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventName.class.getSimpleName()));
        }
        if (!EventName.isValidName(eventName)) {
            throw new IllegalValueException(EventName.MESSAGE_CONSTRAINTS);
        }
        final EventName modelName = new EventName(eventName);

        if (eventVenue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventVenue.class.getSimpleName()));
        }
        if (!EventVenue.isValidVenue(eventVenue)) {
            throw new IllegalValueException(EventVenue.MESSAGE_CONSTRAINTS);
        }
        final EventVenue modelVenue = new EventVenue(eventVenue);

        if (hoursNeeded == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventHoursNeeded.class.getSimpleName()));
        }
        if (!EventHoursNeeded.isValidEventHours(hoursNeeded)) {
            throw new IllegalValueException(EventHoursNeeded.MESSAGE_CONSTRAINTS);
        }
        final EventHoursNeeded modelHoursNeeded = new EventHoursNeeded(hoursNeeded);

        if (manpowerNeeded == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventManpowerNeeded.class.getSimpleName()));
        }
        if (!EventManpowerNeeded.isValidEventManpowerNeeded(manpowerNeeded)) {
            throw new IllegalValueException(EventManpowerNeeded.MESSAGE_CONSTRAINTS);
        }
        final EventManpowerNeeded modelManpowerNeeded = new EventManpowerNeeded(manpowerNeeded);
        final Set<Tag> modelTags = new HashSet<>(eventTags);

        return new Event(modelId, modelName, modelVenue, modelHoursNeeded, modelManpowerNeeded, modelTags);
    }

}
