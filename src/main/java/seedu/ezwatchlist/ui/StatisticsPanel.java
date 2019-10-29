package seedu.ezwatchlist.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.ezwatchlist.model.show.Show;

/**
 * An UI for the statistics panel.
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";

    @FXML
    private ListView<Show> forgottenView;
    @FXML
    private HBox favouriteGenres;
    @FXML
    private VBox forgottenPlaceHolder;

    public StatisticsPanel(ObservableList<Show> forgotten, ObservableList<String> favourite) {
        super(FXML);
        if (forgotten.size() > 0) {
            forgottenView.setItems(forgotten);
            forgottenView.setCellFactory(listView -> new StatisticsViewCell());
        } else {
            forgottenPlaceHolder.getChildren().add(new Label("You currently do not have any forgotten entry!"));
        }


        favourite.stream().forEach(genre -> favouriteGenres.getChildren().add(new Label(genre)));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Show} using a {@code ShowCard}.
     */
    class StatisticsViewCell extends ListCell<Show> {
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
