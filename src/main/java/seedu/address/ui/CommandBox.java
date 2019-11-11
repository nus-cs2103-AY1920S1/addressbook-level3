package seedu.address.ui;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> implements EventHandler<KeyEvent> {


    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final Logger logger = LogsCenter.getLogger(getClass());

    private History history = new History();
    private ResultDisplay resultDisplay;
    private boolean isAutoCompleteOn = true;

    @FXML
    private AutoCompleteTextField commandTextField;


    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        assert commandTextField != null : "Textfield must be initialised by now.";
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    public void linkResultsDisplay(ResultDisplay resultDisplay) {
        assert resultDisplay != null: "Result Display cannot be null!";
        this.resultDisplay = resultDisplay;
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            String command = commandTextField.getText();
            if (command.equals("exit")) {
                commandTextField.shutDownListener();
            }
            history.sendToHistory(command);
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

    //@@author SebastianLie
    /**
     * handles keypresses for GUI commands, like history or autocomplete
     * @param keyCode
     */
    private void keyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.UP) {
            try {
                String previousCommand = history.getPastCommand();
                commandTextField.setText((previousCommand));
                commandTextField.positionCaret(previousCommand.length());
            } catch (NoSuchElementException ex) {
                commandTextField.setText((""));
            }
        }
        if (keyCode == KeyCode.DOWN) {
            try {
                String nextCommand = history.getNextCommand();
                commandTextField.setText((nextCommand));
                commandTextField.positionCaret(nextCommand.length());
            } catch (NoSuchElementException ex) {
                commandTextField.setText((""));
            }
        }
        if (keyCode == KeyCode.CONTROL) {
            if (isAutoCompleteOn) {
                try {
                    commandTextField.setAutoCompleteResult();
                } catch (Exception e) {
                    logger.info("Exception thrown from autocomplete.");
                }
            }
            else {
                commandTextField.initListener();
                isAutoCompleteOn = true;
                resultDisplay.setFeedbackToUser("AutoComplete feature turned on.");
            }
        }
        if (keyCode == KeyCode.ESCAPE) {
            if (isAutoCompleteOn) {
                commandTextField.hidePopUp();
                commandTextField.shutDownListener();
                isAutoCompleteOn = false;
                resultDisplay.setFeedbackToUser("AutoComplete feature turned off.");
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
