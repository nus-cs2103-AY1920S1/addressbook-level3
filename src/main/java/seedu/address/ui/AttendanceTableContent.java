package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * The UI component that displays a training record for a person. A training record includes a name
 *  and an tick or cross indicating attendance.
 */
public class AttendanceTableContent extends UiPart<Region> {

    private static final String FXML = "AttendanceTableContent.fxml";
    private Image tickIcon = new Image(this.getClass().getResourceAsStream("/images/tick.png"));
    private Image crossIcon = new Image(this.getClass().getResourceAsStream("/images/cross.png"));

    @FXML
    private Label name;

    @FXML
    private ImageView indicator;

    public AttendanceTableContent(String personName, boolean isPresent) {
        super(FXML);
        name.setText(personName);
        if (isPresent) {
            indicator.setImage(tickIcon);
        } else {
            indicator.setImage(crossIcon);
        }
    }
}

