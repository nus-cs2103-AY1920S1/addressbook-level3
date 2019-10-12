package seedu.module.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.module.model.module.ArchivedModule;

/**
 * Jackson-friendly version of {@link ArchivedModule}. This class serves only as a reader.
 */
class JsonAdaptedArchivedModule {

    private final String moduleCode;
    private final String title;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedArchivedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedArchivedModule(@JsonProperty("moduleCode") String moduleCode, @JsonProperty("title") String title,
            @JsonProperty("description") String description) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code ArchivedModel} object.
     */
    public ArchivedModule toModelType() {
        return new ArchivedModule(moduleCode, title, description);
    }

}
