package seedu.planner.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import seedu.planner.logic.autocomplete.AutoCompleteParser;
import seedu.planner.logic.autocomplete.AutoCompleteSuggester;
import seedu.planner.logic.autocomplete.exceptions.CommandWordNotFoundException;
import seedu.planner.logic.autocomplete.exceptions.PreambleNotFoundException;
import seedu.planner.logic.parser.Prefix;

/**
 * This class is a TextField which implements an "autocomplete" functionality, based on AutoCompleteSuggester.
 *
 * Started from a code example written by Caleb Brinkman
 * at https://gist.github.com/floralvikings/10290131
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
                List<String> entries = getEntries(input);
                if (input.length() == 0) {
                    entriesPopup.hide();
                } else {
                    if (entries.size() > 0) {
                        populatePopup(entries);
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
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean,
                                Boolean aBoolean2) {
                entriesPopup.hide();
            }
        });

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
            item.setOnAction(actionEvent -> {
                String input = getText();
                boolean suggestionIsPrefix = result.contains("/");
                setText(replaceFromBack(input, result, suggestionIsPrefix));
                positionCaret(getText().length());
                entriesPopup.hide();
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

    //@@author 1nefootstep
    /**
     * Get the existing set of autocomplete entries.
     *
     * @return The existing autocomplete entries.
     */
    public List<String> getEntries(String input) {
        if (input.contains("<") || input.contains(">")) {
            return new LinkedList<>();
        }

        String command;
        boolean preambleIsPresent;
        List<Prefix> listOfPrefixPresent;
        try {
            command = autoCompleteParser.parseCommandWord(input);
        } catch (CommandWordNotFoundException e) {
            return autoCompleteSuggester.getPossibilities(input, false, new ArrayList<>());
        }

        if (input.charAt(input.length() - 1) != ' ') {
            return new LinkedList<>();
        }

        try {
            String preamble = autoCompleteParser.parsePreamble(input, command);
            preambleIsPresent = !preamble.equals("");
        } catch (PreambleNotFoundException e) {
            preambleIsPresent = false;
        }
        listOfPrefixPresent = autoCompleteParser.parsePrefix(input);
        return autoCompleteSuggester.getPossibilities(command, preambleIsPresent, listOfPrefixPresent);
    }

    //@@author 1nefootstep
    /**
     * Replaces the input with the given replacement.
     * Ensures that any half written part of replacement is removed before adding replacement.
     */
    private String replaceFromBack(String input, String replacement, boolean isPrefix) {
        if (isPrefix) {
            return input.trim() + replacement;
        } else {
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
}
