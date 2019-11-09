package seedu.revision.ui;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.revision.logic.Logic;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    @FXML
    private StackPane commandBox;

    private AutoComplete autoCompleteField;

    /**
     * The text box in the UI.
     * @param commandExecutor To execute the command.
     * @param mainWindow Is to see if commandBox is in main or quiz mode.
     */
    public CommandBox(CommandExecutor commandExecutor, boolean mainWindow) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        if (mainWindow) {
            autoCompleteField = new AutoComplete();
            autoCompleteField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault(true));
            autoCompleteField.getStyleClass().add("commandTextField");

            autoCompleteField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    handleCommandEntered(true);
                }
            });

            commandBox.getChildren().add(autoCompleteField);
        } else {
            commandTextField = new TextField();
            commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault(false));
            commandTextField.getStyleClass().add("commandTextField");
            commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    handleCommandEntered(false);
                }
            });
            commandBox.getChildren().add(commandTextField);
        }
    }

    /**
     * Handles the Enter button pressed event.
     * @param mainWindow Is to see if commandBox is in main or quiz mode.
     */
    @FXML
    private void handleCommandEntered(Boolean mainWindow) {
        Platform.runLater(() -> {
            if (mainWindow) {
                try {
                    commandExecutor.execute(autoCompleteField.getText());
                    autoCompleteField.setText("");
                } catch (CommandException | ParseException | IOException e) {
                    setStyleToIndicateCommandFailure(true);
                }
            } else {
                try {
                    commandExecutor.execute(commandTextField.getText());
                    commandTextField.setText("");
                } catch (CommandException | ParseException | IOException e) {
                    setStyleToIndicateCommandFailure(false);
                }
            }
        });
    }

    /**
     * Sets the command box style to use the default style.
     * @param mainWindow Is to see if commandBox is in main or quiz mode.
     */
    private void setStyleToDefault(Boolean mainWindow) {
        if (mainWindow) {
            autoCompleteField.getStyleClass().remove(ERROR_STYLE_CLASS);
        } else {
            commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
        }
    }

    /**
     * Sets the command box style to indicate a failed command.
     * @param mainWindow Is to see if commandBox is in main or quiz mode.
     */
    private void setStyleToIndicateCommandFailure(Boolean mainWindow) {
        ObservableList<String> styleClass;
        if (mainWindow) {
            styleClass = autoCompleteField.getStyleClass();
        } else {
            styleClass = commandTextField.getStyleClass();
        }
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    public TextField getCommandTextField() {
        return commandTextField;
    }

    public AutoComplete getAutoCompleteField() {
        return autoCompleteField;
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException, IOException;
    }

}
