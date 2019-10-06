package seedu.address.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UserTag;

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
    private final boolean isExpanded;
    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedSemester} with the given {@code semesterName}.
     */
    @JsonCreator
    public JsonAdaptedSemester(@JsonProperty("semesterName") String semesterName,
                               @JsonProperty("isBlocked") boolean isBlocked,
                               @JsonProperty("reasonForBlocked") String reasonForBlocked,
                               @JsonProperty("isExpanded") boolean isExpanded,
                               @JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.semesterName = semesterName;
        this.isBlocked = isBlocked;
        this.reasonForBlocked = reasonForBlocked;
        this.isExpanded = isExpanded;
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
        isExpanded = source.isExpanded();

        Iterator<Module> iterator = source.getModules().iterator();
        while (iterator.hasNext()) {
            Module module = iterator.next();
            // only store: 1. module ID i.e. module code as String; 2. user tag IDs as Strings
            String moduleCode = module.getModuleCode().value;
            List<JsonAdaptedTag> userTags = new ArrayList<>();
            Iterator<Tag> tagIterator = module.getTags().iterator();
            while (tagIterator.hasNext()) {
                Tag tag = tagIterator.next();
                if (tag instanceof UserTag) {
                    userTags.add(new JsonAdaptedTag(tag));
                }
            }

            modules.add(new JsonAdaptedModule(moduleCode, userTags));
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

    @JsonValue
    public boolean isExpanded() {
        return isExpanded;
    }

    /**
     * Converts this Jackson-friendly adapted semester object into the model's {@code Semester} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted semester.
     */
    public Semester toModelType() throws IllegalValueException {
        // TODO: Change this later!
        if (!UserTag.isValidTagName(semesterName)) {
            throw new IllegalValueException(UserTag.MESSAGE_CONSTRAINTS);
        }
        return new Semester(SemesterName.Y1S1);
    }

}
