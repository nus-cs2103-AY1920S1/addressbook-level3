package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;

public class TabPanel extends UiPart<Region> {
    private static final String FXML = "TabPanel.fxml";

    @FXML
    private TabPane tabPanel;

    public TabPanel() {
        super(FXML);
    }
}
