package seedu.address.ui.diary;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import seedu.address.commons.core.index.Index;
import seedu.address.model.diary.photo.Photo;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;
import seedu.address.ui.components.PersonCard;

/**
 *
 */
public class DiaryGalleryCard extends UiPart<AnchorPane> {

    private static final String FXML = "components/DiaryGalleryCard.fxml";

    private final Photo photo;
    private final Index displayIndex;

    @FXML
    private Label photoIndexLabel;
    @FXML
    private Label photoDescriptionLabel;
    @FXML
    private Label photoDateLabel;
    @FXML
    private ImageView photoImageView;

    public DiaryGalleryCard(Photo photo, Index displayIndex) {
        super(FXML);
        this.photo = photo;
        this.displayIndex = displayIndex;
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
