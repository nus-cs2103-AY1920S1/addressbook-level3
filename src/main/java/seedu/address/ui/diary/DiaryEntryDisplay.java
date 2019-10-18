package seedu.address.ui.diary;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.diary.photo.Photo;
import seedu.address.model.diary.photo.PhotoList;
import seedu.address.ui.UiPart;

/**
 * Custom JavaFX component controller for displaying the text of a diary entry.
 */
public class DiaryEntryDisplay extends UiPart<VBox> {
    private static final String FXML = "diary/DiaryTextFlow.fxml";

    private static final Pattern IMAGE_SEPARATOR_PATTERN = Pattern.compile(
            "(?<pretext>[^<]*)(?<imagetag><images\\s*(?<position>([a-z]*[A-Z]*)*)"
                    + "(?<numbers>([^>][0-9]*[\\s]*)*)>)(?<posttext>.*)");
    private static final String IMAGE_POSITION_LEFT_PATTERN = "left";

    private Logger logger = LogsCenter.getLogger(DiaryEntryDisplay.class);
    private PhotoList photoList;

    private ObservableList<CharSequence> diaryTextLines;

    @FXML
    private ListView<CharSequence> diaryTextLinesList;

    DiaryEntryDisplay(ObservableList<CharSequence> observableParagraphs) {
        super(FXML);
        this.photoList = new PhotoList();
        this.diaryTextLines = observableParagraphs;
        diaryTextLinesList.setItems(this.diaryTextLines);
        diaryTextLinesList.setCellFactory(listViewCell -> new DiaryTextLineCell());
    }

    void setPhotoList(PhotoList photoList) {
        this.photoList = photoList;
    }

    /**
     * {@link ListCell} to use for the cell factory of the {@code diaryTextLinesList}.
     */
    private class DiaryTextLineCell extends ListCell<CharSequence> {
        @Override
        protected void updateItem(CharSequence item, boolean empty) {
            super.updateItem(item, empty);

            DiaryLine diaryLine;
            if (empty || item == null) {
                diaryLine = new DiaryLine("");
            } else {
                Matcher m = IMAGE_SEPARATOR_PATTERN.matcher(item);

                if (!m.matches()) {
                    diaryLine = new DiaryLine(item.toString());
                } else {
                    String preText = m.group("pretext");
                    String postText = m.group("posttext");
                    String numbers = m.group("numbers");
                    String position = m.group("position");

                    ArrayList<Photo> photos = parseImageSeparator(numbers);
                    if (preText.trim().length() == 0 && postText.trim().length() == 0) {
                        diaryLine = new DiaryLine(photos);
                    } else if (photos.size() == 0) {
                        diaryLine = new DiaryLine(preText.stripTrailing() + postText);
                    } else {
                        diaryLine = new DiaryLine(
                                preText.stripTrailing() + postText,
                                photos.get(0),
                                position.equalsIgnoreCase(IMAGE_POSITION_LEFT_PATTERN));
                    }
                }
            }
            setGraphic(diaryLine.getRoot());
            diaryLine.getRoot().prefWidthProperty().bind(getRoot().widthProperty());
        }

        /**
         * Parses the given string of space delimited numbers, and uses the {@code photoList} of the parent
         * class {@link DiaryEntryDisplay} to return an {@link ArrayList} of {@link Photo}s with matching indices.
         * It will skip any token that is not a valid integer or is out of the bounds of the {@code photoList}.
         *
         * @param numbers The string of numbers to space delimited parse.
         * @return {@link ArrayList} of {@link Photo}s with matching indices.
         */
        private ArrayList<Photo> parseImageSeparator(String numbers) {
            String[] numberStrings = numbers.trim().split("\\s+");
            ArrayList<Photo> photos = new ArrayList<Photo>();

            for (String numberString : numberStrings) {
                try {
                    Index photoNumber = Index.fromOneBased(Integer.parseInt(numberString));
                    photos.add(photoList.getObservablePhotoList().get(photoNumber.getZeroBased()));
                } catch (NumberFormatException ex) {
                    logger.log(Level.INFO,
                            "Detected invalid number in <images ...> separator. " + numberString);
                } catch (IndexOutOfBoundsException ex) {
                    logger.log(Level.INFO, String.format(
                            "Detected out of bounds number in <images ...> separator. Entered: %1$s, List length: %2$s",
                            numberString, photoList.getObservablePhotoList().size()));
                }
            }

            return photos;
        }
    }
}
