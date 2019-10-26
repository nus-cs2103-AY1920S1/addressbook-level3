package seedu.address.ui.diary;

import java.util.function.Consumer;

import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import seedu.address.ui.UiPart;

/**
 * Custom JavaFX component controller for the edit box in the diary page.
 * It is backed by a {@link TextArea}.
 */
class DiaryEditBox extends UiPart<TextArea> {
    private static final String FXML = "diary/DiaryEditBox.fxml";

    DiaryEditBox(Consumer<String> textChangeHandler) {
        super(FXML);
        getRoot().focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                textChangeHandler.accept(getRoot().getText());
            }
        }));
    }

    ObservableList<CharSequence> getObservableParagraphs() {
        return getRoot().getParagraphs();
    }

    void setText(String text) {
        getRoot().setText(text);
    }

    void requestFocus() {
        getRoot().requestFocus();
    }
}
