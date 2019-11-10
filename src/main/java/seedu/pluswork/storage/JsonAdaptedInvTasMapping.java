package seedu.pluswork.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.pluswork.commons.exceptions.IllegalValueException;
import seedu.pluswork.model.mapping.InvTasMapping;

/**
 * Jackson-friendly version of {@link InvTasMapping}.
 */
class JsonAdaptedInvTasMapping {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Mapping's %s field is missing!";

    private final int taskIndex;
    private final int inventoryIndex;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedInvTasMapping(@JsonProperty("task") int taskIndex,
                                    @JsonProperty("inventory") int inventoryIndex) {
        this.taskIndex = taskIndex;
        this.inventoryIndex = inventoryIndex;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedInvTasMapping(InvTasMapping source) {
        taskIndex = source.getTaskIndex();
        inventoryIndex = source.getInventoryIndex();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public InvTasMapping toModelType() throws IllegalValueException {
        return new InvTasMapping(taskIndex, inventoryIndex);
    }
}
