package mams.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import mams.commons.exceptions.IllegalValueException;
import mams.model.module.Module;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MISSING_MODULE_CODE_FIELD_MESSAGE_FORMAT =
            "Module's module code field is missing!";
    public static final String MISSING_SESSION_ID_FIELD_MESSAGE_FORMAT =
            "Module's session id field is missing!";
    public static final String MISSING_TIME_SLOT_FIELD_MESSAGE_FORMAT =
            "Module's time slot field is missing!";

    private final String moduleCode;
    private final int sessionId;
    private final int[] timeSlot; //int[] or List<Integer>?

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleCode,
                             @JsonProperty("sessionId") int sessionId,
                              @JsonProperty("timeSlot") int[] timeSlot) {
        this.moduleCode = moduleCode;
        this.sessionId = sessionId;
        this.timeSlot = timeSlot;
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModuleCode();
        sessionId = source.getSessionId();
        timeSlot = source.getTimeSlot();
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Module toModelType() throws IllegalValueException {
        if (moduleCode == null) {
            throw new IllegalValueException(MISSING_MODULE_CODE_FIELD_MESSAGE_FORMAT);
        }
        if (!Module.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_MODULE_CODE);
        }
        final String modelModuleCode = moduleCode;

        //undeclared
        if (sessionId == 0) {
            throw new IllegalValueException(MISSING_SESSION_ID_FIELD_MESSAGE_FORMAT);
        }
        if (!Module.isValidSessionId(sessionId)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_SESSION_ID);
        }
        final int modelSessionId = sessionId;

        if (timeSlot == null) {
            throw new IllegalValueException(MISSING_TIME_SLOT_FIELD_MESSAGE_FORMAT);
        }
        if (!Module.isValidTimeSlot(timeSlot)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_TIME_SLOT);
        }
        final int[] modelTimeSlot = timeSlot;
        return new Module(modelModuleCode, modelSessionId, modelTimeSlot);
    }
}
