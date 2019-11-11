package seedu.module.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.module.commons.exceptions.IllegalValueException;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.TrackedModule;

/**
 * Jackson-friendly version of {@link TrackedModule}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final List<JsonAdaptedDeadline> deadlines = new ArrayList<>();
    private final List<JsonAdaptedLink> links = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleCode,
                             @JsonProperty("deadlines") List<JsonAdaptedDeadline> deadlines,
                             @JsonProperty("links") List<JsonAdaptedLink> links) {
        this.moduleCode = moduleCode;
        if (deadlines != null) {
            this.deadlines.addAll(deadlines);
        }
        if (links != null) {
            this.links.addAll(links);
        }
    }

    /**
     * Converts a given {@code TrackedModule} into this class for Jackson use.
     */
    public JsonAdaptedModule(TrackedModule source) {
        moduleCode = source.getModuleCode();
        deadlines.addAll(source.getDeadlineList().stream().map(JsonAdaptedDeadline::new).collect(Collectors.toList()));
        links.addAll(source.getLink().stream()
                .map(JsonAdaptedLink::new)
                .collect(Collectors.toList()));
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

        //@@author geshuming
        Optional<ArchivedModule> archivedModule = archivedModules.asUnmodifiableObservableList()
            .parallelStream()
            .filter(a -> a.getModuleCode().equals(moduleCode))
            .findFirst();

        if (!archivedModule.isPresent()) {
            throw new IllegalValueException(String.format("Archived Module %s not found", moduleCode));
        }

        TrackedModule result = new TrackedModule(archivedModule.get());
        //@@author

        for (JsonAdaptedDeadline deadline : deadlines) {
            result.getDeadlineList().add(deadline.toModelType());
        }
        for (JsonAdaptedLink link : links) {
            result.getLink().add(link.toModelType());
        }
        return result;
    }

}
