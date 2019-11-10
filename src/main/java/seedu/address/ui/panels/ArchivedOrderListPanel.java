package seedu.address.ui.panels;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.order.Order;
import seedu.address.ui.UiPart;
import seedu.address.ui.cards.ArchivedOrderCard;

/**
 * Panel containing the list of orders.
 */
public class ArchivedOrderListPanel extends UiPart<Region> {
    private static final String FXML = "ArchivedOrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ArchivedOrderListPanel.class);

    @FXML
    private ListView<Order> archivedOrderListView;

    public ArchivedOrderListPanel(ObservableList<Order> archivedOrderList) {
        super(FXML);
        archivedOrderListView.setItems(archivedOrderList);
        archivedOrderListView.setCellFactory(listView -> new ArchivedOrderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Order} using a {@code ArchivedOrderCard}.
     */
    class ArchivedOrderListViewCell extends ListCell<Order> {
        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ArchivedOrderCard(order, getIndex() + 1).getRoot());
            }
        }
    }

}
