package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;

/**
 * Jackson-friendly version of {@link Module}. This version is specifically used for storing modules under semesters
 * in a study plan. These modules will be stored in JSON files when a study plan is adapted to JSON format.
 * In contrast, {@link JsonAdaptedModule} is used to store more comprehensive module information in the
 * {@code UniqueModuleList} field under {@code StudyPlan}.
 */
class JsonAdaptedSkeletalModule {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode; // the only info we need to store

    /**
     * Constructs a {@code JsonAdaptedSkeletalModule} with the given {@code moduleCode}.
     */
    @JsonCreator
    public JsonAdaptedSkeletalModule(@JsonProperty("moduleCode") String moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedSkeletalModule(Module source) {
        moduleCode = source.getModuleCode().toString();
    }

    @JsonValue
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * Converts this Jackson-friendly adapted skeletal module object into the model's {@code Module} object.
     *  Note that this only creates a skeletal {@code Module} object, which will only be activated when
     *  its study plan is active.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted semester.
     */
    public Module toModelType() throws IllegalValueException {
        // TODO: Change this later to check the validity of module names

        //        if (!Module.isValidModuleCode(moduleCode)) {
        //            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS);
        //        }

        return new Module(new ModuleCode(moduleCode));
    }
}
