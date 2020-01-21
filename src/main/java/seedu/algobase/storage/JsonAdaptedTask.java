package seedu.algobase.storage;

import java.time.DateTimeException;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Id;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.task.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String id;
    private final String problemId;
    private final String dueDate;
    private final String isDone;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given Task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("id") String id,
                           @JsonProperty("problemId") String problemId,
                           @JsonProperty("dueDate") String dueDate,
                           @JsonProperty("isDone") String isDone) {
        this.id = id;
        this.problemId = problemId;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task task) {
        id = task.getId().toString();
        problemId = task.getProblem().getId().toString();
        dueDate = task.getDueDate().format(ParserUtil.FORMATTER);
        isDone = Boolean.toString(task.getIsDone());
    }

    /**
     * Converts this Jackson-friendly adapted Task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task.
     */
    public Task toModelType(AlgoBase algoBase) throws IllegalValueException {
        final Id modelId = retrieveId(id);
        final Problem modelProblem = algoBase.findProblemById(retrieveId(problemId));
        final LocalDate modelDueDate = retrieveDate(dueDate);
        final boolean modelIsSolved = retrieveIsDone(isDone);

        return new Task(modelId, modelProblem, modelDueDate, modelIsSolved);
    }

    /**
     * Converts an id in string format to a long number.
     *
     * @param id id in String format.
     * @return id in long format.
     * @throws IllegalValueException if string format is invalid.
     */
    public Id retrieveId(String id) throws IllegalValueException {
        if (id == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"));
        }

        try {
            return Id.generateId(id);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"));
        }
    }

    /**
     * Converts a date in string format to a LocalDate Object.
     *
     * @param date date in string format.
     * @return the corresponding LocalDate Object.
     * @throws IllegalValueException if {@code date} is invalid.
     */
    private LocalDate retrieveDate(String date) throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date"));
        }

        try {
            return LocalDate.parse(date, ParserUtil.FORMATTER);
        } catch (DateTimeException e) {
            throw new IllegalValueException(ParserUtil.DATE_CONSTRAINTS);
        }
    }

    /**
     * Converts a boolean in string format to a boolean.
     *
     * @param isDone boolean in string format.
     * @return the corresponding boolean.
     * @throws IllegalValueException if {@code date} is invalid.
     */
    private boolean retrieveIsDone(String isDone) throws IllegalValueException {
        if (isDone == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "IsSolved"));
        }

        return Boolean.parseBoolean(isDone);
    }
}
