package seedu.algobase.storage;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.task.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String id;
    private final String problemId;
    private final String dateTime;
    private final String isSolved;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given Task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("id") String id,
                           @JsonProperty("problemId") String problemId,
                           @JsonProperty("dateTime") String dateTime,
                           @JsonProperty("isSolved") String isSolved) {
        this.id = id;
        this.problemId = problemId;
        this.dateTime = dateTime;
        this.isSolved = isSolved;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task task) {
        id = Long.toString(task.getId());
        problemId = Long.toString(task.getProblem().getId());
        dateTime = task.getDateTime().format(Task.FORMATTER);
        isSolved = Boolean.toString(task.getIsSolved());
    }

    /**
     * Converts this Jackson-friendly adapted Task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task.
     */
    public Task toModelType(AlgoBase algoBase) throws IllegalValueException {
        final long modelId = retrieveId(id);
        final Problem modelProblem = algoBase.findProblemById(retrieveId(problemId));
        final LocalDateTime modelDateTime = retrieveDate(dateTime);
        final boolean modelIsSolved = retrieveIsSolved(isSolved);

        return new Task(modelId, modelProblem, modelIsSolved);
    }

    /**
     * Converts an id in string format to a long number.
     *
     * @param id id in String format.
     * @return id in long format.
     * @throws IllegalValueException if string format is invalid.
     */
    public long retrieveId(String id) throws IllegalValueException {
        if (id == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"));
        }

        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"));
        }
    }

    /**
     * Converts a date in string format to a LocalDateTime Object.
     *
     * @param date date in string format.
     * @return the corresponding LocalDateTime Object.
     * @throws IllegalValueException if {@code date} is invalid.
     */
    private LocalDateTime retrieveDate(String date) throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date"));
        }

        try {
            return LocalDateTime.parse(date, Task.FORMATTER);
        } catch (DateTimeException e) {
            throw new IllegalValueException(Task.DATE_TIME_CONSTRAINTS);
        }
    }

    /**
     * Converts a boolean in string format to a boolean.
     *
     * @param isSolved boolean in string format.
     * @return the corresponding boolean.
     * @throws IllegalValueException if {@code date} is invalid.
     */
    private boolean retrieveIsSolved(String isSolved) throws IllegalValueException {
        if (isSolved == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "IsSolved"));
        }

        return Boolean.parseBoolean(isSolved);
    }
}
