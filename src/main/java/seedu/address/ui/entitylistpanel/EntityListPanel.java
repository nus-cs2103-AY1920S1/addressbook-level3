package seedu.address.ui.entitylistpanel;

import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public abstract class EntityListPanel extends UiPart<Region> {

    public EntityListPanel(String fxml) {
        super(fxml);
    }
}
