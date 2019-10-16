package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.mapping.TasMemMapping;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTasMemMapping {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Mapping's %s field is missing!";

    private final int task;
    private final int member;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTasMemMapping(@JsonProperty("task") int task,
                              @JsonProperty("member") int member){
        this.task = task;
        this.member = member;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTasMemMapping(TasMemMapping source) {
        task = source.getTaskIndex();
        member = source.getMemberIndex();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public TasMemMapping toModelType() throws IllegalValueException {
        return new TasMemMapping(task, member);
    }
}
