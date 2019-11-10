package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Color;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Module}. Compared to {@link JsonAdaptedSkeletalModule}, this version
 * saves more information for each module, and is used to save modules in a study plan's "Mega HashMap" of modules.
 */
class JsonAdaptedModule {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final String color;
    private final List<JsonAdaptedTag> userTags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given {@code moduleCode} and a list of {@code userTags}.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleCode,
                             @JsonProperty("color") String color,
                             @JsonProperty("userTags") List<JsonAdaptedTag> userTags) {
        this.moduleCode = moduleCode;
        this.color = color;
        if (userTags != null) {
            this.userTags.addAll(userTags);
        }
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModuleCode().toString();
        color = source.getColor().toString();

        // add only user-defined tags
        for (Tag tag : source.getTags()) {
            if (!tag.isDefault()) {
                userTags.add(new JsonAdaptedTag(tag));
            }
        }
    }

    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object. Note that this
     * only creates an incomplete {@code Module} object, which will only be further furnished when its
     * study plan is active.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {
        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }

        if (color == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Color.class.getSimpleName()));
        }

        try {
            Color.valueOf(color);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Color.MESSAGE_CONSTRAINTS);
        }

        List<Tag> tags = new ArrayList<>();
        for (JsonAdaptedTag userTag : userTags) {
            Tag tag = userTag.toModelType();
            tags.add(tag);
        }
        return new Module(new ModuleCode(moduleCode), Color.valueOf(color), tags);
    }
}
