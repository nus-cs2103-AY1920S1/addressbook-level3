package seedu.module.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.module.commons.exceptions.IllegalValueException;
import seedu.module.model.module.TrackedModule;

/**
 * Jackson-friendly version of {@link TrackedModule}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final String title;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleCode, @JsonProperty("title") String title,
                             @JsonProperty("description") String description) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
    }

    /**
     * Converts a given {@code TrackedModule} into this class for Jackson use.
     */
    public JsonAdaptedModule(TrackedModule source) {
        moduleCode = source.getModuleCode();
        title = source.getTitle();
        description = source.getDescription();
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Model} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public TrackedModule toModelType() throws IllegalValueException {
        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "moduleCode"));
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "title"));
        }

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "description"));
        }
        return new TrackedModule(moduleCode, title, description);
    }

}
