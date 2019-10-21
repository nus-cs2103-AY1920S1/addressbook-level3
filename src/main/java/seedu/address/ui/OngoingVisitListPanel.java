package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.visit.Visit;

/**
 * Panel containing the list of ongoing visits (current maximum is 1).
 */
public class OngoingVisitListPanel extends UiPart<Region> {
    private static final String FXML = "OngoingVisitListPanel.fxml";

    @FXML
    private ListView<Visit> ongoingVisitListView;

    public OngoingVisitListPanel(ObservableList<Visit> visitList) {
        super(FXML);
        ongoingVisitListView.setItems(visitList);
        ongoingVisitListView.setCellFactory(listView -> new VisitListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Visit} using a {@code VisitCard}.
     */
    class VisitListViewCell extends ListCell<Visit> {
        @Override
        protected void updateItem(Visit visit, boolean empty) {
            super.updateItem(visit, empty);

            if (empty || visit == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OngoingVisitCard(visit).getRoot());
            }
        }
    }

}
