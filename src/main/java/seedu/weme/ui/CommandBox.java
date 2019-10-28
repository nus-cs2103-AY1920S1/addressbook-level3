package seedu.weme.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.prompter.exceptions.PromptException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final CommandPrompter commandPrompter;

    private boolean isShowingCommandSuccess = false;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, CommandPrompter commandPrompter) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandPrompter = commandPrompter;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();
            if (!isShowingCommandSuccess) {
                displayCommandPrompt();
            } else {
                isShowingCommandSuccess = false;
            }
        });
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, this::handleTabPress);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            isShowingCommandSuccess = true;
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles the tab key press event.
     */
    private void handleTabPress(KeyEvent event) {
        if (event.getCode().equals(KeyCode.TAB)) {
            try {
                commandTextField.setText(commandPrompter.execute(commandTextField.getText()));
            } catch (PromptException e) {
                setStyleToIndicateCommandFailure();
            } finally {
                commandTextField.positionCaret(commandTextField.getText().length());
                event.consume();
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
     * Display the command prompt in the result box.
     */
    private void displayCommandPrompt() {
        try {
            commandPrompter.execute(commandTextField.getText());
        } catch (PromptException e) {
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
         * @see seedu.weme.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    /**
     * Represents a function that can auto-prompt commands.
     */
    @FunctionalInterface
    public interface CommandPrompter {
        /**
         * Parse the user input and display the suggestions in ResultDisplay.
         * @param userInput text input from CommandBox
         * @return String complete command for auto-completion
         */
        String execute(String userInput) throws PromptException;
    }

}
