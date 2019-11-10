package seedu.weme.ui;

import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;

import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.createcommand.TextMoveCommand;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.logic.commands.memecommand.MemeDislikeCommand;
import seedu.weme.logic.commands.memecommand.MemeLikeCommand;
import seedu.weme.logic.parser.contextparser.WemeParser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.CliSyntax;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.model.ModelContext;
import seedu.weme.model.meme.Meme;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final int BASE_INDEX = 1;
    private static final Set<KeyCode> ARROW_KEYS = Set.of(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT);
    private static final Logger logger = LogsCenter.getLogger(CommandBox.class);

    private final CommandExecutor commandExecutor;
    private final CommandPrompter commandPrompter;
    private final ObservableList<Meme> memeFilteredList;
    private final ObservableValue<ModelContext> context;
    private boolean isShowingCommandSuccess = false;
    private long memeTextLastMoved = 0;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor,
                      CommandPrompter commandPrompter,
                      ObservableList<Meme> memeFilteredList,
                      ObservableValue<ModelContext> context) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandPrompter = commandPrompter;
        this.memeFilteredList = memeFilteredList;
        this.context = context;
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
        case DOWN:
        case LEFT:
        case RIGHT:
            handleArrowKeyPress(keyEvent);
            break;
        case TAB:
            handleTabPress(keyEvent);
            break;
        default:
        }
    }

    /**
     * Handles arrow key press events.
     * Offloads the real handling to the appropriate handlers.
     *
     * @param event an arrow key press event
     */
    private void handleArrowKeyPress(KeyEvent event) {
        assert ARROW_KEYS.contains(event.getCode());

        // Do nothing if caret is not at the end of the text
        if (commandTextField.getCaretPosition() != commandTextField.getText().length()) {
            return;
        }

        logger.info("Handling " + event.getCode() + " arrow key press");
        if (handleLikeByKeyPress(event)
                || handleIndexToggleByKeyPress(event)
                || handleMovingTextUsingKeyPress(event)) {
            logger.info("Successfully handled " + event.getCode() + " arrow key press");
            commandTextField.positionCaret(commandTextField.getText().length());
            event.consume();
        } else {
            logger.info("No action dispatched for " + event.getCode() + " arrow key press");
        }
    }

    /**
     * Changes index in the command box with key press to enable fast liking.
     *
     * @return whether this event is handled
     */
    private boolean handleIndexToggleByKeyPress(KeyEvent event) {
        // Only handle left and right key press
        if (!(event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.RIGHT))) {
            return false;
        }

        if (!context.getValue().equals(ModelContext.CONTEXT_MEMES)) {
            return false;
        }

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(commandTextField.getText().trim());
        // Check if the input is a valid command
        if (!matcher.matches()) {
            return false;
        }

        if (!(matcher.group(COMMAND_WORD).equals(MemeLikeCommand.COMMAND_WORD)
                || matcher.group(COMMAND_WORD).equals(MemeDislikeCommand.COMMAND_WORD))) {
            // Do not handle if the command word is not a like command.
            return false;
        } else {
            final String commandWord = matcher.group(WemeParser.COMMAND_WORD);
            final String argument = matcher.group(WemeParser.ARGUMENTS).trim();
            if (argument.isBlank()) {
                return false;
            }
            int change = event.getCode().equals(KeyCode.LEFT) ? -1 : 1;
            int currentIndex = Integer.parseInt(argument.trim());
            int newIndex = currentIndex + change;

            commandTextField.setText(commandWord + " "
                    + (Math.min(Math.max(newIndex, BASE_INDEX), memeFilteredList.size())));
            return true;
        }
    }

    /**
     * Handles like command in the form of key press.
     *
     * @return whether this event is handled
     */
    private boolean handleLikeByKeyPress(KeyEvent event) {
        if (!context.getValue().equals(ModelContext.CONTEXT_MEMES)) {
            return false;
        }

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(commandTextField.getText().trim());
        if (!matcher.matches()) {
            return false;
        }

        final String commandWord = matcher.group(WemeParser.COMMAND_WORD);
        if ((commandWord.equals(MemeLikeCommand.COMMAND_WORD)
                || commandWord.equals(MemeDislikeCommand.COMMAND_WORD))
                && event.getCode().equals(KeyCode.UP)) {
            try {
                commandExecutor.execute(commandTextField.getText());
                return true;
            } catch (CommandException | ParseException e) {
                setStyleToIndicateCommandFailure();
                return false;
            }
        }
        return false;
    }

    /**
     * Handles moving text using arrow keys when creating memes.
     *
     * @param event an arrow key press event
     * @return whether this event is handled
     */
    private boolean handleMovingTextUsingKeyPress(KeyEvent event) {
        if (!ARROW_KEYS.contains(event.getCode())) {
            return false;
        }

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(commandTextField.getText().trim());
        if (!matcher.matches()) {
            return false;
        }

        // Limit key move rate
        if (System.currentTimeMillis() - memeTextLastMoved < 75) {
            return true;
        } else {
            memeTextLastMoved = System.currentTimeMillis();
        }

        final String commandWord = matcher.group(WemeParser.COMMAND_WORD);
        final String arguments = matcher.group(WemeParser.ARGUMENTS).trim();
        if (commandWord.equals(TextMoveCommand.COMMAND_WORD) && arguments.matches("\\d+")) {
            try {
                setStyleToDefault();
                float multiplier = event.isShiftDown() ? 5 : event.isAltDown() ? 0.2f : 1;
                float distance = TextMoveCommand.DEFAULT_MOVE_DISTANCE * multiplier;
                switch (event.getCode()) {
                case UP:
                    commandExecutor.execute(commandTextField.getText() + " "
                            + CliSyntax.PREFIX_Y_COORDINATE_STRING + "-" + distance);
                    return true;
                case DOWN:
                    commandExecutor.execute(commandTextField.getText() + " "
                            + CliSyntax.PREFIX_Y_COORDINATE_STRING + distance);
                    return true;
                case LEFT:
                    commandExecutor.execute(commandTextField.getText() + " "
                            + CliSyntax.PREFIX_X_COORDINATE_STRING + "-" + distance);
                    return true;
                case RIGHT:
                    commandExecutor.execute(commandTextField.getText() + " "
                            + CliSyntax.PREFIX_X_COORDINATE_STRING + distance);
                    return true;
                default:
                    return false;
                }
            } catch (CommandException | ParseException e) {
                setStyleToIndicateCommandFailure();
            }
            return true;
        }
        return false;
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
         *
         * @param userInput text input from CommandBox
         * @return String complete command for auto-completion
         */
        String execute(String userInput) throws PromptException;
    }

}
