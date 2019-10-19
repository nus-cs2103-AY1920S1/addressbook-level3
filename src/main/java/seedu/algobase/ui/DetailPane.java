package seedu.algobase.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * Contains details about a specific model.
 */
public class DetailPane extends UiPart<Region> {

    private static final String FXML = "DetailPane.fxml";

    @FXML
    private AnchorPane detailPanePlaceholder;

    public DetailPane() {
        super(FXML);
    }
}
