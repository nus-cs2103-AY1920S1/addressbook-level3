package seedu.address.model.item;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Stub class for a Reminder object.
 */
public class Reminder {
    private String date;

    /**
     * Creates a Reminder from a JSON string.
     * @param json a JSON string representation of the reminder
     * @return a newly created reminder
     * @throws IOException throws an exception if the JSON string format is incorrect.
     */
    public static Reminder reminderFromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Reminder reminder = objectMapper.readValue(json, Reminder.class);
        return reminder;
    }

    /**
     * Create a string representation of the Reminder object.
     * @return a string representation of the object
     * @throws JsonProcessingException throws an exception if the object cannot be expressed as a JSON string
     */
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public String getDate() {
        return this.date;
    }
}
