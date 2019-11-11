package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import seedu.address.model.ReadOnlyModulePlanner;


/**
 * A text field that can handle autocompletion.
 */
public class AutocompleteTextField extends TextField {
    public static final int MAX_ENTRIES = 10;
    private ContextMenu keywordMenu;
    private ModulePlannerAutocompleteSearch autocompleteSearch;

    /**
     * Constructs a textfield that can handle autocompletion.
     */
    public AutocompleteTextField(ReadOnlyModulePlanner modulePlanner) {
        super();
        requireNonNull(modulePlanner);
        this.autocompleteSearch = new ModulePlannerAutocompleteSearch(modulePlanner);
        keywordMenu = new ContextMenu();
        focusedProperty().addListener((unused0, unused1, unused2) -> keywordMenu.hide());
        textProperty().addListener((unused0, unused1, unused2) -> keywordMenu.hide());
    }

    /**
     * Populate the entry set with the given search results.
     *
     * @param searchResult The list of matching strings.
     */
    private void populateMenu(List<String> searchResult) {
        ArrayList<CustomMenuItem> menuItems = new ArrayList<>();
        int noOfEntries = Math.min(searchResult.size(), MAX_ENTRIES);
        for (int i = 0; i < noOfEntries; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(unused -> {
                setAutocompleteText(result);
                keywordMenu.hide();
            });
            menuItems.add(item);
        }
        keywordMenu.getItems().clear();
        keywordMenu.getItems().addAll(menuItems);
    }

    /**
     * Handles the autocomplete process.
     */
    public void handleAutocomplete() {
        if (getText().length() == 0) {
            keywordMenu.hide();
        } else {
            String input = getText();
            List<String> searchResults = autocompleteSearch.getSearchResults(input);
            requireNonNull(searchResults);
            if (searchResults.size() == 1) {
                setAutocompleteText(searchResults.get(0));
            } else if (searchResults.size() > 1) {
                populateMenu(searchResults);
                if (!keywordMenu.isShowing()) {
                    keywordMenu.show(AutocompleteTextField.this, Side.BOTTOM, 15, 0);
                }
                keywordMenu.getSkin().getNode().lookup(".menu-item").requestFocus();
            } else {
                // If there are no autocomplete results, do nothing.
                keywordMenu.hide();
            }
        }
    }

    /**
     * Resets the autocomplete when there is a change in active study plan.
     */
    public void handleChangeOfActiveStudyPlan() {
        autocompleteSearch.handleChangeOfActiveStudyPlan();
    }

    /**
     * Sets the text in the text field.
     */
    private void setAutocompleteText(String searchResult) {
        int lastIndex = getText().lastIndexOf(" ");
        String finalText;
        if (lastIndex < 0) {
            finalText = searchResult;
        } else {
            finalText = getText().substring(0, lastIndex)
                    + " " + searchResult;
        }
        setText(finalText);
        positionCaret(finalText.length());
    }
}
