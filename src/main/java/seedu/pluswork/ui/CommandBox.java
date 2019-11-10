package seedu.pluswork.ui;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.pluswork.commons.Keywords;
import seedu.pluswork.logic.Logic;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private Logic logic;

    /**
     * The existing autocomplete entries.
     */
    private final SortedSet<String> entries;

    /**
     * The popup used to select an entry.
     */
    @FXML
    private ContextMenu entriesPopup;

    @FXML
    private TextField commandTextField;

    @FXML
    private Label label;

    /**
     * makes a new CommandBox adapted from Caleb Brinkman's AutoCompleteTextBox
     * https://gist.github.com/floralvikings/10290131
     *
     * @param commandExecutor
     */
    public CommandBox(CommandExecutor commandExecutor, Logic logic) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.logic = logic;
        entries = new TreeSet<>();
        entriesPopup = new ContextMenu();
        // calls #setStyleToDefault() whenever there is a change to the text of the
        // command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        getSuggestions();

    }

    /**
     * returns list(on user interface) of the possible commands from the user input
     * only works for the command keyword and not the other parameters
     *
     * @param observableValue
     * @param s
     * @param s2
     */
    // adapted from group T12-2
    public void getSuggestions() {
        commandTextField.textProperty().addListener((observableValue, oldStr, newStr) -> {
            String text = commandTextField.getText().toLowerCase();
            if (text.length() == 0 || text == null) {
                entriesPopup.hide();
            } else {
                LinkedList<String> searchResult = logic.getAutoCompleteResults(text);
                populatePopup(searchResult);
                if (!entriesPopup.isShowing()) {
                    entriesPopup.show(this.commandTextField, Side.BOTTOM, 0, 0);
                }
                if (searchResult.size() == 1 && searchResult.getFirst().equals(text)) {
                    entriesPopup.hide();
                }
            }
        });
    }

    /**
     * Get the existing set of autocomplete entries.
     *
     * @return The existing autocomplete entries.
     */
    public SortedSet<String> getEntries() {
        return entries;
    }

    /**
     * Populate the entry set with the given search results. Display is limited to
     * 5 entries, for performance.
     *
     * @param searchResult The set of matching strings.
     */

    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 5;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            String existingText = commandTextField.getText();
            final String result = searchResult.get(i);
            Label entryLabel = new Label();
            entryLabel.setGraphic(buildTextFlow(result, existingText));
            entryLabel.setPrefHeight(10);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(actionEvent -> {
                    commandTextField.setText(Keywords.getParameters(result));
                    System.out.println(Keywords.getParameters(result));
                    //this line moves the cursor to the end after choosing a drop-down option
                    commandTextField.endOfNextWord();
                    entriesPopup.hide();
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);

    }

    /**
     * Build TextFlow with selected text. Return "case" dependent.
     *
     * @param text   - string with text
     * @param filter - string to select in text
     * @return - TextFlow
     */
    private static TextFlow buildTextFlow(String text, String filter) {
        if (filter.equals("")) {
            Text toReturn = new Text(text);
            toReturn.setFill(Color.BLACK);
            // toReturn.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
            return new TextFlow(toReturn);
        }
        //System.out.println(filter);
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        //instead of "filter" to keep all "case sensitive"
        Text textFilter = new Text(text.substring(filterIndex, filterIndex + filter.length()));
        textFilter.setFill(Color.web("2eb8b8"));
        // textFilter.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
        return new TextFlow(textBefore, textFilter, textAfter);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException | FileNotFoundException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.pluswork.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException, FileNotFoundException;
    }

    /**
     * Interface to allow other components to update the text inside {@CommandBox}
     *
     * @param text
     */
    public void setCommandText(String text) {
        commandTextField.setText(text);
    }

}
