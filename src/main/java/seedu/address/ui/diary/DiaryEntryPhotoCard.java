package seedu.address.ui.diary;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import seedu.address.model.diary.photo.Photo;
import seedu.address.ui.UiPart;

/**
 * {@link ImageView} for displaying a inline diary entry image.
 * It supports custom behaviours, such as a slight random rotation.
 */
class DiaryEntryPhotoCard extends UiPart<AnchorPane> {

    private static final String FXML = "diary/DiaryEntryPhotoCard.fxml";

    private static final int BOUND_HEIGHT_OFFSET = 30;
    private static final double MIN_ROTATION = -10.0;
    private static final double MAX_ROTATION = 10.0;

    private final Photo photo;

    @FXML
    private ImageView photoImageView;

    DiaryEntryPhotoCard(Photo photo) {
        super(FXML);
        this.photo = photo;
        photoImageView.setImage(photo.getImage());

        getRoot().setRotate(Math.random() * (MAX_ROTATION - MIN_ROTATION) + MIN_ROTATION);
    }

    /**
     * Binds the fit height of the {@code photoImageView} to the given {@link ReadOnlyDoubleProperty}.
     * Maintains the aspect ratio of the image in {@code photo}.
     *
     * @param galleryHeight {@link ReadOnlyDoubleProperty} to bind the fit height to.
     */
    void bindImageViewHeight(ReadOnlyDoubleProperty galleryHeight) {
        double aspectRatio = photo.getImage().getWidth() / photo.getImage().getHeight();
        photoImageView.fitHeightProperty().addListener(((observable, oldValue, newValue) -> {
            photoImageView.setFitWidth(newValue.doubleValue() * aspectRatio);
        }));
        getRoot().prefHeightProperty().bind(galleryHeight.subtract(BOUND_HEIGHT_OFFSET));
        photoImageView.fitHeightProperty().bind(getRoot().prefHeightProperty().add(BOUND_HEIGHT_OFFSET / 4));
    }
}
