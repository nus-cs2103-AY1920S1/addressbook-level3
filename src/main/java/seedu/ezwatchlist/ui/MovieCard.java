package seedu.ezwatchlist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Show;

/**
 * An UI component that displays information of move, to test how it looks like in the interface.
 */
public class MovieCard extends UiPart<Region> {

    private static final String FXML = "MovieListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Movie movie;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public MovieCard(Show movie, int displayedIndex) {
        super(FXML);
        this.movie = (Movie) movie;
        id.setText(displayedIndex + ". ");
        name.setText(movie.getName().showName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MovieCard)) {
            return false;
        }

        // state check
        MovieCard card = (MovieCard) other;
        return id.getText().equals(card.id.getText())
                && movie.equals(card.movie);
    }
}
