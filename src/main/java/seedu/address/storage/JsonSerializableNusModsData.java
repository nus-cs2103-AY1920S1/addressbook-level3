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
 * NusModsData serialized to JSON format.
 */
@JsonRootName(value = "nusmodsdata")
class JsonSerializableNusModsData {

    private final List<JsonAdaptedModule> moduleList = new ArrayList<>();
    private final JsonAdaptedAcadCalendar acadCalendar;
    private final JsonAdaptedHolidays holidays;

    @JsonCreator
    public JsonSerializableNusModsData(@JsonProperty("moduleList") List<JsonAdaptedModule> moduleList,
                                       @JsonProperty("acadCalendar") JsonAdaptedAcadCalendar acadCalendar,
                                       @JsonProperty("holidays") JsonAdaptedHolidays holidays) {
        this.moduleList.addAll(moduleList);
        this.acadCalendar = acadCalendar;
        this.holidays = holidays;
    }

    /**
     * Converts a given {@code NusModsData} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNusModsData}.
     */
    public JsonSerializableNusModsData(NusModsData source) {
        moduleList.addAll(source.getUnmodifiableModuleList().stream()
                .map(JsonAdaptedModule::new).collect(Collectors.toList()));
        acadCalendar = new JsonAdaptedAcadCalendar(source.getAcadCalendar());
        holidays = new JsonAdaptedHolidays(source.getHolidays());
    }

    /**
     * Converts the JsonSerializableNusModsData into the model's {@code NusModsData} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public NusModsData toModelType() throws IllegalValueException {
        NusModsData nusModsData = new NusModsData();

        for (JsonAdaptedModule jsonAdaptedModule : this.moduleList) {
            Module module = jsonAdaptedModule.toModelType();
            nusModsData.addModule(module);
        }

        nusModsData.setAcadCalendar(acadCalendar.toModelType());

        nusModsData.setHolidays(holidays.toModelType());

        return nusModsData;
    }

}
