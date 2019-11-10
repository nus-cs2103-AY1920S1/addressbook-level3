package seedu.moolah.ui.textfield;

import static seedu.moolah.ui.textfield.CommandTextField.ERROR_STYLE_CLASS;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.logic.parser.Prefix;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.ui.UiPart;
import seedu.moolah.ui.panel.exceptions.UnmappedPanelException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    private static final String FXML = "CommandBox.fxml";
    final CommandTextField commandTextField;
    private final CommandExecutor commandExecutor;

    @FXML
    private StackPane commandInputAreaPlaceholder;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField = new CommandTextField(this::handleCommandEntered);
        commandTextField
                .textProperty()
                .addListener((unused1, unused2, unused3) -> setStyleToDefault());
        commandInputAreaPlaceholder.getChildren().add(commandTextField);
    }


    /**
     * Handles the Enter button pressed event.
     */
    private void handleCommandEntered(String input) {
        try {
            commandExecutor.execute(input);
            commandTextField.commitAndFlush();
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
     * @param optionalPrefixes
     */
    public void enableSuggestionAndSyntaxHighlightingFor(String com, List<Prefix> pre, List<Prefix> optionalPrefixes) {
        commandTextField.addSupport(com, pre, optionalPrefixes);
    }

    /**
     * Disable syntax highlighting for the specified command.
     * @param command The command word of the command.
     */
    public void disableSuggestionsAndSyntaxHighlightingFor(String command) {
        commandTextField.removeSupport(command);
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
         * @see seedu.moolah.logic.Logic#execute(String, String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException, UnmappedPanelException;
    }

}
