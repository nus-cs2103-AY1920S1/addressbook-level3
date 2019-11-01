package seedu.address.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;

/**
 * Jackson-friendly version of {@link Semester}.
 */
class JsonAdaptedSemester {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Semester's %s field is missing!";

    // Identity fields
    private final String semesterName;

    // Data fields
    private final boolean isBlocked;
    private final String reasonForBlocked;
    private final List<JsonAdaptedSkeletalModule> modules = new ArrayList<>();
    private final boolean isExpanded;

    /**
     * Constructs a {@code JsonAdaptedSemester} with the given {@code semesterName}.
     */
    @JsonCreator
    public JsonAdaptedSemester(@JsonProperty("semesterName") String semesterName,
                               @JsonProperty("isBlocked") boolean isBlocked,
                               @JsonProperty("reasonForBlocked") String reasonForBlocked,
                               @JsonProperty("modules") List<JsonAdaptedSkeletalModule> modules,
                               @JsonProperty("isExpanded") boolean isExpanded) {
        this.semesterName = semesterName;
        this.isBlocked = isBlocked;
        this.reasonForBlocked = reasonForBlocked;
        if (modules != null) {
            this.modules.addAll(modules);
        }
        this.isExpanded = isExpanded;
    }

    /**
     * Converts a given {@code Semester} into this class for Jackson use.
     */
    public JsonAdaptedSemester(Semester source) {
        semesterName = source.getSemesterName().toString();
        isBlocked = source.isBlocked();
        reasonForBlocked = source.getReasonForBlocked();
        isExpanded = source.isExpanded();

        Iterator<Module> iterator = source.getModules().iterator();
        while (iterator.hasNext()) {
            Module module = iterator.next();
            // only store module ID i.e. module code as String
            String moduleCode = module.getModuleCode().value;
            modules.add(new JsonAdaptedSkeletalModule(moduleCode));
        }
    }

    /**
     * Converts this Jackson-friendly adapted semester object into the model's {@code Semester} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted semester.
     */
    public Semester toModelType() throws IllegalValueException {

        if (!SemesterName.isValidSemesterName(semesterName)) {
            throw new IllegalValueException(SemesterName.MESSAGE_CONSTRAINTS);
        }

        SemesterName modelSemesterName = SemesterName.valueOf(semesterName);
        List<Module> modelModules = new ArrayList<>();
        for (JsonAdaptedSkeletalModule skeletalModule : modules) {
            modelModules.add(skeletalModule.toModelType());
        }

        return new Semester(modelSemesterName, isBlocked, reasonForBlocked, modelModules, isExpanded);
    }
}
