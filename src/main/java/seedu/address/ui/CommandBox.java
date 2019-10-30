package seedu.address.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.geometry.Side;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.commons.Keywords;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    List<String> commandList = Keywords.commandList;

    private final CommandExecutor commandExecutor;

    /** The existing autocomplete entries. */
    private final SortedSet<String> entries;

    /** The popup used to select an entry. */
    private ContextMenu entriesPopup;

    @FXML
    private TextField commandTextField;

    /**
     * makes a new CommandBox adapted from Caleb Brinkman's AutoCompleteTextBox
     * https://gist.github.com/floralvikings/10290131
     *
     * @param commandExecutor
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        entries = new TreeSet<>(commandList);
        entriesPopup = new ContextMenu();
        // calls #setStyleToDefault() whenever there is a change to the text of the
        // command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        commandTextField.textProperty()
                .addListener((observableValue, oldStr, newStr) -> getSuggestions(observableValue, oldStr, newStr));

    }

    /**
     * returns list(on user interface) of the possible commands from the user input
     * only works for the command keyword and not the other parameters
     *
     * @param observableValue
     * @param s
     * @param s2
     */

    public void getSuggestions(ObservableValue<? extends String> observableValue, String s, String s2) {
        String text = commandTextField.getText().toLowerCase();
        if (text.length() == 0) {
            entriesPopup.hide();
        } else {
            LinkedList<String> searchResult = new LinkedList<>();
            searchResult.addAll(entries.subSet(text, text + Character.MAX_VALUE));
            //show the list of suggestions if user input is a substring
            if (entries.size() > 0) {
                populatePopup(searchResult);
                if (!entriesPopup.isShowing()) {
                    entriesPopup.show(this.commandTextField, Side.BOTTOM, 0, 0);
                }
            } else {
                entriesPopup.hide();
            }
        }
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
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    commandTextField.setText(Keywords.getParameters(result));
                    //this line moves the cursor to the end after choosing a drop-down option
                    commandTextField.endOfNextWord();
                    entriesPopup.hide();
                }
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);

    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
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
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
