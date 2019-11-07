package seedu.address.ui.diary.entry;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import seedu.address.model.diary.photo.Photo;
import seedu.address.ui.UiPart;

/**
 * Custom JavaFX component controller representing a single line of items to be displayed in the diary.
 * These items can be a graphic such as {@link Photo}, a simple text string, or both.
 * It is backed using a JavaFX {@link GridPane} component, which allows adjusting the proportions the
 * text or graphic.
 * By default, the {@link GridPane} constraints are set to accommodate only a label without the graphic.
 */
class DiaryLine extends UiPart<GridPane> {

    private static final String FXML = "diary/DiaryLine.fxml";

    /** The width percentage of the containing {@link GridPane} the column of the graphic should occupy. */
    private static final double GRAPHIC_WIDTH_PERCENTAGE = 30.0;
    /** The width percentage of the containing {@link GridPane} the graphic node itself should occupy. */
    private static final double GRAPHIC_NODE_WIDTH_PERCENTAGE = GRAPHIC_WIDTH_PERCENTAGE - 5.0;
    /** The padding between the text and the graphic. */
    private static final double GRAPHIC_TEXT_PADDING = 30.0;

    private static final int DEFAULT_LEFT_GRID_INDEX = 0;
    private static final int DEFAULT_RIGHT_GRID_INDEX = 1;

    @FXML
    private Label lineTextLabel;

    @FXML
    private HBox photoCardsDisplay;

    @FXML
    private Text lineIndexText;

    /**
     * Constructs a {@code DiaryLine} of only text, specified by the collection {@code text}.
     *
     * @param text The {@link String} text to display.
     */
    DiaryLine(String text, String index) {
        super(FXML);

        lineIndexText.setText(index);
        lineTextLabel.setText(text);
        getRoot().getChildren().remove(photoCardsDisplay);
    }

    /**
     * Constructs a {@code DiaryLine} of only images, specified by the collection {@code photos}.
     *
     * @param photos The {@link Collection} of {@link Photo}s to display.
     */
    DiaryLine(Collection<Photo> photos, String index) {
        super(FXML);

        lineIndexText.setText(index);
        getRoot().getChildren().remove(lineTextLabel);
        setGraphicOnlyConstraints();
        photoCardsDisplay.getChildren().addAll(
                photos.stream()
                        .map(DiaryEntryPhotoCard::new)
                        .map(UiPart::getRoot)
                        .collect(Collectors.toList()));
    }

    /**
     * Constructs a {@code DiaryLine} with text and an inline image, specified by the {@code photo}.
     *
     * @param text The {@link String} text of the diary line.
     * @param photo The {@link Photo} instance to use for the image data.
     * @param placeOnLeft True if the image should be positioned on the left. Otherwise, it is positioned on
     *                    the right.
     */
    DiaryLine(String text, Photo photo, boolean placeOnLeft, String index) {
        super(FXML);
        requireNonNull(photo);

        lineIndexText.setText(index);
        lineTextLabel.setText(text);
        int lineTextLabelIndex = placeOnLeft ? DEFAULT_RIGHT_GRID_INDEX : DEFAULT_LEFT_GRID_INDEX;
        int photoCardIndex = placeOnLeft ? DEFAULT_LEFT_GRID_INDEX : DEFAULT_RIGHT_GRID_INDEX;

        DiaryEntryPhotoCard diaryEntryPhotoCard = new DiaryEntryPhotoCard(photo);
        diaryEntryPhotoCard.bindImageViewDimensions(
                getRoot().prefWidthProperty().multiply(GRAPHIC_NODE_WIDTH_PERCENTAGE / 100.0));
        photoCardsDisplay.setAlignment(Pos.CENTER);
        photoCardsDisplay.getChildren().add(diaryEntryPhotoCard.getRoot());

        setTextGraphicConstraints(lineTextLabelIndex, photoCardIndex);
    }

    /**
     * Sets the grid constraints to accommodate only a graphic.
     */
    private void setGraphicOnlyConstraints() {
        getRoot().getColumnConstraints().get(DEFAULT_LEFT_GRID_INDEX).setPercentWidth(0.0);
        getRoot().getColumnConstraints().get(DEFAULT_RIGHT_GRID_INDEX).setPercentWidth(100.0);
    }

    /**
     * Sets the grid constraints to accommodate both a graphic and a text.
     *
     * @param textGridIndex The grid index the {@code lineTextLabel} should be placed in.
     * @param graphicGridIndex The grid index the {@code photoCardsDisplay} should be placed in.
     */
    private void setTextGraphicConstraints(int textGridIndex, int graphicGridIndex) {
        getRoot().setHgap(GRAPHIC_TEXT_PADDING);
        getRoot().getColumnConstraints()
                .get(textGridIndex).setPercentWidth(100.0 - GRAPHIC_WIDTH_PERCENTAGE);
        getRoot().getColumnConstraints()
                .get(graphicGridIndex).setPercentWidth(GRAPHIC_WIDTH_PERCENTAGE);
        GridPane.setColumnIndex(lineTextLabel, textGridIndex);
        GridPane.setColumnIndex(photoCardsDisplay, graphicGridIndex);
    }
}
