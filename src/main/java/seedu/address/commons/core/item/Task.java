package seedu.address.commons.core.item;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;

/**
 * Represents an Item's Task in ELISA.
 * Completion state of Event is false by default.
 * Priority of Event is medium by default.
 * Guarantees: immutable;
 */
public class Task {

    private final Boolean complete;

    /**
     * Constructs a {@code Task}.
     *
     * @param complete Denotes whether the task has been completed or not. Defaults to false if null.
     */
    public Task(Boolean complete) {

        if (complete != null) {
            this.complete = complete;
        } else {
            this.complete = false;
        }
    }

    public Boolean isComplete() {
        return complete;
    }

    public Task markComplete() {
        return new Task(true);
    }

    public Task markIncomplete() {
        return new Task(false);
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append(" Completed: ")
                .append(isComplete().toString());
        return builder.toString();
    }

    //Problematic as the details of the task might be the same while the Item/actual task being referred to is not the
    //same due to description
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.isComplete().equals(isComplete());
    }

    //hashCode is problematic as I believe that there are only 3*2 permutations of priority and Boolean
    //Possibility of high number of hash collisions and as a result slower performance
    @Override
    public int hashCode() {
        return Objects.hash(complete);
    }

    /**
     * Creates a task object from a JSON string.
     * @param jsonString the JSON string that represents the task
     * @return the task object that is created
     * @throws IOException when the jsonString is not in JSON format
     * @throws IllegalValueException when the JSON string contains incorrect value
     */
    public static Task fromJson(String jsonString) throws IOException {
        JsonNode node = JsonUtil.getObjectMapper().readTree(jsonString);
        final Priority p;
        final boolean complete;

        String priorityString = node.get("priority").asText();
        p = Priority.valueOf(priorityString);

        complete = node.get("complete").asBoolean();
        return new Task(complete);
    }
}
