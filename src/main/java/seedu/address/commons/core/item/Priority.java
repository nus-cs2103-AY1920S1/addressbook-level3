package seedu.address.commons.core.item;

import com.fasterxml.jackson.databind.JsonNode;
import seedu.address.commons.util.JsonUtil;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Available priority levels for tasks and events.
 */
public enum Priority {
    HIGH, MEDIUM, LOW;
    /**
     * Creates a Priority object from a JSON string.
     * @param jsonString the JSON string that represents the Priority
     * @return the reminder object that is created
     * @throws IOException when the jsonString is not in JSON format
     */
    public static Reminder fromJson(String jsonString) throws IOException {
        JsonNode node = JsonUtil.getObjectMapper().readTree(jsonString);
        String dateTimeString = node.get("dateTime").asText();
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);

        return new Reminder(dateTime);
    }
}
