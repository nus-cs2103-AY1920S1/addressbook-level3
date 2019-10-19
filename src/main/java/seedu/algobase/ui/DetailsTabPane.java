package seedu.algobase.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * Contains details about a specific model.
 */
public class DetailsTabPane extends UiPart<Region> {

    private static final String FXML = "DetailsTabPane.fxml";

    @FXML
    private AnchorPane detailsTabPanePlaceholder;

    public DetailsTabPane() {
        super(FXML);
    }
}
