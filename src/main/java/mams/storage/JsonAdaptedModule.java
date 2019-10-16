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
    private final String moduleName;
    private final String moduleDescription;
    private final String lecturerName;
    private final String timeSlot;
    private final String quota;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>(); //students

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("code") String moduleCode, @JsonProperty("modulename") String moduleName,
                             @JsonProperty("moduledescription") String moduleDescription,
                             @JsonProperty("lecturername") String lecturerName,
                             @JsonProperty("timeslot") String timeSlot, @JsonProperty("quota") String quota,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.lecturerName = lecturerName;
        this.timeSlot = timeSlot;
        this.quota = quota;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModuleCode();
        moduleName = source.getModuleName();
        moduleDescription = source.getModuleDescription();
        lecturerName = source.getLecturerName();
        timeSlot = source.getTimeSlot();
        quota = source.getQuota();
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

        if (moduleName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Module Name"));
        }
        if (!Module.isValidModuleName(moduleName)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_MODULE_NAME);
        }
        final String modelModuleName = moduleName;

        if (moduleDescription == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Module Description"));
        }
        if (!Module.isValidModuleDescription(moduleDescription)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_MODULE_DESCRIPTION);
        }
        final String modelModuleDescription = moduleDescription;

        if (lecturerName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Lecturer Name"));
        }
        if (!Module.isValidLecturerName(lecturerName)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_LECTURER_NAME);
        }
        final String modelLecturerName = lecturerName;

        if (quota == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Quota"));
        }
        if (!Module.isValidQuota(quota)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_QUOTA);
        }
        final String modelQuota = quota;

        if (timeSlot == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Time Slot"));
        }
        if (!Module.isValidTimeSlot(timeSlot)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_TIME_SLOT);
        }
        final String modelTimeSlot = timeSlot;

        final Set<Tag> modelTags = new HashSet<>(moduleTags); //students
        return new Module(modelModuleCode, modelModuleName, modelModuleDescription,
                modelLecturerName, modelTimeSlot, modelQuota, modelTags);
    }
}
