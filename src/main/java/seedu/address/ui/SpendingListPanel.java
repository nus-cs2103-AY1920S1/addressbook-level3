package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.spending.Spending;

/**
 * Panel containing the list of spendings.
 */
public class SpendingListPanel extends UiPart<Region> {
    private static final String FXML = "SpendingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SpendingListPanel.class);

    @FXML
    private ListView<Spending> spendingListView;

    public SpendingListPanel(ObservableList<Spending> spendingList) {
        super(FXML);
        spendingListView.setItems(spendingList);
        spendingListView.setCellFactory(listView -> new SpendingListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Spending} using a {@code SpendingCard}.
     */
    class SpendingListViewCell extends ListCell<Spending> {
        @Override
        protected void updateItem(Spending spending, boolean empty) {
            super.updateItem(spending, empty);

            if (empty || spending == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SpendingCard(spending, getIndex() + 1).getRoot());
            }
        }
    }

}
