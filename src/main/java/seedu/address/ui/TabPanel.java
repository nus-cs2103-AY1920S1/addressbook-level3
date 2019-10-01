package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;

public class TabPanel extends UiPart<Region> {
    private static final String FXML = "TabPanel.fxml";

    private PersonListPanel personListPanel;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private TabPane tabPanel;

    public TabPanel(Logic logic) {
        super(FXML);
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }
}
