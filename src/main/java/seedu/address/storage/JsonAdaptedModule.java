package seedu.address.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ModulePlanner;
import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UserTag;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final List<JsonAdaptedTag> userTags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given {@code moduleCode} and a list of {@code userTags}.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleCode,
                             @JsonProperty("userTags") List<JsonAdaptedTag> userTags) {
        this.moduleCode = moduleCode;
        if (userTags != null) {
            this.userTags.addAll(userTags);
        }
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModuleCode().toString();

        // add only user-defined tags
        Iterator<Tag> tagIterator = source.getTags().iterator();
        while (tagIterator.hasNext()) {
            Tag tag = tagIterator.next();
            if (tag instanceof UserTag) {
                userTags.add(new JsonAdaptedTag(tag));
            }
        }
    }

    @JsonValue
    public String getModuleCode() {
        return moduleCode;
    }

    @JsonValue
    public List<JsonAdaptedTag> getUserTags() {
        return userTags;
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object. Note that this
     * only creates a skeletal {@code Module} object, which will only be activated when its study plan is active.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted semester.
     */
    public Tag toModelType() throws IllegalValueException {
        // TODO: Change this later!

        //        if (!UserTag.isValidTagName(semesterName)) {
        //            throw new IllegalValueException(UserTag.MESSAGE_CONSTRAINTS);
        //        }
        return new UserTag(moduleCode);
    }
}
