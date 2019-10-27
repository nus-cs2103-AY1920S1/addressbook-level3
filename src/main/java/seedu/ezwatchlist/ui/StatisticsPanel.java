package seedu.ezwatchlist.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.ezwatchlist.model.show.Show;

/**
 * An UI for the statistics panel.
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    //private ObservableList<Show> forgotten;

    @FXML
    private ListView<Show> forgottenView;

    public StatisticsPanel(ObservableList<Show> forgotten) {
        super(FXML);
        forgottenView.setItems(forgotten);
        forgottenView.setCellFactory(listView -> new ForgottenViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Show} using a {@code ShowCard}.
     */
    class ForgottenViewCell extends ListCell<Show> {
        @Override
        protected void updateItem(Show show, boolean empty) {
            super.updateItem(show, empty);

            if (empty || show == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ShowCard(show, getIndex() + 1).getRoot());
            }
        }
    }
}
