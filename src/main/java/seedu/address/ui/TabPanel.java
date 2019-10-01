package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;

/**
 * class containing TabPane class
 */
public class TabPanel extends UiPart<Region> {
    private static final String FXML = "TabPanel.fxml";

    private PersonListPanel personListPanel;
    private PersonListPanel personListPanel2;
    private PersonListPanel personListPanel3;
    private PersonListPanel personListPanel4;

    @FXML
    private StackPane phoneListPanelPlaceholder;

    @FXML
    private StackPane customerListPanelPlaceholder;

    @FXML
    private StackPane orderListPanelPlaceholder;
    @FXML
    private StackPane scheduleListPanelPlaceholder;

    @FXML
    private TabPane tabPanel;

    public TabPanel(Logic logic) {
        super(FXML);
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        customerListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        personListPanel2 = new PersonListPanel(logic.getFilteredPersonList());
        phoneListPanelPlaceholder.getChildren().add(personListPanel2.getRoot());

        personListPanel3 = new PersonListPanel(logic.getFilteredPersonList());
        orderListPanelPlaceholder.getChildren().add(personListPanel3.getRoot());

        personListPanel4 = new PersonListPanel(logic.getFilteredPersonList());
        scheduleListPanelPlaceholder.getChildren().add(personListPanel4.getRoot());


        tabPanel.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

}
