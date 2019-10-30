package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PROFILE_DESC;

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
        if (description != null) {
            if (description.equals("")) {
                this.description.setText("No profile description added. But that's okay, "
                        + "you may use the [" + PREFIX_PROFILE_DESC
                        + "] prefix to add a profile descripion anytime! :)");
                this.description.setStyle("-fx-font-family: Arial; -fx-font-style: italic");
            } else {
                this.description.setText(description);
            }
        }
    }
}
