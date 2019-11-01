package seedu.deliverymans.ui;

import java.util.LinkedList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.model.trie.TrieManager;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
class CommandBox extends UiPart<Region> {

    private static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final TrieManager trieManager;
    private final AutocompletionTextField field;

    @FXML
    private TextField commandTextField;

    CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        this.trieManager = new TrieManager();
        this.field = new AutocompletionTextField();
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
     * Handles the Key pressed event.
     */
    @FXML
    private void handleKeyPressed() {
        LinkedList<String> temp = trieManager.getAutoCompleteResults(commandTextField.getText(),
                MainWindow.getContext());
        if (!temp.isEmpty()) {
            for (String s : temp) {
                System.out.println(s);
            }
        }
        field.getEntries().clear();
        field.getEntries().addAll(temp);
        // System.out.println(commandTextField.getText());
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
         * @see seedu.deliverymans.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
