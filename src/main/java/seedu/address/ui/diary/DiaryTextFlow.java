package seedu.address.ui.diary;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.ui.UiPart;

/**
 * Custom JavaFX component controller for displaying the text of a diary entry.
 */
public class DiaryTextFlow extends UiPart<VBox> {
    private static final String FXML = "diary/DiaryTextFlow.fxml";

    private static final String DIARY_STYLE_SHEET = "diary/diary.css";

    private static final int MIN_CHARACTERS_PER_TEXT = 30;

    private Logger logger = LogsCenter.getLogger(DiaryTextFlow.class);
    private String text;
    private int numCharactersPerText;

    private ObservableList<String> diaryTextLines;

    @FXML
    private ListView<String> diaryTextLinesList;

    DiaryTextFlow(String initialText) {
        super(FXML);
        this.text = initialText;
        this.numCharactersPerText = MIN_CHARACTERS_PER_TEXT;

        diaryTextLines = FXCollections.observableArrayList();
        diaryTextLinesList.getStylesheets().clear();
        diaryTextLinesList.getStylesheets().add(DIARY_STYLE_SHEET);
        diaryTextLinesList.setItems(diaryTextLines);
        diaryTextLinesList.setCellFactory(listViewCell -> new DiaryTextLineCell());
        getRoot().widthProperty().addListener(((observable, oldValue, newValue) -> {
            this.numCharactersPerText = Math.max(newValue.intValue(), MIN_CHARACTERS_PER_TEXT);
            this.numCharactersPerText /= 8;
            logger.log(Level.INFO, String.format("Current characters per text : %d", this.numCharactersPerText));
            setText(this.text);
        }));
    }

    void setText(String text) {
        this.text = text;
        int len = text.length();

        diaryTextLines.clear();
        for (int i = 0; i < len; i += numCharactersPerText) {
            if (i + numCharactersPerText >= len) {
               diaryTextLines.add(text.substring(i));
            } else {
                diaryTextLines.add(text.substring(i, i + numCharactersPerText));
            }
        }
    }

    private class DiaryTextLineCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                DiaryTextLine diaryTextLine = new DiaryTextLine(item);
                diaryTextLine.getRoot().prefWidthProperty().bind(diaryTextLinesList.widthProperty());

                setGraphic(diaryTextLine.getRoot());
            }
        }
    }
}
