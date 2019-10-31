package seedu.ezwatchlist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.ezwatchlist.model.show.Poster;
import seedu.ezwatchlist.model.show.Show;


public class RecommendationCard extends UiPart<Region> {
    private static final String FXML = "RecommendationCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Show show;

    @FXML
    private Label name;
    @FXML
    private ImageView poster;

    public RecommendationCard(Show show)  {
        super(FXML);
        this.show = show;
        name.setText(show.getName().showName);
        Poster poster = show.getPoster();
        Image image = poster.getImage();
        this.poster.setImage(image);
    }
}
