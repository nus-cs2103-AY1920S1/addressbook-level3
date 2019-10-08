package seedu.address.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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

    /**
     * Constructs a {@code JsonAdaptedSemester} with the given {@code semesterName}.
     */
    @JsonCreator
    public JsonAdaptedSemester(@JsonProperty("semesterName") String semesterName,
                               @JsonProperty("isBlocked") boolean isBlocked,
                               @JsonProperty("reasonForBlocked") String reasonForBlocked,
                               @JsonProperty("modules") List<JsonAdaptedSkeletalModule> modules) {
        this.semesterName = semesterName;
        this.isBlocked = isBlocked;
        this.reasonForBlocked = reasonForBlocked;
        if (modules != null) {
            this.modules.addAll(modules);
        }
    }

    /**
     * Converts a given {@code Semester} into this class for Jackson use.
     */
    public JsonAdaptedSemester(Semester source) {
        semesterName = source.getSemesterName().toString();
        isBlocked = source.isBlocked();
        reasonForBlocked = source.getReasonForBlocked();

        Iterator<Module> iterator = source.getModules().iterator();
        while (iterator.hasNext()) {
            Module module = iterator.next();
            // only store module ID i.e. module code as String
            String moduleCode = module.getModuleCode().value;

            /*
            List<JsonAdaptedTag> userTags = new ArrayList<>();
            Iterator<Tag> tagIterator = module.getTags().iterator();
            while (tagIterator.hasNext()) {
                Tag tag = tagIterator.next();
                if (tag instanceof UserTag) {
                    userTags.add(new JsonAdaptedTag(tag));
                }
            }
             */

            modules.add(new JsonAdaptedSkeletalModule(moduleCode));
        }
    }

    @JsonValue
    public String getSemesterName() {
        return semesterName;
    }

    @JsonValue
    public boolean isBlocked() {
        return isBlocked;
    }

    @JsonValue
    public String getReasonForBlocked() {
        return reasonForBlocked;
    }

    /**
     * Converts this Jackson-friendly adapted semester object into the model's {@code Semester} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted semester.
     */
    public Semester toModelType() throws IllegalValueException {
        // TODO: Change this later!
        /*
        if (!Semester.isValidSemesterName(semesterName)) {
            throw new IllegalValueException(Semester.MESSAGE_CONSTRAINTS);
        }
        */
        SemesterName modelSemesterName = SemesterName.valueOf(semesterName);
        List<Module> modelModules = new ArrayList<>();
        for (JsonAdaptedSkeletalModule skeletalModule : modules) {
            modelModules.add(skeletalModule.toModelType());
        }

        return new Semester(modelSemesterName, isBlocked, reasonForBlocked, modelModules);
    }
}
