package seedu.address.ui;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The Ui component for AutoComplete.
 */
public class AutoCompleteOverlay extends UiPart<Region> {

    private static final int CELL_HEIGHT = 20;
    private static final int NUM_IN_VIEW = 10;
    private static final int MAX_HEIGHT = CELL_HEIGHT * NUM_IN_VIEW;

    @FXML
    private ListView<TextFlow> autoCompleteOverlay;

    private SelectionNotifier selectionNotifier;

    public AutoCompleteOverlay(SelectionNotifier selectionNotifier) {
        super("AutoCompleteOverlay.fxml");
        this.selectionNotifier = selectionNotifier;
        this.autoCompleteOverlay.setMaxHeight(MAX_HEIGHT);
    }

    /**
     * Updates the display of suggestions.
     */
    public void showSuggestions(String prefix, List<String> listOfSuggestions) {
        if (prefix.isBlank() || listOfSuggestions.isEmpty()) {
            autoCompleteOverlay.setVisible(false);
            autoCompleteOverlay.getItems().setAll(new ArrayList<>());
            return;
        }
        ObservableList<TextFlow> ols = autoCompleteOverlay.getItems();
        ArrayList<TextFlow> arrls = new ArrayList<>();
        listOfSuggestions.sort(String::compareTo);
        for (String suggestion : listOfSuggestions) {
            if (suggestion.isBlank()) {
                break;
            }
            Text prefixText = new Text(prefix);
            prefixText.setFill(Paint.valueOf("#0FF"));
            Text suggestionText = new Text(suggestion);
            suggestionText.setFill(Paint.valueOf("#FFF"));
            arrls.add(new TextFlow(prefixText, suggestionText));
        }
        ols.setAll(arrls);
        autoCompleteOverlay.getSelectionModel().select(0);
        autoCompleteOverlay.setPrefHeight(listOfSuggestions.size() * CELL_HEIGHT);
        autoCompleteOverlay.setVisible(true);
    }

    /**
     * Traverses the AutoCompleteOverlay.
     *
     * @param traverseUp true if UP, false if DOWN
     */
    public void traverseSelection(boolean traverseUp) {
        int size = autoCompleteOverlay.getItems().size();
        MultipleSelectionModel msm = autoCompleteOverlay.getSelectionModel();
        int targetIndex = (size + msm.getSelectedIndex() + (traverseUp ? -1 : 1)) % size;
        if (targetIndex > msm.getSelectedIndex()) {
            autoCompleteOverlay.scrollTo(Math.max(0, targetIndex + 1 - NUM_IN_VIEW));
        } else {
            autoCompleteOverlay.scrollTo(Math.min(size - 1, targetIndex));
        }
        msm.select(targetIndex);
    }

    public void simulateMouseClick() {
        handleMouseClicked();
    }

    public boolean isSuggesting() {
        return !autoCompleteOverlay.getItems().isEmpty();
    }

    /**
     * Handles the Mouse Clicked Event.
     */
    @FXML
    private void handleMouseClicked() {
        StringBuilder sb = new StringBuilder();
        autoCompleteOverlay.getSelectionModel()
                .getSelectedItem()
                .getChildren()
                .forEach(elem -> sb.append(((Text) elem).getText()));
        selectionNotifier.notify(sb.toString());
    }

    /**
     * Represents a function that notifies selection.
     */
    @FunctionalInterface
    public interface SelectionNotifier {
        /**
         * Notify changes.
         */
        void notify(String commandText);
    }
}
