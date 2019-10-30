package seedu.address.ui;

import static seedu.address.ui.CommandBox.ERROR_STYLE_CLASS;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.event.Event;
import javafx.event.EventType;
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
    private final ContextMenu entriesPopup;

    public AutoCompleteTextField() {
        super();
        this.entries = new TreeSet<>();
        this.entriesPopup = new ContextMenu();
    }

    /**
     * Populates drop down menu with results from {@code searchResults}.
     * @param searchResults A list of strings that match {@code searchWord}.
     * @param searchWord The word being matched against.
     */
    public void populatePopup(List<String> searchResults, String searchWord) {
        List<CustomMenuItem> menuItems = new ArrayList<>();
        int maxEntries = 7;
        int count = Math.min(searchResults.size(), maxEntries);

        for (int i = 0; i < count; i++) {
            final String result = searchResults.get(i);
            Label entryLabel = new Label();
            entryLabel.setGraphic(buildTextFlow(result, searchWord));
            entryLabel.setPrefHeight(10);
            CustomMenuItem item = new CustomMenuItem(entryLabel, false);
            menuItems.add(item);

            item.setOnAction(actionEvent -> {
                setText(getText().substring(0, getText().lastIndexOf(searchWord)) + result);
                positionCaret(getText().length());
                fireEvent(new Event(EventType.ROOT));
            });
        }

        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

    public SortedSet<String> getEntries() {
        return entries;
    }

    public ContextMenu getEntriesPopup() {
        return entriesPopup;
    }

    /**
     * Returns a {@code TextFlow} that highlights the filtered word in a matching word.
     * @param text A word containing {@code filter}.
     * @param filter A word to highlight.
     * @return A highlighted TextFlow.
     */
    public static TextFlow buildTextFlow(String text, String filter) {
        if (filter.equals("")) {
            Text wholeText = new Text(text);
            wholeText.setFill(Color.CYAN);
            wholeText.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
            return new TextFlow(wholeText);
        } else {
            int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
            Text textBefore = new Text(text.substring(0, filterIndex));
            Text textAfter = new Text(text.substring(filterIndex + filter.length()));
            Text textFilter = new Text(text.substring(filterIndex, filterIndex + filter.length()));
            textFilter.setFill(Color.YELLOW);
            textFilter.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
            return new TextFlow(textBefore, textFilter, textAfter);
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    public void setStyleToDefault() {
        this.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Compares {@code firstMatch} and {@code secondMatch} with {@code text} to determine which is more similar.
     * @param firstMatch A string containing {@code text}.
     * @param secondMatch A string containing {@code text}.
     * @param text A string.
     * @return 0 if equally similar, negative int if firstMatch more similar, positive int if secondMatch more similar.
     */
    public int compareEntries(String firstMatch, String secondMatch, String text) {
        int firstIndex = firstMatch.indexOf(text);
        int secondIndex = secondMatch.indexOf(text);
        return firstIndex - secondIndex;
    }

    /**
     * Hides and shows the dropdown.
     */
    public void refreshDropdown() {
        entriesPopup.hide();
        entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
    }

}

//@@author
