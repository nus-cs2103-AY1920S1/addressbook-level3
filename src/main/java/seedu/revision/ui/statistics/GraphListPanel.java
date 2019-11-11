package seedu.revision.ui.statistics;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.ui.UiPart;

/**
 * Panel containing the list of line graphs.
 */
public class GraphListPanel extends UiPart<Region> {
    private static final String FXML = "GraphListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GraphListPanel.class);


    @FXML
    private ListView<ObservableList<Double>> graphListView;

    public GraphListPanel(ObservableList<ObservableList<Double>> graphList) {
        super(FXML);
        graphListView.setItems(graphList);
        graphListView.setCellFactory(listView -> new GraphListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Graph} using a {@code GraphCard}.
     */
    class GraphListViewCell extends ListCell<ObservableList<Double>> {
        @Override
        protected void updateItem(ObservableList<Double> scoreList, boolean empty) {
            super.updateItem(scoreList, empty);

            if (empty || scoreList == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GraphCard(scoreList, getIndex() + 1).getRoot());
            }
        }
    }

}

