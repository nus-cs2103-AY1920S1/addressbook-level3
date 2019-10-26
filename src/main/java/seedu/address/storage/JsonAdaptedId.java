package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.id.IdManager;
import seedu.address.model.task.Task;

/**
 * Jackson-friendly version of {@link IdManager}.
 */
public class JsonAdaptedId {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "IdManager's %s field is missing!";
    public static final String INVALID_INTEGER_ID = "IdManager's %s field has a invalid integer id.";

    private String lastTaskId;
    private String lastCustomerId;
    private String lastDriverId;

    /**
     * Converts a given {@code IdManager} into this class for Jackson use.
     */
    public JsonAdaptedId(IdManager idManager) {
        lastTaskId = String.valueOf(idManager.getLastTaskId());
        lastCustomerId = String.valueOf(idManager.getLastCustomerId());
        lastDriverId = String.valueOf(idManager.getLastDriverId());
    }

    /**
     * Constructs a {@code JsonAdaptedId} with the given Ids.
     */
    @JsonCreator
    public JsonAdaptedId(@JsonProperty("lastTaskId") String lastTaskId,
                         @JsonProperty("lastCustomerId") String lastCustomerId,
                         @JsonProperty("lastDriverId") String lastDriverId) {
        this.lastTaskId = lastTaskId;
        this.lastCustomerId = lastCustomerId;
        this.lastDriverId = lastDriverId;
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public void toModelType() throws IllegalValueException {
        if (lastTaskId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "lastTaskId"));
        }

        if (!Task.isValidId(lastTaskId)) {
            throw new IllegalValueException(String.format(INVALID_INTEGER_ID, "lastTaskId"));
        }
        final int modelLastTaskId = Integer.parseInt(lastTaskId);

        if (lastCustomerId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "lastCustomerId"));
        }

        if (!Task.isValidId(lastCustomerId)) {
            throw new IllegalValueException(String.format(INVALID_INTEGER_ID, "lastCustomerId"));
        }
        final int modelLastCustomerId = Integer.parseInt(lastCustomerId);

        if (lastDriverId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "lastDriverId"));
        }

        if (!Task.isValidId(lastDriverId)) {
            throw new IllegalValueException(String.format(INVALID_INTEGER_ID, "lastDriverId"));
        }
        final int modelLastDriverId = Integer.parseInt(lastDriverId);

        IdManager.setLastTaskId(modelLastTaskId);
        IdManager.setLastCustomerId(modelLastCustomerId);
        IdManager.setLastDriverId(modelLastDriverId);
    }
}
