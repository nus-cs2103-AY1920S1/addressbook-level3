package io.xpire.ui;

import java.util.Objects;
import java.util.function.UnaryOperator;

import io.xpire.logic.Logic;
import io.xpire.logic.commands.CommandResult;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.history.HistoryManager;
import io.xpire.ui.history.InputHistoryManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String MESSAGE_MAXIMUM_INPUT_LENGTH_REACHED = "Maximum input length is 60 characters";
    public static final String MESSAGE_MAXIMUM_INPUT_RETRIEVAL_REACHED = "There are no previous inputs to retrieve.\n"
            + "(Up to 20 inputs can be saved!)";

    public static final String ERROR_STYLE_CLASS = "error";
    public static final int MAX_LENGTH = 60;
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private ResultDisplay resultDisplay;
    private HistoryManager<String> inputHistoryManager = new InputHistoryManager<>();

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, ResultDisplay resultDisplay) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.resultDisplay = resultDisplay;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        this.checkLength();
        commandTextField.setOnMouseClicked(e -> commandTextField.requestFocus());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String input = commandTextField.getText();
        if (!input.isBlank()) {
            inputHistoryManager.save(input);
        }
        try {
            commandExecutor.execute(input);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    //@@author xiaoyu-nus
    /**
     * Returns whether the commandBox has received a wrong command.
     */
    private boolean isStyleFailure() {
        return commandTextField.getStyleClass().contains(ERROR_STYLE_CLASS);
    }

    /**
     * Handles event when a keyboard key is pressed.
     * @param e event fired by the keyboard.
     */
    @FXML
    private void handleKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.UP) {
            handleUpKey();
        } else if (e.getCode() == KeyCode.DOWN) {
            handleDownKey();
        }
    }

    /**
     * Handles an up key event by retrieving the previous entered command.
     */
    private void handleUpKey() {
        String previousInput = inputHistoryManager.previous();
        if (previousInput == null) {
            resultDisplay.setFeedbackToUser(MESSAGE_MAXIMUM_INPUT_RETRIEVAL_REACHED);
        } else {
            resultDisplay.setFeedbackToUser("");
            if (isStyleFailure()) {
                // need to jump twice since the error message is not wiped from the command box.
                previousInput = inputHistoryManager.previous();
            }
            commandTextField.setText(previousInput);
        }
        commandTextField.end();
    }

    /**
     * Handles a down key event by retrieving the next entered command after the current command.
     */
    private void handleDownKey() {
        String nextInput = inputHistoryManager.next();
        resultDisplay.setFeedbackToUser("");
        commandTextField.setText(Objects.requireNonNullElse(nextInput, ""));
        commandTextField.end();
    }

    /**
     * Checks whether user input command length exceeds the maximum length.
     */
    private void checkLength() {
        // Adapted from https://stackoverflow.com/questions/15159988/javafx-2-2-textfield-maxlength
        UnaryOperator<TextFormatter.Change> modifyChange = c -> {
            if (c.isContentChange()) {
                int newLength = c.getControlNewText().length();
                if (newLength > MAX_LENGTH) {
                    String tail = c.getControlNewText().substring(0, MAX_LENGTH);
                    c.setText(tail);
                    int oldLength = c.getControlText().length();
                    c.setRange(0, oldLength);
                    resultDisplay.setFeedbackToUser(MESSAGE_MAXIMUM_INPUT_LENGTH_REACHED);
                    setStyleToIndicateCommandFailure();
                }
            }
            return c;
        };
        this.commandTextField.setTextFormatter(new TextFormatter<>(modifyChange));
    }
    //@@author

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
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
