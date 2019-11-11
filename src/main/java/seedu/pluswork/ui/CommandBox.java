package seedu.pluswork.ui;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
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
     * The popup used to select an entry.
     */
    @FXML
    private ContextMenu entriesPopup;

    @FXML
    private TextField commandTextField;

    @FXML
    private Label label;

    /**
     * @param commandExecutor
     * @@author Caleb Brinkamn - reused
     * makes a new CommandBox adapted from Caleb Brinkman's AutoCompleteTextBox
     * https://gist.github.com/floralvikings/10290131
     */
    public CommandBox(CommandExecutor commandExecutor, Logic logic) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.logic = logic;
        entriesPopup = new ContextMenu();
        // calls #setStyleToDefault() whenever there is a change to the text of the
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        getSuggestions();
    }

    /**
     * moves the caret position of textbox to the next input field / prefix if present
     */

    public void handleShiftPressed() {
        commandTextField.requestFocus();
        int caret = commandTextField.getCaretPosition();
        int nextSlash = commandTextField.getText().indexOf("/", caret + 1);
        commandTextField.positionCaret(nextSlash + 1);
    }

    /**
     * returns list(on user interface) of the possible commands from the user input
     * only works for the command keyword and not the other parameters
     *
     * @param observableValue
     * @param s
     * @param s2
     */
    // adapted from group T12-2's autocomplete textfield architecture
    public void getSuggestions() {
        commandTextField.textProperty().addListener((observableValue, oldStr, newStr) -> {
            String text = commandTextField.getText().toLowerCase();
            if (text.length() == 0 || text == null) {
                entriesPopup.hide();
            } else {
                LinkedList<String> searchResult = logic.getAutoCompleteResults(text);
                populatePopup(searchResult);
                if (!entriesPopup.isShowing()) {
                    entriesPopup.show(this.commandTextField, Side.BOTTOM, commandTextField.getCaretPosition() * 8, 0);
                }
                if (searchResult.size() == 1 && searchResult.getFirst().equals(text)) {
                    entriesPopup.hide();
                }
            }
        });
        commandTextField.setOnKeyPressed(event -> {
                    LinkedList<String> searchResult = logic.getAutoCompleteResults(commandTextField.getText().toLowerCase());
                    populatePopup(searchResult);
                }
        );
    }

    /**
     * Populate the entry set with the given search results. Display is limited to
     * 5 entries, for performance.
     *
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);

            Label entryLabel = new Label();
            // boolean isPrefix = existingText.substring(0, caretPos).trim().endsWith("/");

            entryLabel.setText(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(actionEvent -> {
                commandTextField.setText(Keywords.getParameters(result));
                commandTextField.endOfNextWord();
                entriesPopup.hide();
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
