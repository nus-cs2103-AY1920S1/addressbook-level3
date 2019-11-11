package seedu.address.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandMasterList;

//@@author SebastianLie
/**
 * AutoComplete added to textfield.
 * Listens to user input and populates
 * drop down menu with existing commands.
 */
public class AutoCompleteTextField extends TextField {

    private final SortedSet<String> entries;
    private ContextMenu entriesPopup;
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final int maxEntries = 10;
    private ArrayList<String> entriesList = new ArrayList<>();

    /**
     * The listener that will be added to textproperty
     * listens for changes in user input and suggests
     * commands accordingly, using getSuggestedCommands
     */
    private ChangeListener<String> changeListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            String enteredText = getText();

            int maxCompareWordSize = 6;
            int comparingCutoffPoint = Math.min(enteredText.length(), maxCompareWordSize);
            String mainText = enteredText.substring(0, comparingCutoffPoint);

            if (enteredText == null || enteredText.isEmpty()) {
                entriesPopup.hide();
            } else {
                ArrayList<String> filteredEntries = getSuggestedCommands(mainText);
                if (!filteredEntries.isEmpty()) {
                    populatePopUp(filteredEntries, mainText);
                    if (!entriesPopup.isShowing()) {
                        //position of popup
                        entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                    // V impt, enables focus on 1st suggestion all the time
                    entriesPopup.getSkin().getNode().lookup(".menu-item").requestFocus();
                } else {
                    entriesPopup.hide();
                }
            }
        }
    };

    /**
     * Initialises all commands and their usages,
     * popup-menu as well as listener.
     */
    public AutoCompleteTextField() {
        super();
        this.entries = new TreeSet<>();
        this.entriesPopup = new ContextMenu();
        List<String> commandMasterList = new ArrayList<String>(CommandMasterList.getCommandWords());
        assert !commandMasterList.isEmpty() : "Command master list cannot be empty!";
        entries.addAll(commandMasterList);
        initListener();
    }

    /**
     * Adds listener to suggest commands.
     */
    public void initListener() {
        textProperty().addListener(changeListener);
        focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            entriesPopup.hide();
        });
        logger.info("Listening to textfield.");
    }

    /**
     * shutsdown listener for autocomplete
     */
    public void shutDownListener() {
        logger.info("Shutting down autocomplete listener for textfield....");
        textProperty().removeListener(changeListener);
    }

    /**
     * filter entries based on contains
     */
    private ArrayList<String> getSuggestedCommands(String enteredText) {
        ArrayList<String> suggestedCommands = new ArrayList<>();
        boolean commandContainsUserInput;
        for (String command : entries) {
            commandContainsUserInput = command.contains(enteredText);
            if (commandContainsUserInput) {
                suggestedCommands.add(command);
            }
        }
        suggestedCommands.sort(new SuggestionComparator(enteredText));
        return suggestedCommands;
    }

    /**
     * comparator to decide which
     * suggested command to place first
     * in popup menu
     */
    public class SuggestionComparator implements Comparator<String> {

        private final String enteredText;

        public SuggestionComparator(String enteredText) {
            this.enteredText = enteredText;
        }
        @Override
        public int compare(String command, String otherCommand) {
            int inputIndexOfCommand = command.indexOf(enteredText);
            int inputIndexOfOtherCommand = otherCommand.indexOf(enteredText);
            if (inputIndexOfCommand < inputIndexOfOtherCommand) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    /**
     * Populates contextmenu with suggestions from listener
     * if any suggestion is selected,
     * set the textfield to suggestion.
     * @param searchResults
     * @param searchRequest
     */
    private void populatePopUp(ArrayList<String> searchResults, String searchRequest) {
        assert !searchResults.isEmpty() : "Search result must be non-empty in this method.";
        if (searchRequest.equals("")) {
            return;
        }
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int numEntries = Math.min(searchResults.size(), maxEntries);
        entriesList.clear();
        for (int i = 0; i < numEntries; i++) {
            final String mainText = searchResults.get(i);
            boolean requestLongerThanResult = searchRequest.length() >= mainText.length();
            if (mainText.equals("") || requestLongerThanResult) {
                continue;
            }
            CustomMenuItem popUpItem = buildPopUpEntry(mainText, searchRequest);
            menuItems.add(popUpItem);

            setOnSelection(popUpItem, mainText);
        }
        List<MenuItem> popUpList = entriesPopup.getItems();
        popUpList.clear();
        popUpList.addAll(menuItems);
    }

    /**
     * Helper for populatePopup.
     * Builds highlighted portion of text
     * for each popup entry.
     * @param mainText
     * @param portionToHighlight
     * @return
     */
    private CustomMenuItem buildPopUpEntry(String mainText, String portionToHighlight) {
        entriesList.add(mainText);
        Label entryLabel = new Label();
        TextFlow highlightText = Styles.buildTextFlow(mainText, portionToHighlight);
        entryLabel.setGraphic(highlightText);
        entryLabel.setPrefHeight(10);
        CustomMenuItem popupItem = new CustomMenuItem(entryLabel, true);
        return popupItem;
    }

    /**
     * Upon selection,
     * item text placed into textfield,
     * cursor set to back of command and
     * popup menu closed.
     * @param popUpItem
     * @param mainText
     */
    private void setOnSelection(CustomMenuItem popUpItem, String mainText) {
        popUpItem.setOnAction(actionEvent -> {
            setText(mainText);
            positionCaret(mainText.length());
            entriesPopup.hide();
        });
    }

    /**
     * Helps to autocomplete on ctrl key press
     * sets cursor position at.
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
     * Hide autocomplete results if needed.
     */
    public void hidePopUp() {
        if (entriesPopup.isShowing()) {
            entriesPopup.hide();
        }
    }
}
