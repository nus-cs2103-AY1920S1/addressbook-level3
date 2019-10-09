package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.visittodo.VisitTodo;
import seedu.address.model.visittodoitem.Detail;
import seedu.address.model.visittodoitem.VisitTodoItem;

/**
 * Jackson-friendly version of {@link VisitTodoItem}.
 */
class JsonAdaptedVisitTodoItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "VisitTodoItem's %s field is missing!";

    private final JsonAdaptedVisitTodo visitTodo;
    private final String detail;
    private final Boolean isDone;

    /**
     * Constructs a {@code JsonAdaptedVisitTodoItem} with the given visit detail.
     */
    @JsonCreator
    public JsonAdaptedVisitTodoItem(@JsonProperty("visitTodo") JsonAdaptedVisitTodo visitTodo,
                                    @JsonProperty("detail") String detail,
                                    @JsonProperty("isDone") Boolean isDone) {
        this.visitTodo = visitTodo;
        this.detail = detail;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code VisitTodoItem} into this class for Jackson use.
     */
    public JsonAdaptedVisitTodoItem(VisitTodoItem source) {
        visitTodo = new JsonAdaptedVisitTodo(source.getVisitTodo());
        detail = source.getDetail().detail;
        isDone = source.isDone();
    }

    /**
     * Converts this Jackson-friendly adapted visit object into the model's {@code VisitTodoItem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted visit.
     */
    public VisitTodoItem toModelType() throws IllegalValueException {
        if (visitTodo == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, VisitTodo.class.getSimpleName()));
        }
        final VisitTodo modelVisitTodo = visitTodo.toModelType();

        if (detail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Detail.class.getSimpleName()));
        }
        if (!Detail.isValidDetail(detail)) {
            throw new IllegalValueException(Detail.MESSAGE_CONSTRAINTS);
        }
        final Detail modelDetail = new Detail(detail);

        if (isDone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "IsDone"));
        }

        return new VisitTodoItem(modelVisitTodo, modelDetail, isDone);
    }

}
