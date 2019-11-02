package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import seedu.address.logic.parser.FinSecParser;
import seedu.address.storage.SuggestionsStorage;

/**
 * Represents a TextField which implements an "autocomplete" functionality, based on a supplied list of suggestions.
 */
public class AutocorrectTextField extends TextField {

    /** The autocomplete suggestions for commands. */
    private Set<String> suggestionCommands;

    /** The additional autocomplete suggestions */
    private Set<String> addSuggestions;

    /** The popup used to select an entry. */
    private ContextMenu suggestionsPopup;

    /** Construct a new AutocorrectTextField. */
    public AutocorrectTextField() {
        super();
        suggestionCommands = FinSecParser.getCommandList().keySet();
        addSuggestions = new HashSet<>(SuggestionsStorage.getSuggestions());
        suggestionsPopup = new ContextMenu();
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (getText().length() == 0) {
                    suggestionsPopup.hide();
                } else {
                    //show the drop down
                    ArrayList<String> searchResult = new ArrayList<>();
                    for (String command : suggestionCommands) {
                        //if the user entry is a subset of any of the words in the suggestions ArrayList
                        if (command.startsWith(getText()) && command.contains(getText())) {
                            searchResult.add(command);
                        }
                    }
                    for (String word : addSuggestions) {
                        if (word.startsWith(getText()) && word.contains(getText())) {
                            searchResult.add(word);
                        }
                    }
                    if (suggestionCommands.size() > 0) {
                        populatePopup(searchResult);
                        if (!suggestionsPopup.isShowing()) {
                            suggestionsPopup.show(AutocorrectTextField.this, Side.BOTTOM,
                                    0, 0);
                        }
                    } else {
                        suggestionsPopup.hide();
                    }
                }
            }
        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean,
                                Boolean aBoolean2) {
                suggestionsPopup.hide();
            }
        });

    }

    /**
     * Populate the entry set with the given search results.  Display is limited to 10 entries, for performance.
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    setText(result);
                    endOfNextWord();
                    suggestionsPopup.hide();
                }
            });
            menuItems.add(item);
        }
        suggestionsPopup.getItems().clear();
        suggestionsPopup.getItems().addAll(menuItems);

    }

}
