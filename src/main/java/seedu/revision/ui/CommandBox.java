package seedu.revision.ui;

import java.io.IOException;

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

    private AutoComplete autoComplete;

    /**
     * The text box in the UI.
     * @param commandExecutor To execute the command.
     * @param mainWindow Is to see if commandBox is in main or quiz mode.
     */
    public CommandBox(CommandExecutor commandExecutor, boolean mainWindow) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        if (mainWindow) {
            autoComplete = new AutoComplete();
            autoComplete.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault(true));
            autoComplete.setId("commandTextField");

            autoComplete.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    handleCommandEntered(true);
                }
            });

            commandBox.getChildren().add(autoComplete);
        } else {
            commandTextField = new TextField();
            commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault(false));
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
        if (mainWindow) {
            try {
                commandExecutor.execute(autoComplete.getText());
                autoComplete.setText("");
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
    }

    /**
     * Sets the command box style to use the default style.
     * @param mainWindow Is to see if commandBox is in main or quiz mode.
     */
    private void setStyleToDefault(Boolean mainWindow) {
        if (mainWindow) {
            autoComplete.getStyleClass().remove(ERROR_STYLE_CLASS);
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
            styleClass = autoComplete.getStyleClass();
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
