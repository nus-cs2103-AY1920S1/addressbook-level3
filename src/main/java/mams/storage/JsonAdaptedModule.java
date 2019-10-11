package mams.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import mams.commons.exceptions.IllegalValueException;
import mams.model.module.Module;
import mams.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final String sessionId;
    private final String timeSlot;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>(); //students

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    //todo
    public JsonAdaptedModule(@JsonProperty("code") String moduleCode, @JsonProperty("sessionid") String sessionId,
                             @JsonProperty("timeslot") String timeSlot,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.moduleCode = moduleCode;
        this.sessionId = sessionId;
        this.timeSlot = timeSlot;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModuleCode();
        sessionId = source.getSessionId();
        timeSlot = source.getTimeSlot();
        tagged.addAll(source.getStudents().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Module toModelType() throws IllegalValueException {
        final List<Tag> moduleTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            moduleTags.add(tag.toModelType());
        }

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Module Code"));
        }
        if (!Module.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_MODULE_CODE);
        }
        final String modelModuleCode = moduleCode;

        if (sessionId == null) { //sessionId expected for Json data
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Session Id"));
        }
        if (!Module.isValidSessionId(sessionId)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_SESSION_ID);
        }
        final String modelSessionId = sessionId;

        if (timeSlot == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Time Slot"));
        }
        if (!Module.isValidTimeSlot(timeSlot)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_TIME_SLOT);
        }
        final String modelTimeSlot = timeSlot;

        final Set<Tag> modelTags = new HashSet<>(moduleTags); //students
        return new Module(modelModuleCode, modelSessionId, modelTimeSlot, modelTags);
    }
}
