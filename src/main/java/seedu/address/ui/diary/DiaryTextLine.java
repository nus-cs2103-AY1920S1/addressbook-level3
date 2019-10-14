package seedu.address.ui.diary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import seedu.address.ui.UiPart;

/**
 * Custom JavaFX component controller representing a single line of text to be displayed in the diary.
 */
public class DiaryTextLine extends UiPart<HBox> {

    private static final String FXML = "diary/DiaryTextLine.fxml";

    @FXML
    private Label lineTextLabel;

    DiaryTextLine(String text) {
        super(FXML);
        lineTextLabel.setText(text);
    }
}
