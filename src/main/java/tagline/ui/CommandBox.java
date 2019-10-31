package tagline.ui;

import org.controlsfx.control.textfield.TextFields;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.logic.parser.exceptions.ParseException;
import tagline.ui.exceptions.PromptOngoingException;
import tagline.ui.util.AutoCompleteNode;
import tagline.ui.util.AutoCompleteUtil;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    public static final int AUTO_COMPLETE_MAX_ROWS = 4;

    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final AutoCompleteNode autoCompleteMatcher;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        autoCompleteMatcher = AutoCompleteUtil.getAutoCompleteRootNode();
        TextFields.bindAutoCompletion(commandTextField,
            input -> autoCompleteMatcher.findMatches(input.getUserText()))
            .setVisibleRowCount(AUTO_COMPLETE_MAX_ROWS);

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Sets the enabled status of the auto-completion box.
     */
    public void setAutoCompleteEnabled(boolean enabled) {
        autoCompleteMatcher.setEnabled(enabled);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            setAutoCompleteEnabled(true);
            commandTextField.setText("");
        } catch (PromptOngoingException e) {
            setAutoCompleteEnabled(false);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setAutoCompleteEnabled(true);
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().removeAll(ERROR_STYLE_CLASS);
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
         * @see tagline.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException, PromptOngoingException;
    }

}
