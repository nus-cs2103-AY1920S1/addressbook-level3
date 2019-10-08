package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The Ui component for AutoComplete.
 */
public class AutoCompleteOverlay extends UiPart<Region> {

    @FXML
    private ListView<TextFlow> autoCompleteOverlay;

    public AutoCompleteOverlay() {
        super("AutoCompleteOverlay.fxml");
    }

    /**
     * Updates the display of suggestions.
     *
     * @param prefix
     * @param listOfSuggestions
     */
    public void showSuggestions(String prefix, List<String> listOfSuggestions) {
        if (prefix.isBlank() || listOfSuggestions.isEmpty()) {
            autoCompleteOverlay.setPrefHeight(0);
            return;
        }
        ObservableList<TextFlow> ols = autoCompleteOverlay.getItems();
        ArrayList<TextFlow> arrls = new ArrayList<>();
        listOfSuggestions.sort(String::compareTo);
        for (String suggestion : listOfSuggestions) {
            Text prefixText = new Text(prefix);
            prefixText.setFill(Paint.valueOf("#0FF"));
            Text suggestionText = new Text(suggestion);
            suggestionText.setFill(Paint.valueOf("#FFF"));
            arrls.add(new TextFlow(prefixText, suggestionText));
        }
        ols.setAll(arrls);
        autoCompleteOverlay.getSelectionModel().select(0);
        autoCompleteOverlay.setPrefHeight(listOfSuggestions.size() * 20);
    }

    public void traverseSelection(boolean moveUp) {
        autoCompleteOverlay.scrollTo(1);
    }
}
