package seedu.address.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.logic.AutoCompleteResult;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.exception.EnumNotPresentException;


/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final Logic logic;

    @FXML
    private AutoCompleteTextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, Logic logic) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.logic = logic;

        addListener();
    }

    /**
     * Adds a listener to extract the input text on change for autocomplete.
     */
    private void addListener() {
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // calls #setStyleToDefault() whenever there is a change to the text of the command box.
            commandTextField.setStyleToDefault();
            String enteredText = commandTextField.getText();
            // hide suggestions if no input
            if (enteredText == null || enteredText.isEmpty()) {
                commandTextField.getEntriesPopup().hide();
            } else {
                AutoCompleteResult result = logic.getAutocompleteValues(enteredText);

                // add possible values
                commandTextField.getEntries().clear();
                commandTextField.getEntries().addAll(result.getValues());

                // filter
                String stringToCompare = result.getStringToCompare();
                List<String> filteredEntries = commandTextField.getEntries().stream()
                        .filter(e -> e.toLowerCase().contains(stringToCompare.toLowerCase()))
                        .sorted((e1, e2) -> commandTextField.compareEntries(e1, e2, stringToCompare))
                        .collect(Collectors.toList());
                if (!filteredEntries.isEmpty() && !filteredEntries.contains(stringToCompare)) {
                    commandTextField.populatePopup(filteredEntries, stringToCompare);
                    commandTextField.refreshDropdown();
                } else {
                    commandTextField.getEntriesPopup().hide();
                }
            }
        });

        commandTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            commandTextField.getEntriesPopup().hide();
        }));
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException | EnumNotPresentException e) {
            setStyleToIndicateCommandFailure();
        }
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
        CommandResult execute(String commandText) throws CommandException, ParseException, EnumNotPresentException;
    }

}
