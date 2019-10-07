package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.NusModsData;
import seedu.address.model.module.Module;

/**
 * A ModuleList serialized to JSON format.
 */
@JsonRootName(value = "nusmodsdata")
class JsonSerializableNusModsData {

    private final List<JsonAdaptedModule> moduleList = new ArrayList<>();

    @JsonCreator
    public JsonSerializableNusModsData(@JsonProperty("moduleList") List<JsonAdaptedModule> moduleList) {
        this.moduleList.addAll(moduleList);
    }

    /**
     * Converts a given {@code NusModsData} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNusModsData}.
     */
    public JsonSerializableNusModsData(NusModsData source) {
        moduleList.addAll(source.getUnmodifiableModuleList().stream()
                .map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this ModuleList into the model's {@code ModuleList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public NusModsData toModelType() throws IllegalValueException {
        NusModsData nusModsData = new NusModsData();

        for (JsonAdaptedModule jsonAdaptedModule : this.moduleList) {
            Module module = jsonAdaptedModule.toModelType();
            nusModsData.addModule(module);
        }

        return nusModsData;
    }

}
