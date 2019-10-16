package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final SyntaxHighlightTextArea syntaxHighlightTextArea;


    @FXML
    private StackPane commandInputAreaPlaceholder;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        syntaxHighlightTextArea = new SyntaxHighlightTextArea();
        syntaxHighlightTextArea.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        commandInputAreaPlaceholder.getChildren().add(syntaxHighlightTextArea);

        syntaxHighlightTextArea.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                handleCommandEntered();
            }
        });
    }

    public void importSyntaxStyleSheet(Scene scene) {
        syntaxHighlightTextArea.importStyleSheet(scene);
    }

    /**
     * Handles the Enter button pressed event.
     */
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(syntaxHighlightTextArea.getText());
            syntaxHighlightTextArea.clear();
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        // enable syntax highlighting
        syntaxHighlightTextArea.watch();
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        //override style and disable syntax highlighting
        syntaxHighlightTextArea.overrideStyle(ERROR_STYLE_CLASS);
    }

    public void addSyntax(String com, List<Prefix> pre, String syntax) {
        syntaxHighlightTextArea.createPattern(com, pre, syntax);
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
