//@@author francislow

package cs.f10.t1.nursetraverse.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import cs.f10.t1.nursetraverse.commons.core.LogsCenter;
import cs.f10.t1.nursetraverse.logic.Logic;
import cs.f10.t1.nursetraverse.logic.commands.CommandResult;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {
    private static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final CommandExecutor commandExecutor;

    private ArrayList<UiObserver> uiObservers = new ArrayList<>();
    private DataSender dataSender;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        setKeyPressListener();
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
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
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    public void setKeyPressListener() {
        commandTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
            case UP:
            case DOWN:
                notifyObserversKeyPressed(event.getCode());
                try {
                    String textToPassWhenUpDownKeyPressed = receiveDataFromSender()[1];
                    notifyObserversToChange(event.getCode(), textToPassWhenUpDownKeyPressed);
                } catch (NullPointerException e) {
                    logger.info("Suggested word list is empty, thus cannot receive anything from sender.");
                }
                commandTextField.positionCaret(commandTextField.getText().length());
                break;
            case SHIFT:
                try {
                    String textToDisplay = receiveDataFromSender()[0];
                    commandTextField.setText(textToDisplay);
                    // Notify observers to update based on textToBeDisplayed
                    notifyObserversKeyPressed(event.getCode());
                    notifyObserversToChange(KeyCode.SHIFT, textToDisplay);
                    commandTextField.positionCaret(commandTextField.getText().length());
                } catch (NullPointerException e) {
                    logger.info("Nothing is selected thus shift key does not work");
                }
                break;
            case ENTER:
                //handleCommandEntered() will be executed first before this
                notifyObserversKeyPressed(event.getCode());
                notifyObserversToChange(KeyCode.ENTER, commandTextField.getText());
                break;
            default:
                notifyObserversKeyPressed(event.getCode());
                notifyObserversToChange(event.getCode(), commandTextField.getText() + event.getText());
            }
        });
    }

    public void addObserver(UiObserver uiObserver) {
        uiObservers.add(uiObserver);
    }

    public void setDataSender(DataSender dataSender) {
        this.dataSender = dataSender;
    }

    private void notifyObserversKeyPressed(KeyCode keyCode) {
        for (UiObserver uiObserver : uiObservers) {
            uiObserver.update(keyCode);
        }
    }

    private void notifyObserversToChange(KeyCode keyCode, String resultString) {
        for (UiObserver uiObserver : uiObservers) {
            uiObserver.update(keyCode, resultString);
        }
    }

    private String[] receiveDataFromSender() {
        return dataSender.sendData();
    }
}
