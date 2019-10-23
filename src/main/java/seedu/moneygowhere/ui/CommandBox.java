package seedu.moneygowhere.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import seedu.moneygowhere.logic.commands.CommandResult;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final PreviousCommand prevCommand;
    private final NextCommand nextCommand;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, PreviousCommand prevCommand, NextCommand nextCommand) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.prevCommand = prevCommand;
        this.nextCommand = nextCommand;

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        commandTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.valueOf("UP")) {
                handleUp();
            }
            if (event.getCode() == KeyCode.valueOf("DOWN")) {
                handleDown();
            }
        });

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
     * Handles when the up button was pressed.
     * Retrieves the previously stored command with respect to the current index.
     */
    private void handleUp() {
        commandTextField.setText(prevCommand.getPrevCommand());
        commandTextField.end();
    }

    /**
     * Handles when the down button was pressed.
     * Retrieves the next stored command with respect to the current index.
     */
    private void handleDown() {
        commandTextField.setText(nextCommand.getNextCommand());
        commandTextField.end();
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
         * @see seedu.moneygowhere.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    /**
     * Represents a function to get previous commands.
     */
    @FunctionalInterface
    public interface PreviousCommand {
        /**
         * Gets the previously stored command.
         *
         * @see seedu.moneygowhere.logic.Logic#getPrevCommand()
         */
        String getPrevCommand();
    }

    /**
     * Represents a function to get previous commands.
     */
    @FunctionalInterface
    public interface NextCommand {
        /**
         * Gets the previously stored command.
         *
         * @see seedu.moneygowhere.logic.Logic#getNextCommand()
         */
        String getNextCommand();
    }
}
