package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * A ui for the user's profile header consisting of the user's name and customisable description.
 */
public class Profile extends UiPart<Region> {

    private static final String FXML = "Profile.fxml";

    @FXML
    private ImageView displayPicture;

    @FXML
    private Label name;

    @FXML
    private Label description;

    public Profile(Image displayPicture, String name, String description) {
        super(FXML);
        this.displayPicture.setImage(displayPicture);
        this.name.setText(name);
        this.description.setText(description);
    }
}
