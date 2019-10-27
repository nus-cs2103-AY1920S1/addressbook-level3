package seedu.address.ui.diary.entry;

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

    private static final double MIN_ROTATION = -8.0;
    private static final double MAX_ROTATION = 8.0;

    private final Photo photo;

    @FXML
    private ImageView photoImageView;

    /**
     * Constructs an instance of {@link DiaryEntryPhotoCard}.
     * The random rotation of the image is calculated here, along with the offset for the height
     * required due to the rotation.
     *
     * @param photo The {@link Photo} instance to use.
     */
    DiaryEntryPhotoCard(Photo photo) {
        super(FXML);
        this.photo = photo;
        photoImageView.setImage(photo.getImage());

        double randomRotation = Math.random() * (MAX_ROTATION - MIN_ROTATION) + MIN_ROTATION;
        getRoot().setRotate(randomRotation);
    }

    /**
     * Binds the dimensions of the {@code photoImageView} to the given {@link ReadOnlyDoubleProperty}.
     * Prioritizes maintaining the aspect ratio of the image in {@code photo}.
     *
     * @param width {@link DoubleBinding} to bind the fit width to.
     */
    void bindImageViewDimensions(DoubleBinding width) {
        double aspectRatio = photo.getImage().getWidth() / photo.getImage().getHeight();

        photoImageView.setPreserveRatio(false);
        photoImageView.fitWidthProperty().bind(width);
        photoImageView.fitHeightProperty().bind(width.divide(aspectRatio));
        //height.bind(heightFromWidth);
    }
}
