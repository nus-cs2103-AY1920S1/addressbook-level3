package seedu.address.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import seedu.address.storage.SuggestionsStorage;

/**
 * This class is a TextField which implements an "autocomplete" functionality, based on a supplied list of entries.
 * @author Caleb Brinkman
 */
public class AutoCompleteTextField extends TextField {

    /** The existing autocomplete suggestions. */
    private final SortedSet<String> suggestions;

    /** The popup used to select an entry. */
    private ContextMenu suggestionsPopup;

    /** Construct a new AutoCompleteTextField. */
    public AutoCompleteTextField() {
        super();
        //tree set of suggestions to be shown when user is entering
        suggestions = SuggestionsStorage.getSuggestions();
        suggestionsPopup = new ContextMenu();
        textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (getText().length() == 0) {
                    suggestionsPopup.hide();
                } else {
                    LinkedList<String> searchResult = new LinkedList<>();
                    searchResult.addAll(suggestions.subSet(getText(), getText() + Character.MAX_VALUE));
                    if (suggestions.size() > 0) {
                        populatePopup(searchResult);
                            if (!suggestionsPopup.isShowing()) {
                                suggestionsPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                            }
                    } else {
                        suggestionsPopup.hide();
                    }
                }
            }
        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
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
            item.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent) {
                    setText(result);
                    suggestionsPopup.hide();
                }
            });
            menuItems.add(item);
        }
        suggestionsPopup.getItems().clear();
        suggestionsPopup.getItems().addAll(menuItems);

    }
}