package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
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
        //syntaxHighlightTextArea.getStyleClass().remove(ERROR_STYLE_CLASS);
        syntaxHighlightTextArea.watch();
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        syntaxHighlightTextArea.overrideStyle(ERROR_STYLE_CLASS);
//        ObservableList<String> styleClass = syntaxHighlightTextArea.getStyleClass();
//
//        if (styleClass.contains(ERROR_STYLE_CLASS)) {
//            return;
//        }
//
//        styleClass.add(ERROR_STYLE_CLASS);
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
