package seedu.savenus.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.model.purchase.Purchase;

/**
 * Panel containing the list of purchase.
 */
public class PurchaseListPanel extends UiPart<Region> {
    private static final String FXML = "PurchaseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PurchaseListPanel.class);

    @FXML
    private ListView<Purchase> purchaseListView;

    public PurchaseListPanel(ObservableList<Purchase> purchaseList) {
        super(FXML);
        purchaseListView.setItems(purchaseList);
        purchaseListView.setCellFactory(listView -> new PurchaseListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Purchase} using a {@code PurchaseCard}.
     */
    class PurchaseListViewCell extends ListCell<Purchase> {
        @Override
        protected void updateItem(Purchase purchase, boolean empty) {
            super.updateItem(purchase, empty);

            if (empty || purchase == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PurchaseCard(purchase).getRoot());
            }
        }
    }

}
