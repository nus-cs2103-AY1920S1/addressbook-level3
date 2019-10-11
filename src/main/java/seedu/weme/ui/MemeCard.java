package seedu.weme.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.weme.model.meme.Meme;

/**
 * An UI component that displays information of a {@code Meme}.
 */
public class MemeCard extends UiPart<Region> {

    private static final String FXML = "MemeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on MemeBook level 4</a>
     */

    public final Meme meme;

    @FXML
    private HBox cardPane;
    @FXML
    private Label path;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;

    public MemeCard(Meme meme, int displayedIndex) {
        super(FXML);
        this.meme = meme;
        id.setText(displayedIndex + ". ");
        path.setText(meme.getFilePath().toString()); // for now the MemeCard displays the path.
        description.setText(meme.getDescription().value);
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
        if (!(other instanceof MemeCard)) {
            return false;
        }

        // state check
        MemeCard card = (MemeCard) other;
        return id.getText().equals(card.id.getText())
                && meme.equals(card.meme);
    }
}
