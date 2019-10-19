package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
// import org.controlsfx.control.textfield.TextFields;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    @FXML
    private MenuBar commandMenuField;

    @FXML
    private ComboBox<String> commandComboField;

    private Menu temp;
    //private ObservableList<String> list = FXCollections.observableArrayList("app", "game", "load");

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        //String[] prompts = {"load", "home", "start"};
        //TextFields.bindAutoCompletion(commandTextField, prompts);
        commandTextField.textProperty().addListener((observable, oldCommand, newCommand) -> {
            System.out.println("command changing from " + oldCommand + " to " + newCommand);
            if (oldCommand.equals("") && newCommand.equals("s")) {
                commandTextField.setText("start ");
                // commandTextField.positionCaret(6);
            }

            if (oldCommand.equals("start ") && newCommand.equals("start")) {
                commandTextField.setText("");

            }
        });
        temp = new Menu("Menu 1");
        commandMenuField.getMenus().add(temp);
        commandComboField.getItems().add("app");
    }


    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        commandMenuField.getMenus().remove(temp);
        try {
            commandExecutor.execute(commandTextField.getText());
        } catch (CommandException | ParseException e) {
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
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    void clearCommandBox() {
        commandTextField.setText("");
    }

    void setGuessTextAndCaret() {
        commandTextField.setText("guess ");
        commandTextField.positionCaret(6);
    }

}
