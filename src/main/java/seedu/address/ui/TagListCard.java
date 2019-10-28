package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.tag.DefaultTag;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays a list containing some {@code Tag}.
 */
public class TagListCard extends UiPart<Region> {

    private static final String FXML = "TagListCard.fxml";

    public final Tag tag;

    @FXML
    private VBox tagListCard;

    @FXML
    private StackPane tags;

    public TagListCard(Tag tag) {
        super(FXML);
        this.tag = tag;
        Label tagLabel = new Label(tag.getTagName());
        tags.getChildren().add(tagLabel);
        if (tag.isDefault()) {
            tagLabel.setId("defaultTag");
            Label tagDescription = new Label(((DefaultTag) tag).getDescription());
            tagDescription.setWrapText(true);
            tagDescription.getStyleClass().add("cell_small_label");
            tagListCard.getChildren().add(tagDescription);
        } else {
            tagLabel.setId("userTag");
        }
    }

}
