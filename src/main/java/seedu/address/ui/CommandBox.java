package seedu.address.ui;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Region;
import seedu.address.logic.actions.Action;
import seedu.address.logic.CommandBoxHelper;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.ModeEnum;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private CommandBoxHelper commandBoxHelper;

    @FXML
    private TextField commandTextField;

    @FXML
    private MenuBar commandMenuField;

    @FXML
    private ComboBox<String> commandComboField;

    private Menu temp;
    private List<Menu> currentMenus;
    private List<String> currentCombos;

    public CommandBox(CommandExecutor commandExecutor, CommandBoxHelper commandBoxHelper) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandBoxHelper = commandBoxHelper;


        initialiseText();
        fillMenu();
        fillCombo();

    }

    private void initialiseText() {
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        commandTextField.textProperty().addListener((observable, oldCommand, newCommand) -> {
            System.out.println("command changing from " + oldCommand + " to " + newCommand);
            updateMenu(oldCommand, newCommand);
        });
    }

    private void updateMenu(String oldCommand, String newCommand) {

    }

    private void fillMenu() {
        for (Action temp : commandBoxHelper.getMenuItems("")) {
            Label label = new Label(temp.toString());
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    commandTextField.setText(temp.toString() + " ");
                }
            });
            Menu button = new Menu();
            button.setGraphic(label);
            commandMenuField.getMenus().add(button);
        }
    }

    private void fillCombo() {
        List<ModeEnum> temp = commandBoxHelper.getModes();
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        try {
                            commandExecutor.execute(commandComboField.getValue());
                        } catch (CommandException | ParseException ex) {
                            setStyleToIndicateCommandFailure();
                        }

                    }
                };
        commandComboField.setOnAction(event);
        for (ModeEnum mode : temp) {
            commandComboField.getItems().add(mode.toString());
        }
    }


    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
        commandComboField.setValue(commandBoxHelper.getMode().toString());
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
