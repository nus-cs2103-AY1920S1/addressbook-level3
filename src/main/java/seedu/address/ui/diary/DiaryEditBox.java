package seedu.address.ui.diary;

import java.util.function.Consumer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import seedu.address.ui.UiPart;

/**
 * Custom JavaFX component controller for the edit box in the diary page.
 * It is backed by a {@link TextArea}.
 */
public class DiaryEditBox extends UiPart<VBox> {
    private static final String FXML = "diary/DiaryEditBox.fxml";

    private Consumer<String> textChangeHandler;

    @FXML
    private TextArea textEditor;

    DiaryEditBox(Consumer<String> textChangeHandler) {
        super(FXML);
        this.textChangeHandler = textChangeHandler;
        textEditor.prefWidthProperty().bind(getRoot().widthProperty());
        textEditor.prefHeightProperty().bind(getRoot().heightProperty());
    }

    String getText() {
        return textEditor.getText();
    }

    ObservableList<CharSequence> getObservableParagraphs() {
        return textEditor.getParagraphs();
    }

    void setText(String text) {
        textEditor.setText(text);
    }

    @FXML
    private void handleTextChange() {
        textChangeHandler.accept(textEditor.getText());
    }
}
