package seedu.address.ui;

import static seedu.address.ui.CommandBox.ERROR_STYLE_CLASS;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

//@@author uberSaiyan-reused
//StackOverflow answer on writing an autocomplete text field from
//https://stackoverflow.com/questions/36861056/javafx-textfield-auto-suggestions

/**
 * Represents a JavaFX TextField with auto-complete drop down menu built-in.
 */
public class AutoCompleteTextField extends TextField {
    private final SortedSet<String> entries;
    private ContextMenu entriesPopup;

    public AutoCompleteTextField() {
        super();
        this.entries = new TreeSet<>();
        this.entriesPopup = new ContextMenu();

        setListener();
    }

    private void setListener() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            // calls #setStyleToDefault() whenever there is a change to the text of the command box.
            setStyleToDefault();
            String enteredText = getText();
            // hide suggestions if no input
            if (enteredText == null || enteredText.isEmpty()) {
                entriesPopup.hide();
            } else {
                // filter
                List<String> filteredEntries = entries.stream()
                        .filter(e -> e.toLowerCase().contains(enteredText.toLowerCase()))
                        .sorted((e1, e2) -> compareEntries(e1, e2, enteredText))
                        .collect(Collectors.toList());
                if (!filteredEntries.isEmpty()) {
                    populatePopup(filteredEntries, enteredText);
                    if (!entriesPopup.isShowing()) {
                        entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    entriesPopup.hide();
                }
            }
        });

        focusedProperty().addListener(((observable, oldValue, newValue) -> {
            entriesPopup.hide();
        }));
    }

    /**
     * Populates drop down menu with results from {@code searchResults}.
     * @param searchResults A list of strings that match {@code searchWord}.
     * @param searchWord The word being matched against.
     */
    private void populatePopup(List<String> searchResults, String searchWord) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 5;
        int count = Math.min(searchResults.size(), maxEntries);

        for (int i = 0; i < count; i++) {
            final String result = searchResults.get(i);
            Label entryLabel = new Label();
            entryLabel.setGraphic(buildTextFlow(result, searchWord));
            entryLabel.setPrefHeight(10);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            menuItems.add(item);

            item.setOnAction(actionEvent -> {
                setText(result);
                positionCaret(result.length());
                entriesPopup.hide();
            });
        }

        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

    public SortedSet<String> getEntries() {
        return entries;
    }

    /**
     * Returns a {@code TextFlow} that highlights the filtered word in a matching word.
     * @param text A word containing {@code filter}.
     * @param filter A word to highlight.
     * @return A highlighted TextFlow.
     */
    private static TextFlow buildTextFlow(String text, String filter) {
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex, filterIndex + filter.length()));
        textFilter.setFill(Color.ORANGE);
        textFilter.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
        return new TextFlow(textBefore, textFilter, textAfter);
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        this.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    private int compareEntries(String firstMatch, String secondMatch, String text) {
        int firstIndex = firstMatch.indexOf(text);
        int secondIndex = secondMatch.indexOf(text);
        return firstIndex - secondIndex;
    }

}

//@@author
