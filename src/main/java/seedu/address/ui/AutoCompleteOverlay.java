//@@author CarbonGrid
package seedu.address.ui;

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
    private static final double FONT_WIDTH = 10.81; //via experimenting, currently no solution to get an exact
    private static final int SCROLL_NEGATE_OFFSET = 20;
    private static final int NUM_IN_VIEW = 10;
    private static final int MAX_HEIGHT = CELL_HEIGHT * NUM_IN_VIEW;
    private static final int X_OFFSET = 22;

    private final SelectionNotifier selectionNotifier;

    @FXML
    private ListView<TextFlow> autoCompleteOverlay;

    public AutoCompleteOverlay(SelectionNotifier selectionNotifier) {
        super("AutoCompleteOverlay.fxml");
        this.selectionNotifier = selectionNotifier;
        this.autoCompleteOverlay.setVisible(false);
        this.autoCompleteOverlay.setMaxHeight(MAX_HEIGHT);
    }

    /**
     * Updates the display of suggestions.
     */
    public void showSuggestions(String prefix, List<String> listOfSuggestions) {
        autoCompleteOverlay.setVisible(false);
        ObservableList<TextFlow> ols = autoCompleteOverlay.getItems();
        ols.setAll();

        if (prefix.isBlank() || listOfSuggestions.isEmpty()) {
            return;
        }
        int prefixLastIndex = prefix.lastIndexOf(' ') + 1;
        prefix = prefix.substring(prefixLastIndex);
        autoCompleteOverlay.setTranslateX(prefixLastIndex * FONT_WIDTH + X_OFFSET);

        int suggestionLength = 0;
        listOfSuggestions.sort(String::compareTo);
        for (String suggestion : listOfSuggestions) {
            if (suggestion.isBlank()) {
                break;
            }
            Text prefixText = new Text(prefix);
            prefixText.setFill(Paint.valueOf("#ff9f1a"));
            Text suggestionText = new Text(suggestion);
            suggestionText.setFill(Paint.valueOf("#FFF"));
            TextFlow textFlow = new TextFlow(prefixText, suggestionText);
            ols.add(textFlow);
            suggestionLength = Math.max(suggestion.length(), suggestionLength);
        }
        autoCompleteOverlay.getSelectionModel().select(0);
        autoCompleteOverlay.setPrefHeight(1 + listOfSuggestions.size() * CELL_HEIGHT);
        autoCompleteOverlay.setPrefWidth((prefix.length() + suggestionLength) * FONT_WIDTH + SCROLL_NEGATE_OFFSET);
        if (!ols.isEmpty()) {
            autoCompleteOverlay.setVisible(true);
        }
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
        autoCompleteOverlay.scrollTo(targetIndex);
        msm.select(targetIndex);
    }

    public void simulateMouseClick() {
        handleMouseClicked();
    }

    public boolean isSuggesting() {
        return autoCompleteOverlay.isVisible();
    }

    public void hide() {
        autoCompleteOverlay.setVisible(false);
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
        sb.append(' ');
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
