package seedu.planner.logic.commands.autocomplete;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import seedu.planner.logic.commands.autocomplete.exceptions.CommandWordNotFoundException;
import seedu.planner.logic.commands.autocomplete.exceptions.PreambleNotFoundException;
import seedu.planner.logic.parser.Prefix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * This class is a TextField which implements an "autocomplete" functionality, based on a supplied list of entries.
 *
 * @author Caleb Brinkman
 */
public class AutoCompleteTextField extends TextField {
    /**
     * The popup used to select an entry.
     */
    private ContextMenu entriesPopup;

    private final AutoCompleteParser autoCompleteParser = new AutoCompleteParser();
    private final AutoCompleteSuggester autoCompleteSuggester = new AutoCompleteSuggester();

    /**
     * Construct a new AutoCompleteTextField.
     */
    public AutoCompleteTextField() {
        super();
        entriesPopup = new ContextMenu();
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                String input = getText();
                SortedSet<String> entries = getEntries(input);
                if (input.length() == 0) {
                    entriesPopup.hide();
                } else {
                    LinkedList<String> searchResult = new LinkedList<>(entries);
                    if (entries.size() > 0) {
                        populatePopup(searchResult);
                        if (!entriesPopup.isShowing()) {
                            entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                        }
                    } else {
                        entriesPopup.hide();
                    }
                }
            }
        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                entriesPopup.hide();
            }
        });

    }

    /**
     * Get the existing set of autocomplete entries.
     *
     * @return The existing autocomplete entries.
     */
    public SortedSet<String> getEntries(String input) {
        if (input.contains("<") || input.contains(">")) {
            return new TreeSet<>();
        }

        String command;
        boolean preambleIsPresent;
        List<Prefix> listOfPrefixPresent;
        try {
            command = autoCompleteParser.parseCommandWord(input);
        } catch (CommandWordNotFoundException e) {
            return new TreeSet<>(
                    autoCompleteSuggester.getPossibilities(input, false, new ArrayList<>())
            );
        }

        if (input.charAt(input.length() - 1) != ' ') {
            return new TreeSet<>();
        }

        try {
            String preamble = autoCompleteParser.parsePreamble(input, command);
            if (preamble.equals("")) {
                preambleIsPresent = false;
            } else {
                preambleIsPresent = true;
            }
        } catch (PreambleNotFoundException e) {
            preambleIsPresent = false;
        }
        listOfPrefixPresent = autoCompleteParser.parsePrefix(input);
        return new TreeSet<>(
            autoCompleteSuggester.getPossibilities(command, preambleIsPresent, listOfPrefixPresent)
        );
    }

    /**
     * Populate the entry set with the given search results.  Display is limited to 5 entries, for performance.
     *
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 7;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String input = getText();
                    setText(replaceFromBack(input, result));
                    positionCaret(getText().length());
                    entriesPopup.hide();
                }
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

    private String replaceFromBack(String input, String replacement) {
        String[] splitWords = input.split(" ");
        String match = "";
        for (int i = splitWords.length - 1; i >= 0; i--) {
            String curr;
            if (match == "") {
                curr = splitWords[i];
            } else {
                curr = splitWords[i] + " " + match;
            }
            if (Pattern.compile("\\b" + curr).matcher(replacement).find()) {
                match = curr;
            } else {
                break;
            }
        }
        return input.replaceFirst(match + "\\s*\\Z", replacement);
    }
}