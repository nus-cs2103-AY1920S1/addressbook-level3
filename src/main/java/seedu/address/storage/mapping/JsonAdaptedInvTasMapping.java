package seedu.address.storage.mapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.mapping.InvTasMapping;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedInvTasMapping {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Mapping's %s field is missing!";

    private final int inventoryIndex;
    private final int taskIndex;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedInvTasMapping(@JsonProperty("task") int inventoryIndex,
                              @JsonProperty("member") int taskIndex) {
        this.inventoryIndex = inventoryIndex;
        this.taskIndex = taskIndex;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedInvTasMapping(Mapping source) {
        inventoryIndex = source.getInventoryIndex();
        taskIndex = source.getTaskIndex();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Mapping toModelType() throws IllegalValueException {
        return new InvTasMapping(inventoryIndex, taskIndex);
    }

}
