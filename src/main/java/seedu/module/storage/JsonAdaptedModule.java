package seedu.module.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.module.commons.exceptions.IllegalValueException;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.Deadline;
import seedu.module.model.module.TrackedModule;

/**
 * Jackson-friendly version of {@link TrackedModule}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final String deadline;

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleCode, @JsonProperty("deadline") String deadline) {
        this.moduleCode = moduleCode;
        this.deadline = deadline;
    }

    /**
     * Converts a given {@code TrackedModule} into this class for Jackson use.
     */
    public JsonAdaptedModule(TrackedModule source) {
        moduleCode = source.getModuleCode();
        deadline = source.getDeadline().getValue();
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Model} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public TrackedModule toModelType(ArchivedModuleList archivedModules) throws IllegalValueException {
        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "moduleCode"));
        }

        Optional<ArchivedModule> archivedModule = archivedModules.asUnmodifiableObservableList()
            .parallelStream()
            .filter(a -> a.getModuleCode().equals(moduleCode))
            .findFirst();

        if (!archivedModule.isPresent()) {
            throw new IllegalValueException(String.format("Archived Module %s not found", moduleCode));
        }

        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));
        }
        final Deadline modelDeadline = new Deadline(deadline);

        return new TrackedModule(archivedModule.get(), modelDeadline);
    }

}
