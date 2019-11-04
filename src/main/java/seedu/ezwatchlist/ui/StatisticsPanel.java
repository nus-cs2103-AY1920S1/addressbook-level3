package seedu.ezwatchlist.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
    private StackPane forgottenPlaceHolder;

    public StatisticsPanel(ObservableList<Show> forgotten, ObservableMap<String, Integer> favourite) {
        super(FXML);
        if (forgotten.size() > 0) {
            forgottenView.setItems(forgotten);
            forgottenView.setCellFactory(listView -> new StatisticsViewCell());
        } else {
            forgottenPlaceHolder.getChildren().add(new Label("You currently do not have any forgotten entry!"));
        }

        List<String> favouriteKeys = new ArrayList<>();
        favouriteKeys.addAll(favourite.keySet());
        Collections.sort(favouriteKeys, (key1, key2) -> favourite.get(key2) - favourite.get(key1));
        favouriteKeys.stream().forEach(genre -> favouriteGenres.getChildren()
                .add(new Label(genre + " (" + favourite.get(genre) + " entries) ")));
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
