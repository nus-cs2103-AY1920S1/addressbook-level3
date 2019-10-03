package seedu.address.model.item;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Stub class for the task object.
 */
public class Task {
    /**
     * Enum class that contains the priority of the tasks.
     */
    enum Priority implements Comparable<Priority> {
        HIGH, MEDIUM, LOW;
    }

    private boolean done;
    private Priority priority;

    public Task() {
        done = false;
        priority = Priority.MEDIUM;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public boolean isDone() {
        return this.done;
    }

    /**
     * Creates a Task from a JSON string.
     * @param json a JSON string representation of the Task
     * @return a newly created task
     * @throws IOException throws an exception if the JSON string format is incorrect.
     */
    public static Task taskFromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Task task = objectMapper.readValue(json, Task.class);
        return task;
    }

    /**
     * Create a string representation of the Task object.
     * @return a string representation of the object
     * @throws JsonProcessingException throws an exception if the object cannot be expressed as a JSON string
     */
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
