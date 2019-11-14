package cs.f10.t1.nursetraverse.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.model.visittodo.VisitTodo;

/**
 * Jackson-friendly version of {@link VisitTodo}.
 */
public class JsonAdaptedVisitTodo {

    private final String description;

    /**
     * Constructs a {@code JsonAdaptedVisitTodo} with the given {@code description}.
     */
    @JsonCreator
    public JsonAdaptedVisitTodo(String description) {
        this.description = description;
    }

    /**
     * Converts a given {@code VisitTodo} into this class for Jackson use.
     */
    public JsonAdaptedVisitTodo(VisitTodo source) {
        description = source.description;
    }

    @JsonValue
    public String getVisitTodoDescription() {
        return description;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code VisitTodo} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public VisitTodo toModelType() throws IllegalValueException {
        if (!VisitTodo.isValidVisitTodoDescription(description)) {
            throw new IllegalValueException(VisitTodo.MESSAGE_CONSTRAINTS);
        }
        return new VisitTodo(description);
    }

}
