package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.show.Show;

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
    private Label dateOfRelease;
    @FXML
    private Label isWatched;
    @FXML
    private Label description;
    @FXML
    private Label runningTime;
    @FXML
    private FlowPane actors;

    public ShowCard(Show show, int displayedIndex) {
        super(FXML);
        this.show = show;
        id.setText(displayedIndex + ". ");
        name.setText(show.getName().showName);
        dateOfRelease.setText(show.getDateOfRelease().value);
        isWatched.setText(Boolean.toString(show.isWatched().value));
        description.setText(show.getDescription().fullDescription);
        runningTime.setText(Integer.toString(show.getRunningTime().value));
        show.getActors().stream()
                .sorted(Comparator.comparing(actor -> actor.actorName))
                .forEach(actor -> actors.getChildren().add(new Label(actor.actorName)));
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
}
