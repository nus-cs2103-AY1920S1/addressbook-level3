package seedu.address.storage.cap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cap.CapLog;
import seedu.address.model.cap.ReadOnlyCapLog;
import seedu.address.model.common.Module;

/**
 * An Immutable CapLog that is serializable to JSON format.
 */
@JsonRootName(value = "capmodulelog")
class JsonSerializableCapLog {

    public static final String MESSAGE_DUPLICATE_MODULE = "Module list contains duplicate module(s).";

    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given modules.
     */
    @JsonCreator
    public JsonSerializableCapLog(@JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts a given {@code ReadOnlyCapLog} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCapLog}.
     */
    public JsonSerializableCapLog(ReadOnlyCapLog source) {
        modules.addAll(source
                .getModuleList()
                .stream()
                .map(JsonAdaptedModule::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Cap Log into the model's {@code CapLog} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CapLog toModelType() throws IllegalValueException {
        CapLog capLog = new CapLog();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (capLog.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            capLog.addModule(module);
        }
        return capLog;
    }

}
