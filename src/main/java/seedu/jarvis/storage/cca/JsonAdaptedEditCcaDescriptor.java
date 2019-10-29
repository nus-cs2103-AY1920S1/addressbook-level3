package seedu.jarvis.storage.cca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.cca.EditCcaCommand.EditCcaDescriptor;
import seedu.jarvis.model.cca.CcaName;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.EquipmentList;
import seedu.jarvis.storage.JsonAdapter;

/**
 * Jackson-friendly version of {@link EditCcaDescriptor}
 */
public class JsonAdaptedEditCcaDescriptor implements JsonAdapter<EditCcaDescriptor> {
    private final String name;
    private final String type;
    private final List<JsonAdaptedEquipment> equipmentList = new ArrayList<>();
    private final JsonAdaptedCcaProgress progress;

    /**
     * Constructs a {@code JsonAdaptedEditCcaDescriptor} with the given description.
     *
     * @param name cca name, can be null.
     * @param type cca type, can be null.
     * @param equipmentList equipment list, can be null.
     * @param progress progress, can be null.
     */
    @JsonCreator
    public JsonAdaptedEditCcaDescriptor(@JsonProperty("name") String name, @JsonProperty("type") String type,
                                        @JsonProperty("equipmentList") List<JsonAdaptedEquipment> equipmentList,
                                        @JsonProperty("progress") JsonAdaptedCcaProgress progress) {
        this.name = name;
        this.type = type;
        this.equipmentList.addAll(equipmentList);
        this.progress = progress;
    }

    /**
     * Converts a given {@code EditCcaDescriptor} into this class for Jackson use.
     *
     * @param editCcaDescriptor {@code EditCcaDescriptor} to be converted for Jackson use.
     */
    public JsonAdaptedEditCcaDescriptor(EditCcaDescriptor editCcaDescriptor) {
        name = editCcaDescriptor.getCcaName().map(n -> n.fullName).orElse(null);
        type = editCcaDescriptor.getCcaType().map(t -> t.ccaType).orElse(null);
        editCcaDescriptor.getEquipmentList().ifPresent(e -> equipmentList.addAll(e.getInternalEquipmentList()
                .stream()
                .map(JsonAdaptedEquipment::new)
                .collect(Collectors.toList())));
        progress = editCcaDescriptor.getCcaProgress().map(JsonAdaptedCcaProgress::new).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted descriptor into the model's {@code EditCcaDescriptor} object.
     *
     * @return {@code EditCcaDescriptor} object.
     * @throws IllegalValueException If there were any data constraints violated in the adapted
     * {@code EditCcaDescriptor}.
     */
    @Override
    public EditCcaDescriptor toModelType() throws IllegalValueException {
        EditCcaDescriptor editCcaDescriptor = new EditCcaDescriptor();
        editCcaDescriptor.setCcaName(name != null ? new CcaName(name) : null);
        editCcaDescriptor.setCcaType(type != null ? new CcaType(type) : null);
        EquipmentList list = new EquipmentList();
        for (JsonAdaptedEquipment jsonAdaptedEquipment : equipmentList) {
            list.addEquipment(jsonAdaptedEquipment.toModelType());
        }
        editCcaDescriptor.setEquipmentList(equipmentList.isEmpty() ? null : list);
        editCcaDescriptor.setCcaProgress(progress != null ? progress.toModelType() : null);
        return editCcaDescriptor;
    }
}
