package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.mapping.Mapping;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedMapping {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Mapping's %s field is missing!";

    private final JsonAdaptedMember member;
    private final JsonAdaptedTask task;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedMapping(@JsonProperty("task") JsonAdaptedTask task,
                              @JsonProperty("member") JsonAdaptedMember member){
        this.task = task;
        this.member = member;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedMapping(Mapping source) {
        task = new JsonAdaptedTask(source.getTask());
        member = new JsonAdaptedMember(source.getMember());
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Mapping toModelType() throws IllegalValueException {
        if (task == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Task.class.getSimpleName()));
        }
        if (member == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Member.class.getSimpleName()));
        }
        return new Mapping(member.toModelType(), task.toModelType());
    }

}
