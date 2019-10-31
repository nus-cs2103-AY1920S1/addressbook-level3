package seedu.weme.ui;

import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;

import java.util.regex.Matcher;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.logic.commands.memecommand.MemeDislikeCommand;
import seedu.weme.logic.commands.memecommand.MemeLikeCommand;
import seedu.weme.logic.parser.contextparser.WemeParser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.model.meme.Meme;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final int BASE_INDEX = 1;

    private final CommandExecutor commandExecutor;
    private final CommandPrompter commandPrompter;

    private boolean isShowingCommandSuccess = false;
    private ObservableList<Meme> memeFilteredList;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor,
                      CommandPrompter commandPrompter,
                      ObservableList<Meme> memeFilteredList) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandPrompter = commandPrompter;
        this.memeFilteredList = memeFilteredList;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();
            if (!isShowingCommandSuccess) {
                displayCommandPrompt();
            } else {
                isShowingCommandSuccess = false;
            }
        });
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
        case DOWN:
            handleLikeByKeyPress(keyEvent);
            break;
        case LEFT:
        case RIGHT:
            handleIndexToggleByKeyPress(keyEvent);
            break;
        case TAB:
            handleTabPress(keyEvent);
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Changes index in the command box with key press to enable fast liking.
     */
    private void handleIndexToggleByKeyPress(KeyEvent event) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(commandTextField.getText().trim());
        // Check if the input is a valid command
        if (!matcher.matches()) {
            return;
        }

        if (!matcher.group(COMMAND_WORD).equals(MemeLikeCommand.COMMAND_WORD)) {
            // Do not handle if the command word is not a like command.
            return;
        } else {
            final String commandWord = matcher.group(WemeParser.COMMAND_WORD);
            final String argument = matcher.group(WemeParser.ARGUMENTS);
            int change = event.getCode().equals(KeyCode.LEFT) ? -1 : 1;
            int currentIndex = Integer.parseInt(argument.trim());
            int newIndex = currentIndex + change;

            commandTextField.setText(commandWord + " "
                    + (Math.min(Math.max(newIndex, BASE_INDEX), memeFilteredList.size())));
        }
        commandTextField.positionCaret(commandTextField.getText().length());
        event.consume();
    }

    /**
     * Handles like command in the form of key press.
     */
    private void handleLikeByKeyPress(KeyEvent event) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(commandTextField.getText().trim());
        if (matcher.matches()) {
            try {
                final String commandWord = matcher.group(WemeParser.COMMAND_WORD);
                if (commandWord.equals(MemeLikeCommand.COMMAND_WORD)
                        && event.getCode().equals(KeyCode.UP)
                        || commandWord.equals(MemeDislikeCommand.COMMAND_WORD)
                        && event.getCode().equals(KeyCode.DOWN)) {
                    commandExecutor.execute(commandTextField.getText());
                }
            } catch (CommandException | ParseException e) {
                setStyleToIndicateCommandFailure();
            } finally {
                commandTextField.positionCaret(commandTextField.getText().length());
                event.consume();
            }
        }
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
