package seedu.deliverymans.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.model.customer.Customer;

/**
 * Panel containing the list of customers.
 */
public class CustomerListPanel extends UiPart<Region> {
    private static final String FXML = "CustomerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CustomerListPanel.class);

    @FXML
    private ListView<Customer> customerListView;

    public CustomerListPanel(ObservableList<Customer> customerList) {
        super(FXML);
        customerListView.setItems(customerList);
        customerListView.setCellFactory(listView -> new CustomerListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Customer} using a {@code CustomerCard}.
     */
    class CustomerListViewCell extends ListCell<Customer> {
        @Override
        protected void updateItem(Customer customemr, boolean empty) {
            super.updateItem(customemr, empty);

            if (empty || customemr == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CustomerCard(customemr, getIndex() + 1).getRoot());
            }
        }
    }

}
