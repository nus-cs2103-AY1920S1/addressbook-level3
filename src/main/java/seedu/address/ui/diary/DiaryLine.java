package seedu.address.ui.diary;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import seedu.address.model.diary.photo.Photo;
import seedu.address.ui.UiPart;

/**
 * Custom JavaFX component controller representing a single line of items to be displayed in the diary.
 * These items can be a {@link Photo}, or a simple string.
 */
class DiaryLine extends UiPart<HBox> {

    private static final String FXML = "diary/DiaryLine.fxml";

    @FXML
    private Label lineTextLabel;

    @FXML
    private HBox photoCardsDisplay;

    DiaryLine(String text) {
        super(FXML);
        lineTextLabel.setText(text);
        getRoot().getChildren().remove(photoCardsDisplay);
    }

    DiaryLine(Collection<Photo> photos) {
        this("");
        photoCardsDisplay.getChildren().addAll(
                photos.stream()
                        .map(photo -> {
                            DiaryEntryPhotoCard diaryEntryPhotoCard = new DiaryEntryPhotoCard(photo);
                            diaryEntryPhotoCard.bindImageViewHeight(photoCardsDisplay.heightProperty());

                            return diaryEntryPhotoCard.getRoot();
                        })
                        .collect(Collectors.toList()));
        lineTextLabel.setGraphic(photoCardsDisplay);
    }

    DiaryLine(String text, Photo photo, boolean placeOnLeft) {
        this(text);
        requireNonNull(photo);
        DiaryEntryPhotoCard diaryEntryPhotoCard = new DiaryEntryPhotoCard(photo);
        diaryEntryPhotoCard.bindImageViewHeight(photoCardsDisplay.heightProperty());
        photoCardsDisplay.getChildren().add(diaryEntryPhotoCard.getRoot());
        lineTextLabel.setGraphic(photoCardsDisplay);
        if (placeOnLeft) {
            lineTextLabel.setContentDisplay(ContentDisplay.LEFT);
        }
    }
}
