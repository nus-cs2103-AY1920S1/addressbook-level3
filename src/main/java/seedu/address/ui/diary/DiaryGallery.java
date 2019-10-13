package seedu.address.ui.diary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.diary.photo.Photo;
import seedu.address.model.diary.photo.PhotoList;
import seedu.address.model.person.Person;
import seedu.address.model.trip.Trip;
import seedu.address.ui.UiPart;
import seedu.address.ui.components.PersonCard;
import seedu.address.ui.trips.TripCard;

/**
 * Abstraction of a gallery for displaying the {@code Photo}s of a {@code DiaryEntry}.
 */
public class DiaryGallery extends UiPart<VBox> {
    private static final String FXML = "diary/DiaryGallery.fxml";

    @FXML
    private ListView<Photo> photosPlaceholder;

    private PhotoList photoList;

    public DiaryGallery(PhotoList photoList) {
        super(FXML);
        this.photoList = photoList;
        fillPhotosPlaceholder();
    }

    private void fillPhotosPlaceholder() {
        photosPlaceholder.setItems(photoList.getObservablePhotoList());
        photosPlaceholder.setCellFactory(listView -> new PhotoListViewCell());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DiaryGallery)) {
            return false;
        }

        // state check
        DiaryGallery otherCard = (DiaryGallery) other;
        return photoList.equals(otherCard.photoList);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Photo} using a {@code DiaryGalleryCard}.
     */
    static class PhotoListViewCell extends ListCell<Photo> {
        @Override
        protected void updateItem(Photo photo, boolean empty) {
            super.updateItem(photo, empty);

            if (empty || photo == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DiaryGalleryCard(photo).getRoot());
            }
        }
    }
}
