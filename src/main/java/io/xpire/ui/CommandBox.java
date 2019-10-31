package io.xpire.ui;

import static io.xpire.commons.core.Messages.MESSAGE_MAXIMUM_INPUT_LENGTH_EXCEEDED;

import java.util.function.UnaryOperator;

import io.xpire.logic.Logic;
import io.xpire.logic.commands.CommandResult;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.logic.parser.exceptions.ParseException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Region;


/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    public static final int MAX_LENGTH = 60;
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private ResultDisplay resultDisplay;

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

        /*
        String[] possibleWords = {"sort", "set reminder", "exit", "clear", "add", "delete", "search", "view", "help",
        "check", "tag"};
        TextFields.bindAutoCompletion(commandTextField, suggestions);
         */
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
                    resultDisplay.setFeedbackToUser(MESSAGE_MAXIMUM_INPUT_LENGTH_EXCEEDED);
                    setStyleToIndicateCommandFailure();
                }
            }
            return c;
        };
        this.commandTextField.setTextFormatter(new TextFormatter(modifyChange));
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
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
