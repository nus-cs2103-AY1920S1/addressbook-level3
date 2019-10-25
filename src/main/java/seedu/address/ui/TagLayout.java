package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Represents a tag for an athlete.
 */
public class TagLayout extends UiPart<Region> {

    private static final String FXML = "TagLayout.fxml";

    @FXML
    private Label tagName;

    @FXML
    private ImageView tagIcon;

    public TagLayout(String labelText) {
        super(FXML);
        tagName.setText(labelText);
        tagIcon.setImage(new Image("/images/tag_icon.png"));
    }
}
