package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> implements EventHandler<KeyEvent> {

    private ArrayDeque<String> pastCommands = new ArrayDeque<>();
    private ArrayDeque<String> nextCommands = new ArrayDeque<>();
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            String command = commandTextField.getText();
            while (!nextCommands.isEmpty()) {
                pastCommands.push(nextCommands.pop());
            }
            pastCommands.push(command);
            commandExecutor.execute(command);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * handles all key presses
     * keycode of key pressed sent to keyPressed
     */
    @FXML
    private void handleKeyPress() {
        commandTextField.setOnKeyPressed(event -> keyPressed(event.getCode()));
    }


    private void keyPressed(KeyCode keyCode){
        /**
         * sets textfield according to key press
         * for up and down arrow, handles previous and next commands
         * with 2 stacks, enabling user to scroll through past commands
         * if no more past or future commands, the textfield will be blank
         *
         * @param keyCode
         */
        if (keyCode == KeyCode.UP ){
            try {
                String previousCommand = pastCommands.pop();
                nextCommands.push(previousCommand);
                commandTextField.setText((previousCommand));
            } catch(NoSuchElementException ex) {
                commandTextField.setText((""));
            }
        }
        if (keyCode == KeyCode.DOWN) {
            try {
                String nextCommand = nextCommands.pop();
                pastCommands.push(nextCommand);
                commandTextField.setText((nextCommand));
            } catch(NoSuchElementException ex) {
                commandTextField.setText((""));
            }
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

    @Override
    public void handle(KeyEvent event) {
        commandTextField.setText("hi");
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
