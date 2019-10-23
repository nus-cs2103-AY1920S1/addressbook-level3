package seedu.address.storage.mapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.mapping.InvMemMapping;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedInvMemMapping {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Mapping's %s field is missing!";

    private final int inventoryIndex;
    private final int memberIndex;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedInvMemMapping(@JsonProperty("task") int inventoryIndex,
                              @JsonProperty("member") int memberIndex) {
        this.inventoryIndex = inventoryIndex;
        this.memberIndex = memberIndex;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedInvMemMapping(InvMemMapping source) {
        inventoryIndex = source.getInventoryIndex();
        memberIndex = source.getMemberIndex();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public InvMemMapping toModelType() throws IllegalValueException {
        return new InvMemMapping(inventoryIndex, memberIndex);
    }

}
