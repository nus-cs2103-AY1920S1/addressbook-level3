package seedu.address.ui.diary;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import seedu.address.model.diary.photo.Photo;
import seedu.address.ui.UiPart;

/**
 * {@link ImageView} for displaying a inline diary entry image.
 * It supports custom behaviours, such as a slight random rotation.
 */
class DiaryEntryPhotoCard extends UiPart<StackPane> {

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
     * @param width {@link DoubleBinding} to bind the fit width to.
     */
    void bindImageViewDimensions(DoubleBinding width) {
        double aspectRatio = photo.getImage().getWidth() / photo.getImage().getHeight();
        photoImageView.fitWidthProperty().bind(width);
        photoImageView.fitHeightProperty()
                .bind(width.subtract(BOUND_HEIGHT_OFFSET).divide(aspectRatio));
    }
}
