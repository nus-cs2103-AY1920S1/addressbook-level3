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
public class CommandBox extends UiPart<Region>{
    private static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final CommandExecutor commandExecutor;

    private ArrayList<Observer> observers = new ArrayList<>();
    private DataSender dataSender;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, AutoCompletePanel autoCompletePanel) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
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

    public void setOnButtonPressedListener() {
        commandTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
            case UP:
            case DOWN:
                notifyObservers(event.getCode());
                commandTextField.positionCaret(commandTextField.getText().length());
                break;
            case RIGHT:
                try {
                    String[] dataFromSender = receiveFromSender();
                    String textToDisplay = dataFromSender[0];
                    commandTextField.setText(textToDisplay);
                    // Notify observers to update based on textToBeDisplayed
                    notifyObservers(KeyCode.RIGHT, textToDisplay);
                    commandTextField.positionCaret(commandTextField.getText().length());
                } catch (NullPointerException e) {
                    logger.info("Nothing is selected thus right key does not work");
                }
                break;
            case ENTER:
                //handleCommandEntered() will be executed first before this
                notifyObservers(KeyCode.ENTER, commandTextField.getText());
                break;
            case SHIFT:
                String[] dataFromSender = receiveFromSender();
                String textToPassWhenShiftKeyPressed = dataFromSender[1];
                notifyObservers(KeyCode.SHIFT, textToPassWhenShiftKeyPressed);
                break;
            default:
                notifyObservers(event.getCode(), commandTextField.getText() + event.getText());
            }
        });
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(KeyCode keyCode) {
        for (Observer observer : observers) {
            observer.update(keyCode);
        }
    }

    public void notifyObservers(KeyCode keyCode, String resultString) {
        for (Observer observer : observers) {
            observer.update(keyCode, resultString);
        }
    }

    public void setDataSender(DataSender dataSender) {
        this.dataSender = dataSender;
    }

    public String[] receiveFromSender() {
        return dataSender.sendData();
    }
}
