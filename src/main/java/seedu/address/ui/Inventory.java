package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * Defines the display for the Inventory tab in the user interface.
 */
public class Inventory extends UiPart<Region> {

    private static final String FXML = "Inventory.fxml";

    public Inventory () {
        super(FXML);
    }
}
