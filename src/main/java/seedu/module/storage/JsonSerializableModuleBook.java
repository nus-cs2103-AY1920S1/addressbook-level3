package seedu.module.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.module.commons.exceptions.IllegalValueException;
import seedu.module.model.ModuleBook;
import seedu.module.model.ReadOnlyModuleBook;
import seedu.module.model.module.TrackedModule;

/**
 * An Immutable ModuleBook that is serializable to JSON format.
 */
@JsonRootName(value = "modulebook")
class JsonSerializableModuleBook {

    public static final String MESSAGE_DUPLICATE_MODULES = "Module list contains duplicate Module(s).";

    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableModuleBook} with the given modules.
     */
    @JsonCreator
    public JsonSerializableModuleBook(@JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts a given {@code ReadOnlyModuleBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableModuleBook}.
     */
    public JsonSerializableModuleBook(ReadOnlyModuleBook source) {
        modules.addAll(source.getModuleList().stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this module book into the model's {@code ModuleBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ModuleBook toModelType() throws IllegalValueException {
        ModuleBook moduleBook = new ModuleBook();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            TrackedModule trackedModule = jsonAdaptedModule.toModelType();
            if (moduleBook.hasModule(trackedModule)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULES);
            }
            moduleBook.addModule(trackedModule);
        }
        return moduleBook;
    }

}
