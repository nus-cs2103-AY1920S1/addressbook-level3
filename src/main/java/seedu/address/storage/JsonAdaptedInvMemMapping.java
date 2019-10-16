package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.mapping.InvMemMapping;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedInvMemMapping {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Mapping's %s field is missing!";

    private final int member;
    private final int inventory;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedInvMemMapping(@JsonProperty("member") int member,
                              @JsonProperty("inventory") int inventory){
        this.member = member;
        this.inventory = inventory;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedInvMemMapping(InvMemMapping source) {
        member = source.getMemberIndex();
        inventory = source.getInventoryIndex();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public InvMemMapping toModelType() throws IllegalValueException {
        return new InvMemMapping(member, inventory);
    }
}
