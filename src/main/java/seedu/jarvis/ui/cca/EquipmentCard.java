package seedu.jarvis.ui.cca;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.jarvis.model.cca.Equipment;
import seedu.jarvis.ui.UiPart;

/**
 * An UI component that displays information of a {@code Equipment}.
 */
public class EquipmentCard extends UiPart<Region> {

    private static final String FXML = "EquipmentCard.fxml";

    @FXML
    private Label equipmentName;

    private final Equipment equipment;

    public EquipmentCard(Equipment equipment) {
        super(FXML);
        this.equipment = equipment;
        equipmentName.setText(equipment.getEquipmentName());
    }
}
