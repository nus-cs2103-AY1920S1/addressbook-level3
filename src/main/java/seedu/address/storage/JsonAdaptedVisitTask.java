package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.visittask.Detail;
import seedu.address.model.visittask.VisitTask;
import seedu.address.model.visittodo.VisitTodo;

/**
 * Jackson-friendly version of {@link VisitTask}.
 */
class JsonAdaptedVisitTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "VisitTask's %s field is missing!";

    private final JsonAdaptedVisitTodo visitTodo;
    private final String detail;
    private final Boolean isDone;

    /**
     * Constructs a {@code JsonAdaptedVisitTask} with the given visit detail.
     */
    @JsonCreator
    public JsonAdaptedVisitTask(@JsonProperty("visitTodo") JsonAdaptedVisitTodo visitTodo,
                                    @JsonProperty("detail") String detail,
                                    @JsonProperty("isDone") Boolean isDone) {
        this.visitTodo = visitTodo;
        this.detail = detail;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code VisitTask} into this class for Jackson use.
     */
    public JsonAdaptedVisitTask(VisitTask source) {
        visitTodo = new JsonAdaptedVisitTodo(source.getVisitTodo());
        detail = source.getDetail().detail;
        isDone = source.isDone();
    }

    /**
     * Converts this Jackson-friendly adapted visit object into the model's {@code VisitTask} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted visit.
     */
    public VisitTask toModelType() throws IllegalValueException {
        if (visitTodo == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    VisitTodo.class.getSimpleName()));
        }
        final VisitTodo modelVisitTodo = visitTodo.toModelType();

        if (detail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Detail.class.getSimpleName()));
        }
        if (!Detail.isValidDetail(detail)) {
            throw new IllegalValueException(Detail.MESSAGE_CONSTRAINTS);
        }
        final Detail modelDetail = new Detail(detail);

        if (isDone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "IsDone"));
        }

        return new VisitTask(modelVisitTodo, modelDetail, isDone);
    }

}
