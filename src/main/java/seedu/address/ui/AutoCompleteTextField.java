package seedu.address.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandMasterList;

//@@author SebastianLie
/**
 * AutoComplete added to textfield.
 * listens to user input and populates
 * drop down menu with existing commands
 */
public class AutoCompleteTextField extends TextField {

    private final SortedSet<String> entries;
    private ContextMenu entriesPopup;
    private final Logger logger = LogsCenter.getLogger(getClass());
    private ArrayList<String> entriesList = new ArrayList<>();

    /**
     * the listener that will be added to textproperty
     * listens for changes in user input and suggests
     * commands accordingly, filters by contains()
     */
    private ChangeListener<String> changeListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            String enteredText = getText();
            int maxCompareWordSize = 6;
            int comparingCutoffPoint = Math.min(enteredText.length(), maxCompareWordSize);
            String mainRequest = enteredText.substring(0, comparingCutoffPoint);
            if (enteredText == null || enteredText.isEmpty()) {
                entriesPopup.hide();
            } else {
                //TODO make this code better
                List<String> filteredEntries = entries.stream()
                        .filter(e -> e.toLowerCase().contains(mainRequest.toLowerCase()))
                        .collect(Collectors.toList());
                if (!filteredEntries.isEmpty()) {
                    populatePopup(filteredEntries, mainRequest);
                    if (!entriesPopup.isShowing()) {
                        //position of popup
                        entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    entriesPopup.hide();
                }
            }
        }
    };

    /**
     * initialises all commands and their usages,
     * popup-menu as well as listener
     */
    public AutoCompleteTextField() {
        super();
        this.entries = new TreeSet<>();
        this.entriesPopup = new ContextMenu();
        List<String> commandMasterList = new ArrayList<String>(CommandMasterList.getCommandWords());
        assert !commandMasterList.isEmpty() : "Command master list cannot be empty!";
        entries.addAll(commandMasterList);
        setListener();
    }

    /**
     * adds listener to suggest commands
     */
    private void setListener() {
        textProperty().addListener(changeListener);
        logger.info("Listening to textfield.");
    }

    /**
     * populates contextmenu with suggestions from listener
     * if any suggestion is selected,
     * set the textfield to suggestion
     * @param searchResult
     * @param searchRequest
     */
    private void populatePopup(List<String> searchResult, String searchRequest) {
        assert !searchResult.isEmpty() : "Search result must be non-empty in this method.";
        if (searchRequest.equals("")) {
            return;
        }
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 10;
        int numEntries = Math.min(searchResult.size(), maxEntries);
        entriesList.clear();
        for (int i = 0; i < numEntries; i++) {
            final String result = searchResult.get(i);
            boolean requestLongerThanResult = searchRequest.length() >= result.length();
            if (result.equals("") || requestLongerThanResult) {
                continue;
            }
            entriesList.add(result);
            Label entryLabel = new Label();
            TextFlow highlightText = Styles.buildTextFlow(result, searchRequest);

            entryLabel.setGraphic(highlightText);
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

    /**
     * shutsdown listener for autocomplete
     */
    public void shutdown() {
        logger.info("Shutting down autocomplete listener for textfield....");
        textProperty().removeListener(changeListener);
    }

    /**
     * helps to autocomplete on ctrl key press
     * sets cursor position at
     */
    public void setAutoCompleteResult() {
        // return if no suggestions
        if (entriesList.isEmpty() || !entriesPopup.isShowing()) {
            return;
        }
        String result = entriesList.get(0);
        setText(result);
        positionCaret(result.length());
        entriesPopup.hide();
    }
    /**
     * hide autocomplete results if needed
     */
    public void hidePopUp() {
        entriesPopup.hide();

    }
}
