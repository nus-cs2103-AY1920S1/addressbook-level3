package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.ui.panels.CustomerListPanel;
import seedu.address.ui.panels.OrderListPanel;
import seedu.address.ui.panels.PhoneListPanel;
import seedu.address.ui.panels.ScheduleListPanel;

/**
 * class containing TabPane class
 */
public class TabPanel extends UiPart<Region> {
    private static final String FXML = "TabPanel.fxml";

    //private PersonListPanel personListPanel;
    private CustomerListPanel customerListPanel;
    private PhoneListPanel phoneListPanel;
    private OrderListPanel orderlistPanel;
    private ScheduleListPanel scheduleListPanel;

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

    public TabPanel(CustomerListPanel customerListPanel,
                    PhoneListPanel phoneListPanel,
                    OrderListPanel orderlistPanel,
                    ScheduleListPanel scheduleListPanel) {
        super(FXML);
        customerListPanel = customerListPanel;
        customerListPanelPlaceholder.getChildren().add(customerListPanel.getRoot());

        phoneListPanel = phoneListPanel;
        phoneListPanelPlaceholder.getChildren().add(phoneListPanel.getRoot());

        orderlistPanel = orderlistPanel;
        orderListPanelPlaceholder.getChildren().add(orderlistPanel.getRoot());

        scheduleListPanel = scheduleListPanel;
        scheduleListPanelPlaceholder.getChildren().add(scheduleListPanel.getRoot());


        tabPanel.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
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
