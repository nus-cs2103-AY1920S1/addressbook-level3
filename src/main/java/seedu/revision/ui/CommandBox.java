package seedu.revision.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.revision.logic.MainLogic;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;

import java.io.IOException;

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

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        autoComplete = new AutoComplete();
        autoComplete.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        autoComplete.setId("commandTextField");

        autoComplete.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                handleCommandEntered();
            } else if (keyEvent.getCode() == KeyCode.TAB) {
                autoComplete.handleAutocomplete();
                keyEvent.consume();
            }
        });
        commandBox.getChildren().add(autoComplete);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(autoComplete.getText());
            autoComplete.setText("");
        } catch (CommandException | ParseException | IOException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        autoComplete.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = autoComplete.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    public TextField getCommandTextField() {
        return autoComplete;
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see MainLogic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException, IOException;
    }

}
