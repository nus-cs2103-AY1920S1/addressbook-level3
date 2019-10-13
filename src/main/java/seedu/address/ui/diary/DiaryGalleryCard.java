package seedu.address.ui.diary;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import seedu.address.model.diary.photo.Photo;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;
import seedu.address.ui.components.PersonCard;

/**
 *
 */
public class DiaryGalleryCard extends UiPart<AnchorPane> {

    private static final String FXML = "components/DiaryGalleryCard.fxml";

    public final Photo photo;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public DiaryGalleryCard(Photo photo) {
        super(FXML);
        this.photo = photo;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        DiaryGalleryCard card = (DiaryGalleryCard) other;
        return photo.equals(card.photo);
    }
}
