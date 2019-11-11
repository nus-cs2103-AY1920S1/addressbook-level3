package seedu.jarvis.storage.cca;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.cca.Equipment;
import seedu.jarvis.storage.JsonAdapter;

/**
 * Jackson-friendly version of {@link Equipment}.
 */
public class JsonAdaptedEquipment implements JsonAdapter<Equipment> {

    private final String equipmentName;

    /**
     * Constructs a {@code JsonAdaptedEquipment} with the given {@code Equipment}.
     *
     * @param equipmentName Name of the equipment.
     */
    @JsonCreator
    public JsonAdaptedEquipment(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    /**
     * Converts a given {@code Equipment} into this class for Jackson use.
     *
     * @param equipment {@code Equipment} to be converted to {@code JsonAdaptedEquipment}.
     */
    public JsonAdaptedEquipment(Equipment equipment) {
        equipmentName = equipment.equipmentName;
    }

    @JsonValue
    public String getEquipmentName() {
        return equipmentName;
    }

    /**
     * Converts this Jackson-friendly adapted {@code Equipment} object into the model's {@code Equipment} object.
     *
     * @return {@code Equipment} of the Jackson-friendly adapted {@code Equipment}.
     * @throws IllegalValueException If there were any data constraints violated in the adapted {@code Equipment}.
     */
    @Override
    public Equipment toModelType() throws IllegalValueException {
        if (!Equipment.isValidEquipmentName(equipmentName)) {
            throw new IllegalValueException(Equipment.MESSAGE_CONSTRAINTS);
        }
        return new Equipment(equipmentName);
    }
}
