package seedu.deliverymans.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final Logic logic;
    @FXML
    private AutocompletionTextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, Logic logic) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        this.logic = logic;

        setListener();
    }


    //@@author soilingrogue-reused
    //Reused from https://stackoverflow.com/questions/36861056/javafx-textfield-auto-suggestions

    /**
     * Setting listener for CommandBox text field.
     */
    private void setListener() {
        //Add "suggestions" by changing text
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String enteredText = commandTextField.getText();
            //always hide suggestion if nothing has been entered
            // (only "spacebars" are disallowed in TextFieldWithLengthLimit)
            if (enteredText == null || enteredText.isEmpty()) {
                commandTextField.hideEntriesPopup();
            } else {
                //Add autocomplete results to commandTextField entries
                LinkedList<String> temp = logic.getAutoCompleteResults(enteredText);
                commandTextField.getEntries().clear();
                commandTextField.getEntries().addAll(temp);

                int slash = enteredText.lastIndexOf("/");
                if (slash != -1) {
                    enteredText = enteredText.substring(slash + 1);
                }
                List<String> filteredEntries = getFilteredEntries(enteredText);

                //some suggestions are found
                if (!filteredEntries.isEmpty()) {
                    //build popup - list of "CustomMenuItem"
                    commandTextField.populatePopup(filteredEntries, enteredText);
                    if (!commandTextField.getEntriesPopup().isShowing()) { //optional
                        //position of popup
                        commandTextField.show();
                    }
                    //no suggestions -> hide
                } else {
                    commandTextField.hideEntriesPopup();
                }
            }
        });

        // Hide always by focus-in (optional) and out
        commandTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            commandTextField.hideEntriesPopup();
        });
    }

    /**
     * Filters the entries and returns a list of matching texts.
     */
    private List<String> getFilteredEntries(String enteredText) {
        return commandTextField.getEntries().stream()
                .filter(e -> e.toLowerCase().contains(enteredText.toLowerCase()))
                .collect(Collectors.toList());
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
         * @see seedu.deliverymans.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
