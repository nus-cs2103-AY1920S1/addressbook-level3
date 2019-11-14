package seedu.address.ui.diary.gallery;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import seedu.address.commons.core.index.Index;
import seedu.address.model.diary.photo.DiaryPhoto;
import seedu.address.model.diary.photo.PhotoList;
import seedu.address.ui.UiPart;

/**
 * Abstraction of a gallery for displaying the {@code Photo}s of a {@code DiaryEntry}.
 */
public class DiaryGallery extends UiPart<VBox> {
    private static final String FXML = "diary/DiaryGallery.fxml";

    @FXML
    private ListView<DiaryPhoto> photosPlaceholder;

    private PhotoList photoList;

    public DiaryGallery() {
        super(FXML);
        this.photoList = new PhotoList();
        fillPhotosPlaceholder();
    }

    public void setPhotoList(PhotoList photoList) {
        this.photoList = photoList;
        photosPlaceholder.setItems(photoList.getObservablePhotoList());
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
    class PhotoListViewCell extends ListCell<DiaryPhoto> {
        @Override
        protected void updateItem(DiaryPhoto photo, boolean empty) {
            super.updateItem(photo, empty);

            if (empty || photo == null) {
                setGraphic(null);
                setText(null);
            } else {
                DiaryGalleryCard diaryGalleryCard = new DiaryGalleryCard(photo, Index.fromZeroBased(this.getIndex()));
                diaryGalleryCard.bindImageViewWidth(DiaryGallery.this.photosPlaceholder.widthProperty());

                setGraphic(diaryGalleryCard.getRoot());
            }
        }
    }
}
