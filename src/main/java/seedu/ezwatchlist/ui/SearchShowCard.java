package seedu.ezwatchlist.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.ezwatchlist.model.show.Poster;
import seedu.ezwatchlist.model.show.Show;

/**
 * An UI component that displays information of a {@code Show}.
 */
public class SearchShowCard extends UiPart<Region> {

    private static final String FXML = "SearchShowListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Show show;
    private int displayedIndex;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label type;
    @FXML
    private Label dateOfRelease;
    @FXML
    private Label description;
    @FXML
    private Label runningTime;
    @FXML
    private Label lastWatched;
    @FXML
    private HBox actors;
    @FXML
    private ImageView poster;
    @FXML
    private HBox genres;
    @FXML
    private VBox information;

    private MainWindow mainWindow;

    public SearchShowCard(Show show, int displayedIndex) {
        super(FXML);
        this.show = show;
        this.displayedIndex = displayedIndex;
        id.setText(displayedIndex + ". ");
        name.setText(show.getName().showName);
        type.setText("Type: " + show.getType());
        dateOfRelease.setText("Date of Release: " + show.getDateOfRelease().value);
        description.setText("Description: " + show.getDescription().fullDescription);
        runningTime.setText("Running Time: " + Integer.toString(show.getRunningTime().value) + " minutes");
        Poster poster = show.getPoster();
        Image image = poster.getImage();
        this.poster.setImage(image);

        actors.getChildren().add(new Label("Actors: "));
        show.getActors().stream().limit(4)
                .sorted(Comparator.comparing(actor -> actor.actorName))
                .forEach(actor -> actors.getChildren().add(new Label(actor.actorName)));
        actors.getChildren().stream().forEach(node -> node.getStyleClass().add("cell_small_label"));

        show.getGenres().stream()
                .forEach(genre -> genres.getChildren().add(new Label(genre.getGenreName())));

        setLastWatched();
    }

    public int getDisplayedIndex() {
        return displayedIndex;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchShowCard)) {
            return false;
        }

        // state check
        SearchShowCard card = (SearchShowCard) other;
        return id.getText().equals(card.id.getText())
                && show.equals(card.show);
    }

    private void setLastWatched() {
        if (show.getType().equals("Tv Show")) {
            if (show.getLastWatchedSeasonNum() == 0) {
                lastWatched.setText("");
            } else {
                lastWatched.setText("Last Watched: \nSeason " + show.getLastWatchedSeasonNum()
                        + " Episode " + show.getLastWatchedSeasonEpisode());
            }
        } else {
            lastWatched.setText("");
        }
    }

}
