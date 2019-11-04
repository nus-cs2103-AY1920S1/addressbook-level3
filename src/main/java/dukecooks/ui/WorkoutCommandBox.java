package dukecooks.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

/**
 * The UI component that is responsible for receiving user command inputs in RunWorkoutWindow.
 */
public class WorkoutCommandBox extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    public WorkoutCommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        this.commandExecutor = commandExecutor;
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String textEntered = commandTextField.getText();
        if (textEntered.equals("done")) {
            commandExecutor.execute();
            commandTextField.setText("");
        } else {
            setStyleToIndicateCommandFailure();
        }
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
         * Executes the command;
         *
         */
        void execute();
    }
}
