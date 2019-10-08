package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class Profile extends UiPart<Region> {

    private static final String FXML = "Profile.fxml";

    @FXML
    private ImageView displayPicture;

    @FXML
    private Label name;

    @FXML
    private Label asl;


    public Profile(Image img, String name, String asl) {
        super(FXML);
        displayPicture.setImage(img);
        this.name.setText(name);
        this.asl.setText(asl);
    }
}
