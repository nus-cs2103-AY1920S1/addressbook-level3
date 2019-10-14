package seedu.address.ui.diary;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiPart;

/**
 * Custom JavaFX component controller for displaying the text of a diary entry.
 */
public class DiaryTextFlow extends UiPart<VBox> {
    private static final String FXML = "diary/DiaryTextFlow.fxml";

    private static final int MIN_CHARACTERS_PER_TEXT = 10;

    private Logger logger = LogsCenter.getLogger(DiaryTextFlow.class);
    private int numCharactersPerText = MIN_CHARACTERS_PER_TEXT;
    private String text;

    @FXML
    private TextFlow diaryTextFlow;

    DiaryTextFlow(String initialText) {
        super(FXML);
        this.text = initialText;


        getRoot().widthProperty().addListener(((observable, oldValue, newValue) -> {
            numCharactersPerText = Math.max(newValue.intValue(), MIN_CHARACTERS_PER_TEXT);
            logger.log(Level.INFO, String.format("Current characters per text : %d",newValue.intValue()));
            setText(this.text);
        }));
    }

    void setText(String text) {
        this.text = text;
        int len = text.length();

        diaryTextFlow.getChildren().clear();
        for (int i = 0; i < len; i += numCharactersPerText) {
            if (i + numCharactersPerText >= len) {
                diaryTextFlow.getChildren().add(
                        new Text(text.substring(i)));
            } else {
                diaryTextFlow.getChildren().add(
                        new Text(text.substring(i, i + numCharactersPerText)));
            }
        }
    }
}
