package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * A text field that can handle autocompletion.
 */
public class Autocomplete extends TextField {
    private static final int MAX_ENTRIES = 10;
    private final SortedSet<String> commandKeywords;
    private ContextMenu keywordMenu;

    /**
     * Constructs a textfield that can handle autocompletion.
     * @param commandKeywords A list of keywords to be autocompleted.
     */
    public Autocomplete(List<String> commandKeywords) {
        super();
        this.commandKeywords = new TreeSet<>();
        this.commandKeywords.addAll(commandKeywords);
        keywordMenu = new ContextMenu();
        focusedProperty().addListener((unused0, unused1, unused2) -> keywordMenu.hide());
        textProperty().addListener((unused0, unused1, unused2) -> keywordMenu.hide());
    }

    /**
     * Populate the entry set with the given search results.
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
                setText(result);
                positionCaret(result.length());
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
            ArrayList<String> searchResult = new ArrayList<>(commandKeywords.subSet(getText(),
                    getText() + Character.MAX_VALUE));
            if (searchResult.size() == 1) {
                setText(searchResult.get(0));
                positionCaret(searchResult.get(0).length());
            } else if (searchResult.size() > 1) {
                populateMenu(searchResult);
                if (!keywordMenu.isShowing()) {
                    keywordMenu.show(Autocomplete.this, Side.BOTTOM, 15, 0);
                }
            } else {
                keywordMenu.hide();
            }
        }
    }
}
