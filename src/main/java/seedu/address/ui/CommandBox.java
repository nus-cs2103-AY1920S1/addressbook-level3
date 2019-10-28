package seedu.address.ui;

import java.util.Collections;
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
import seedu.address.ui.CustomTextField.CommandTextField;
import seedu.address.ui.panel.exceptions.UnmappedPanelException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final CommandTextField commandTextField;


    @FXML
    private StackPane commandInputAreaPlaceholder;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField = new CommandTextField();
        commandTextField.textProperty()
                .addListener((unused1, unused2, unused3) -> setStyleToDefault());
        commandInputAreaPlaceholder.getChildren().add(commandTextField);

        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                handleCommandEntered();
            }
        });
    }

    public void importSyntaxStyleSheet(Scene scene) {
        commandTextField.importStyleSheet(scene);
    }

    /**
     * Handles the Enter button pressed event.
     */
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.clear();
        } catch (CommandException | ParseException | UnmappedPanelException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        // enable syntax highlighting
        commandTextField.enableSyntaxHighlighting();
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        //override style and disable syntax highlighting
        commandTextField.overrideStyle(ERROR_STYLE_CLASS);
    }

    /**
     * Adds a command to enable syntax highlighting for
     * @param com The command word of the command
     * @param pre The prefix of the command
     */
    public void enableSyntaxHighlightingForCommand(String com, List<Prefix> pre) {
        commandTextField.createPattern(com, pre, Collections.EMPTY_LIST);
    }

    /**
     * Disable syntax highlighting for the specified command.
     * @param command The command word of the command.
     */
    public void disableSyntaxHighlightingForCommand(String command) {
        commandTextField.removePattern(command);
    }

    public void enableSyntaxHighlighting() {
        commandTextField.enableSyntaxHighlighting();
    }


    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String, String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException, UnmappedPanelException;
    }

}
