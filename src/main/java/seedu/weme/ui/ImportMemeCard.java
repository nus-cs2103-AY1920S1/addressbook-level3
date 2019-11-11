package seedu.weme.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.weme.model.meme.Meme;

/**
 * An UI component that displays information of a {@code Meme}.
 */
public class ImportMemeCard extends UiPart<Region> {

    private static final String FXML = "ImportMemeGridCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Meme meme;

    @FXML
    private HBox cardPane;
    @FXML
    private ImageView display;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;

    public ImportMemeCard(Meme meme, int displayedIndex) {
        super(FXML);
        this.meme = meme;
        id.setText(displayedIndex + "");
        display.setImage(new Image(meme.getImagePath().toUrl().toString(), 200, 200, true, true, true));
        description.setText(meme.getDescription().value);
        meme.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Updates the card content except for the meme image.
     * @param meme the meme this card is for
     * @param newIndex the new index of the this card
     */
    public void update(Meme meme, int newIndex) {
        id.setText(newIndex + "");
        description.setText(meme.getDescription().value);
        tags.getChildren().clear();
        meme.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ImportMemeCard)) {
            return false;
        }

        // state check
        ImportMemeCard card = (ImportMemeCard) other;
        return id.getText().equals(card.id.getText())
                && meme.equals(card.meme);
    }
}
