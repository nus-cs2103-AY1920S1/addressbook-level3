package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.earnings.Earnings;

/**
 * Panel containing the list of earnings.
 */
public class EarningsListPanel extends UiPart<Region> {
    private static final String FXML = "EarningsListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EarningsListPanel.class);

    @FXML
    private ListView<Earnings> earningsListView;

    public EarningsListPanel(ObservableList<Earnings> earningsList) {
        super(FXML);
        earningsListView.setItems(earningsList);
        earningsListView.setCellFactory(listView -> new EarningsListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Earnings} using a {@code EarningsCard}.
     */
    class EarningsListViewCell extends ListCell<Earnings> {
        @Override
        protected void updateItem(Earnings earnings, boolean empty) {
            super.updateItem(earnings, empty);
            if (empty || earnings == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EarningsCard(earnings, getIndex() + 1).getRoot());
            }
        }
    }

}
