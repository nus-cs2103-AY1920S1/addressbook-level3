package seedu.address.ui.diary.gallery;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.diary.photo.Photo;
import seedu.address.ui.UiPart;
import seedu.address.ui.components.PersonCard;

/**
 * Abstraction of a diary gallery card able to display a {@link Photo}.
 */
class DiaryGalleryCard extends UiPart<AnchorPane> {

    private static final String FXML = "diary/DiaryGalleryCard.fxml";

    private static final int BOUND_WIDTH_PADDING = 10;

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
        initialiseGalleryCard();
    }

    /**
     * Binds the fit width of the {@code photoImageView} to the given {@link ReadOnlyDoubleProperty}.
     * Maintains the aspect ratio of the image in {@code photo}.
     *
     * @param galleryWidth {@link ReadOnlyDoubleProperty} to bind the fit width to.
     */
    void bindImageViewWidth(ReadOnlyDoubleProperty galleryWidth) {
        double aspectRatio = photo.getImage().getWidth() / photo.getImage().getHeight();
        photoImageView.fitWidthProperty().bind(galleryWidth.subtract(BOUND_WIDTH_PADDING * 2));
        photoImageView.fitHeightProperty()
                .bind(galleryWidth.subtract(BOUND_WIDTH_PADDING * 2).divide(aspectRatio));
    }

    /**
     * Initialises the display data of the gallery card with the {@code photo} and {@displayIndex}.
     */
    private void initialiseGalleryCard() {
        photoIndexLabel.setText(displayIndex.getOneBased() + "");
        photoDescriptionLabel.setText(photo.getDescription());
        photoDateLabel.setText(ParserDateUtil.getDisplayTime(photo.getDateTaken()));
        photoImageView.setImage(photo.getImage());
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
        return photo.equals(card.photo)
                && displayIndex.equals(card.displayIndex);
    }
}
