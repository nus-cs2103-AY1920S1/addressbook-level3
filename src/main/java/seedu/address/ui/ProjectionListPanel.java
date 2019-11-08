package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.projection.Projection;

/**
 * Panel containing the list of Projections set.
 */
public class ProjectionListPanel extends UiPart<Region> {
    private static final String FXML = "ProjectionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TransactionListPanel.class);

    @FXML
    private ListView<Projection> projectionListView;

    ProjectionListPanel(ObservableList<Projection> projections) {
        super(FXML);
        projectionListView.setItems(projections);
        projectionListView.setCellFactory(listView -> new ProjectionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Projection} using a {@code ProjectionCard}.
     */
    static class ProjectionListViewCell extends ListCell<Projection> {
        @Override
        protected void updateItem(Projection projection, boolean empty) {
            super.updateItem(projection, empty);

            if (empty || projection == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ProjectionCard(projection, getIndex() + 1).getRoot());
            }
        }
    }

}
