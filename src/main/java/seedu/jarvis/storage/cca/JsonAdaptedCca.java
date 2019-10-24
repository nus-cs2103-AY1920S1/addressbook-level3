package seedu.jarvis.storage.cca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaName;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.EquipmentList;

/**
 * Jackson-friendly version of {@link Cca}.
 */
public class JsonAdaptedCca {
    private final String name;
    private final String ccaType;
    private final List<JsonAdaptedEquipment> equipmentList = new ArrayList<>();
    private final JsonAdaptedCcaProgress ccaProgress;

    /**
     * Constructs a {@code JsonAdaptedCca} with the given {@code Cca}.
     */
    @JsonCreator
    public JsonAdaptedCca(@JsonProperty("name") String name, @JsonProperty("ccaType") String ccaType,
                          @JsonProperty("equipmentList") List<JsonAdaptedEquipment> equipmentList,
                          @JsonProperty("ccaProgress") JsonAdaptedCcaProgress ccaProgress) {
        this.name = name;
        this.ccaType = ccaType;
        this.equipmentList.addAll(equipmentList);
        this.ccaProgress = ccaProgress;
    }

    public JsonAdaptedCca(Cca cca) {
        name = cca.getName().fullName;
        ccaType = cca.getCcaType().ccaType;
        equipmentList.addAll(cca.getEquipmentList()
                .getInternalEquipmentList()
                .stream()
                .map(JsonAdaptedEquipment::new)
                .collect(Collectors.toList()));
        ccaProgress = new JsonAdaptedCcaProgress(cca.getCcaProgress());
    }

    /**
     * Converts this Jackson-friendly adapted {@code Cca} object into the model's {@code Cca} object.
     *
     * @return {@code Cca} of the Jackson-friendly adapted {@code Cca}.
     * @throws IllegalValueException If there were any data constraints violated in the adapted {@code Cca}.
     */
    public Cca toModelType() throws IllegalValueException {
        EquipmentList listOfEquipments = new EquipmentList();
        for (JsonAdaptedEquipment jsonAdaptedEquipment : equipmentList) {
            listOfEquipments.addEquipment(jsonAdaptedEquipment.toModelType());
        }
        return new Cca(new CcaName(name), new CcaType(ccaType), listOfEquipments, ccaProgress.toModelType());
    }
}
