package seedu.ichifund.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.ichifund.commons.core.LogsCenter;
import seedu.ichifund.model.analytics.Data;

/**
 * Panel containing data from a report.
 */
public class DataListPanel extends UiPart<Region> {
    private static final String FXML = "DataListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DataListPanel.class);

    @FXML
    private ListView<Data> dataListView;

    public DataListPanel(ObservableList<Data> dataList) {
        super(FXML);
        dataListView.setItems(dataList);
        dataListView.setCellFactory(listView -> new DataListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Data} using a {@code AnalyticsCard}.
     */
    class DataListViewCell extends ListCell<Data> {
        @Override
        protected void updateItem(Data data, boolean empty) {
            super.updateItem(data, empty);

            if (empty || data == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DataCard(data, getIndex() + 1).getRoot());
            }
        }
    }
}
