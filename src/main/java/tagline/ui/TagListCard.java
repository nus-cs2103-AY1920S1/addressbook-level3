package tagline.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import tagline.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Tag}.
 */
public class TagListCard extends UiPart<Region> {
    private static final String FXML = "TagListCard.fxml";

    public final Tag tag;

    @FXML
    private HBox noteListCardPane;
    @FXML
    private Label tagName;

    public TagListCard(Tag tag) {
        super(FXML);
        this.tag = tag;
        tagName.setText(tag.toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagListCard)) {
            return false;
        }

        // state check
        TagListCard card = (TagListCard) other;
        return tag.equals(card.tag);
    }
}
