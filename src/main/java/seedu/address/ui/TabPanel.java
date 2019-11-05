package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.ui.panels.ArchivedOrderListPanel;
import seedu.address.ui.panels.CalendarPanel;
import seedu.address.ui.panels.CustomerListPanel;
import seedu.address.ui.panels.OrderListPanel;
import seedu.address.ui.panels.PhoneListPanel;

/**
 * class containing TabPane class
 */
public class TabPanel extends UiPart<Region> {
    private static final String FXML = "TabPanel.fxml";

    @FXML
    private StackPane phoneListPanelPlaceholder;

    @FXML
    private StackPane customerListPanelPlaceholder;

    @FXML
    private StackPane orderListPanelPlaceholder;

    @FXML
    private StackPane calendarPanelPlaceHolder;

    @FXML
    private StackPane archivedOrderListPanelPlaceHolder;

    @FXML
    private TabPane tabPanel;

    public TabPanel(CustomerListPanel customerListPanel,
                    PhoneListPanel phoneListPanel,
                    OrderListPanel orderlistPanel,
                    CalendarPanel calendarPanel,
                    ArchivedOrderListPanel archivedOrderListPanel) {
        super(FXML);
        customerListPanelPlaceholder.getChildren().add(customerListPanel.getRoot());

        phoneListPanelPlaceholder.getChildren().add(phoneListPanel.getRoot());

        orderListPanelPlaceholder.getChildren().add(orderlistPanel.getRoot());

        calendarPanelPlaceHolder.getChildren().add(calendarPanel.getAgenda());

        archivedOrderListPanelPlaceHolder.getChildren().add(archivedOrderListPanel.getRoot());

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

    protected void switchTabArchivedOrder() {
        this.tabPanel.getSelectionModel().select(4);
    }

}
