package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

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

    public TabPanel(PersonListPanel personListPanel,
                    PersonListPanel personListPanel2,
                    PersonListPanel personListPanel3,
                    PersonListPanel personListPanel4) {
        super(FXML);
        personListPanel = personListPanel;
        customerListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        personListPanel2 = personListPanel2;
        phoneListPanelPlaceholder.getChildren().add(personListPanel2.getRoot());

        personListPanel3 = personListPanel3;
        orderListPanelPlaceholder.getChildren().add(personListPanel3.getRoot());

        personListPanel4 = personListPanel4;
        scheduleListPanelPlaceholder.getChildren().add(personListPanel4.getRoot());


        tabPanel.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);


        this.switchTabSchedule();
    }

    protected void switchTabCustomer() {
        this.tabPanel.getSelectionModel().select(0);
    }

    protected void switchTabPhone() {
        this.tabPanel.getSelectionModel().select(1);
    }

    protected void switchTabOrder() {
        this.tabPanel.getSelectionModel().select(2);
    }

    protected void switchTabSchedule() {
        this.tabPanel.getSelectionModel().select(3);
    }

}
