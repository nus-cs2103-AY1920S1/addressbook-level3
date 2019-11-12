package seedu.ezwatchlist.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.TvShow;


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
    @FXML
    private ListView<Movie> movieRecommendationView;
    @FXML
    private ListView<TvShow> tvRecommendationView;
    @FXML
    private StackPane recommendationPlaceHolder;

    public StatisticsPanel(ObservableList<Show> forgotten, ObservableMap<String, Integer> favourite,
                           ObservableList<Movie> movieRecommendations, ObservableList<TvShow> tvRecommendations) {
        //forgotten
        super(FXML);
        if (forgotten.size() > 0) {
            forgottenView.setItems(forgotten);
            forgottenView.setCellFactory(listView -> new StatisticsViewCell());
        } else {
            forgottenPlaceHolder.getChildren().add(new Label("You currently do not have any forgotten entry!"));
        }
        //favourite
        if (favourite.size() > 0) {
            List<String> favouriteKeys = new ArrayList<>();
            favouriteKeys.addAll(favourite.keySet());
            Collections.sort(favouriteKeys, (key1, key2) -> favourite.get(key2) - favourite.get(key1));
            favouriteKeys.stream().forEach(genre -> favouriteGenres.getChildren()
                    .add(new Label(genre + " (" + favourite.get(genre) + " entries) ")));
        } else {
            favouriteGenres.getChildren().add(new Label("You currently have no favourite genres"));
        }
        if (movieRecommendations == null && tvRecommendations == null) {
            recommendationPlaceHolder.getChildren().add(new Label("You currently do not have any recommendation!"));
        } else {
            if (movieRecommendations != null && movieRecommendations.size() > 0) {
                movieRecommendationView.setItems(movieRecommendations);
                movieRecommendationView.setCellFactory(listView -> new MovieRecommendationViewCell());
            }
            if (tvRecommendations != null && tvRecommendations.size() > 0) {
                tvRecommendationView.setItems(tvRecommendations);
                tvRecommendationView.setCellFactory(listView -> new TvRecommendationViewCell());
            }
        }
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
                ShowCard showCard = new ShowCard(show, getIndex() + 1);
                setGraphic(showCard.getRoot());
                showCard.setWatchedListener(new NonChangeableCheckBox(showCard.getWatched(), show));
            }
        }
    }
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Movie} using a {@code RecommendationCard}.
     */
    class MovieRecommendationViewCell extends ListCell<Movie> {
        @Override
        protected void updateItem(Movie movie, boolean empty) {
            super.updateItem(movie, empty);

            if (empty || movie == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RecommendationCard(movie).getRoot());
            }
        }
    }
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Movie} using a {@code RecommendationCard}.
     */
    class TvRecommendationViewCell extends ListCell<TvShow> {
        @Override
        protected void updateItem(TvShow tv, boolean empty) {
            super.updateItem(tv, empty);

            if (empty || tv == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RecommendationCard(tv).getRoot());
            }
        }
    }

    /**
     * This class prevents the user from marking the checkbox by clicking
     *
     * @author AxxG "How to make checkbox or combobox readonly in JavaFX"
     */
    class NonChangeableCheckBox implements ChangeListener<Boolean> {
        private CheckBox checkBox;
        private Show show;

        public NonChangeableCheckBox (CheckBox checkBox, Show show) {
            this.show = show;
            this.checkBox = checkBox;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
            this.checkBox.setSelected(show.isWatched().value);
        }
    }
}
