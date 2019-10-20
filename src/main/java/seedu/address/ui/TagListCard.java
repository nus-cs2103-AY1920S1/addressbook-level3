package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays a list containing some {@code Tag}.
 */
public class TagListCard extends UiPart<Region> {

    private static final String FXML = "TagListCard.fxml";

    public final Tag tag;

    @FXML
    private StackPane tags;

    @FXML
    private Label tagDescription;

    public TagListCard(Tag tag) {
        super(FXML);
        this.tag = tag;
        tags.getChildren().add(new Label(tag.getTagName()));
        if (tag.isDefault()) {
            tagDescription.setText("default tag description will be here");
        } else {
            tagDescription.setText("");
        }
    }

}
