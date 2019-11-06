package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyModulePlanner;


/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private StackPane commandBox;

    private AutocompleteTextField autocompleteTextField;

    public CommandBox(CommandExecutor commandExecutor, ReadOnlyModulePlanner modulePlanner) {
        super(FXML);
        requireNonNull(commandExecutor);
        requireNonNull(modulePlanner);
        this.commandExecutor = commandExecutor;
        autocompleteTextField = new AutocompleteTextField(modulePlanner);
        autocompleteTextField.setPromptText("Type here...");
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        autocompleteTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        autocompleteTextField.setId("commandTextField");
        autocompleteTextField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                logger.info("Command entered.");
                handleCommandEntered();
            } else if (keyEvent.getCode() == KeyCode.TAB) {
                logger.info("Autocomplete requested.");
                autocompleteTextField.handleAutocomplete();
                keyEvent.consume();
            }
        });
        commandBox.getChildren().add(autocompleteTextField);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(autocompleteTextField.getText());
            autocompleteTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        autocompleteTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = autocompleteTextField.getStyleClass();

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

    /**
     * Resets the autocomplete when there is a change in active study plan.
     */
    public void handleChangeOfActiveStudyPlan() {
        autocompleteTextField.handleChangeOfActiveStudyPlan();
    }
}
