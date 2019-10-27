package seedu.jarvis.storage.planner;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.enums.Status;
import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.commons.core.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Deadline}.
 */
public class JsonAdaptedDeadline extends JsonAdaptedTask implements JsonAdapter<Task> {
    private final String date;

    /**
     * Constructs a {@code JsonAdaptedDeadline} with the given deadline details.
     *
     * @param description Description of the deadline.
     * @param date Date of the deadline.
     */
    @JsonCreator
    public JsonAdaptedDeadline(@JsonProperty("description") String description,
                               @JsonProperty("priority") String priority, @JsonProperty("frequency") String frequency,
                               @JsonProperty("status") String status, @JsonProperty("tags") List<JsonAdaptedTag> tags,
                               @JsonProperty("date") String date) {
        super(description, priority, frequency, status, tags);
        this.date = date;
    }

    /**
     * Converts a given {@code Deadline} into this class for Jackson use.
     *
     * @param deadline {@code Deadline} to be used to construct the {@code JsonAdaptedDeadline}.
     */
    public JsonAdaptedDeadline(Deadline deadline) {
        super(deadline);
        date = deadline.getDueDate().format(Task.getDateFormat());
    }

    /**
     * Converts this Jackson-friendly adapted {@code Deadline} into the model's {@code Task} object.
     *
     * @return {@code Task} of the Jackson-friendly adapted {@code Deadline}.
     * @throws IllegalValueException If there were any data constraints violated in the adapted {@code Deadline}.
     */
    @Override
    public Task toModelType() throws IllegalValueException {
        validateAttributes();
        try {
            return new Deadline(
                    description,
                    priority != null ? Priority.valueOf(priority) : null,
                    frequency != null ? Frequency.valueOf(frequency) : null,
                    status != null ? Status.valueOf(status) : null,
                    adaptToTags(tags),
                    LocalDate.parse(date, Task.getDateFormat()));
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(MESSAGE_INVALID_ATTRIBUTES);
        }
    }
}
