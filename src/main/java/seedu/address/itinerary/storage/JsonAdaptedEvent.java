package seedu.address.itinerary.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Tag;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;

public class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String title;
    private final String date;
    private final String time;
    private final String location;
    private final String description;
    private final String tag;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("title") String title, @JsonProperty("date") String date,
                            @JsonProperty("time") String time, @JsonProperty("location") String location,
                            @JsonProperty("description") String description, @JsonProperty("tag") String tag) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
        this.tag = tag;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        title = source.getTitle().toString();
        date = source.getDate().toString();
        time = source.getTime().toString();
        location = source.getLocation().toString();
        description = source.getDesc().toString();
        tag = source.getTag().toString();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event}
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    public Event toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "title of event"));
        }
        final Title title = new Title(this.title);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date of event"));
        }
        final Date date = new Date(this.date);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "time of event"));
        }
        final Time time = new Time(this.time);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "location of event"));
        }
        final Location location = new Location(this.location);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "descriptin of event"));
        }
        final Description description = new Description(this.description);

        if (tag == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "tag of event"));
        }
        final Tag tag = new Tag(this.tag);


        return new Event(title, date, location, description, time, tag);
    }
}
