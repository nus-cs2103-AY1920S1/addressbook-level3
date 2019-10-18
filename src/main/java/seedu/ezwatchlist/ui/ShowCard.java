package seedu.ezwatchlist.ui;

import java.util.Comparator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ezwatchlist.model.show.Show;


/**
 * An UI component that displays information of a {@code Show}.
 */
public class ShowCard extends UiPart<Region> {

    private static final String FXML = "ShowListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Show show;

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
    private HBox actors;
    @FXML
    private CheckBox watched;
    @FXML
    private ImageView posterPlaceholder;

    public ShowCard(Show show, int displayedIndex) {
        super(FXML);
        this.show = show;
        id.setText(displayedIndex + ". ");
        name.setText(show.getName().showName);
        type.setText(show.type);
        dateOfRelease.setText(show.getDateOfRelease().value);
        description.setText(show.getDescription().fullDescription);
        runningTime.setText(Integer.toString(show.getRunningTime().value));
        posterPlaceholder.setImage(new Image(show.getPoster())); //new Image("/images/poster-placeholder.png"));
        show.getActors().stream()
                .sorted(Comparator.comparing(actor -> actor.actorName))
                .forEach(actor -> actors.getChildren().add(new Label(actor.actorName)));

        //sets the checkbox selected value to be equal to the watched value of the show
        watched.setSelected(show.isWatched().value);
        watched.selectedProperty().addListener(new NonChangeableCheckBox());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShowCard)) {
            return false;
        }

        // state check
        ShowCard card = (ShowCard) other;
        return id.getText().equals(card.id.getText())
                && show.equals(card.show);
    }

    /**
     * This class prevents the user from marking the checkbox by clicking
     *
     * @author AxxG "How to make checkbox or combobox readonly in JavaFX"
     */
    class NonChangeableCheckBox implements ChangeListener<Boolean> {
        @Override
        public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
            watched.setSelected(show.isWatched().value);
        }
    }
}
