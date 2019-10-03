package seedu.address.model.item;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Stub class for the event class.
 */
public class Event {
    private String date;

    public String getDate() {
        return this.date;
    }

    /**
     * Creates an event from a JSON string.
     * @param json a JSON string representation of the event
     * @return a newly created event
     * @throws IOException throws an exception if the JSON string format is incorrect.
     */
    public static Event eventFromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Event event = objectMapper.readValue(json, Event.class);
        return event;
    }

    /**
     * Create a string representation of the event object.
     * @return a string representation of the object
     * @throws JsonProcessingException throws an exception if the object cannot be expressed as a JSON string
     */
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
