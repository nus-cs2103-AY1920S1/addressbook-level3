package seedu.jarvis.storage.planner;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;
import seedu.jarvis.model.planner.tasks.Event;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.storage.commons.core.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent extends JsonAdaptedTask {

    private final String start;
    private final String end;

    /**
     * Constructs a {@code JsonAdaptedTodo} with the given event details.
     *
     * @param description Description of the event.
     * @param start Starting date of the event.
     * @param end Ending date of the event.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("description") String description, @JsonProperty("priority") String priority,
                            @JsonProperty("frequency") String frequency,
                            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("start") String start,
                            @JsonProperty("end") String end) {
        super(description, priority, frequency, tags);
        this.start = start;
        this.end = end;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     *
     * @param event {@code Event} to be used to construct the {@code JsonAdaptedEvent}.
     */
    public JsonAdaptedEvent(Event event) {
        super(event);
        start = event.getStartDate().format(Task.getDateFormat());
        end = event.getEndDate().format(Task.getDateFormat());
    }

    /**
     * Converts this Jackson-friendly adapted task into the model's {@code Task} object.
     *
     * @return {@code Task} of the Jackson-friendly adapted task.
     * @throws IllegalValueException If there were any data constraints violated in the adapted task.
     */
    @Override
    public Task toModelType() throws IllegalValueException {
        checkPriorityAndFrequency();

        return new Event(description, Priority.valueOf(priority), Frequency.valueOf(frequency), adaptToTags(tags),
                LocalDate.parse(start, Task.getDateFormat()),
                LocalDate.parse(end, Task.getDateFormat()));
    }
}
