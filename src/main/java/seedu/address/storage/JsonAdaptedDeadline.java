package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.deadline.DueDate;
import seedu.address.model.deadline.Task;


/**
 * Jackson-friendly version of {@link Deadline}.
 */
class JsonAdaptedDeadline {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deadline's %s field is missing!";

    private final String task;
    private final String date;

    /**
     * Constructs a {@code JsonAdaptedDeadline} with the given deadline details.
     */
    @JsonCreator
    public JsonAdaptedDeadline(@JsonProperty("task") String task,
                               @JsonProperty("date") String date) {
        this.task = task;
        this.date = date;
    }

    /**
     * Converts a given {@code FlashCard} into this class for Jackson use.
     */
    public JsonAdaptedDeadline(Deadline source) {
        task = source.getTask().fullTask;
        date = source.getDueDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted flashCard object into the model's {@code FlashCard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted flashCard.
     */
    public Deadline toModelType() throws IllegalValueException {

        if (task == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                            Task.class.getSimpleName()));
        }
        if (!Task.isValidTask(task)) {
            throw new IllegalValueException(Task.MESSAGE_CONSTRAINTS);
        }
        final Task modelTask = new Task(task);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DueDate.class.getSimpleName()));
        }
        if (!DueDate.isValidDate(date)) {
            throw new IllegalValueException(DueDate.MESSAGE_CONSTRAINTS);
        }
        final DueDate modelDueDate = new DueDate(date);

        return new Deadline(modelTask, modelDueDate);
    }

}
