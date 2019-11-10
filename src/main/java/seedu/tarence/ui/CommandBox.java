package seedu.tarence.ui;

import static javafx.geometry.Pos.BASELINE_LEFT;
import static javafx.geometry.Pos.BASELINE_RIGHT;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.tarence.MainApp;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final InputExecutor commandExecutor;
    private final InputExecutor autocompleteExecutor;
    private final InputExecutor nextSuggestionExecutor;
    private final InputExecutor inputChangedExecutor;
    private final InputExecutor pastInputExecutor;
    private final InputExecutor inputFieldFocusExecutor;
    private final InputExecutor scrollPanelExecutor;

    @FXML
    private TextField commandTextField;

    @FXML
    private TextFlow commandTextFlow;

    @FXML
    private Text autocompleteTextBox;

    public CommandBox(InputExecutor commandExecutor, InputExecutor autocompleteExecutor,
                      InputExecutor nextSuggestionExecutor,
                      InputExecutor inputChangedExecutor, InputExecutor pastInputExecutor,
                      InputExecutor inputFieldFocusExecutor, InputExecutor scrollPanelExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.autocompleteExecutor = autocompleteExecutor;
        this.nextSuggestionExecutor = nextSuggestionExecutor;
        this.inputChangedExecutor = inputChangedExecutor;
        this.pastInputExecutor = pastInputExecutor;
        this.inputFieldFocusExecutor = inputFieldFocusExecutor;
        this.scrollPanelExecutor = scrollPanelExecutor;

        // actions to carry out whenever text field content changes
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();
            try {
                handlePastInput(null);
                handleAutocomplete();
            } catch (ParseException | CommandException e) {
                e.printStackTrace();
            }
        });

        Font.loadFont(MainApp.class.getResource("/fonts/RobotoMono-Light.ttf").toExternalForm(), 12);
        Font.loadFont(MainApp.class.getResource("/fonts/RobotoMono-Thin.ttf").toExternalForm(), 12);
        commandTextField.prefColumnCountProperty().bind(commandTextField.textProperty().length());
        commandTextField.setMinWidth(10.0); // to make the field clickable
        commandTextField.setPadding(new Insets(5.0, 0.0, 5.0, 10.0));

        commandTextField.setAlignment(BASELINE_RIGHT);

        autocompleteTextBox.setFill(Color.GRAY);

        commandTextFlow.getChildren().set(1, autocompleteTextBox);
    }

    public void setInput(String autocompletedString) {
        commandTextField.setText(autocompletedString);
        commandTextField.requestFocus();
        commandTextField.positionCaret(autocompletedString.length());
    }

    /**
     * Sets the contents of the autocomplete box to the given autofill string.
     */
    public void setAutocompleteBox(String autocompletedString) {
        autocompleteTextBox.setText(autocompletedString);
    }

    /**
     * Clears the autocomplete box.
     */
    public void clearAutocompleteBox() {
        autocompleteTextBox.setText("");
    }

    /**
     * Helper method to reset focus to CommandBox.
     */
    public void setFocus() {
        commandTextField.requestFocus();
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
            setFocus();
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
            setFocus();
        }
    }

    /**
     * Handles the Tab button pressed event.
     */
    @FXML
    private void handleAutocomplete() throws CommandException, ParseException {
        autocompleteExecutor.execute(commandTextField.getText());
    }

    /**
     * Handles the Control button pressed event.
     */
    @FXML
    private void handleNextSuggestion() throws CommandException, ParseException {
        try {
            nextSuggestionExecutor.execute("");
        } catch (IndexOutOfBoundsException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles the Up button pressed event.
     * @param code
     * @param KeyCode
     */
    @FXML
    private void handlePastInput(KeyCode keyCode) throws CommandException, ParseException {
        if (keyCode.equals(KeyCode.UP)) {
            pastInputExecutor.execute("up");
        } else if (keyCode.equals(KeyCode.DOWN)) {
            pastInputExecutor.execute("down");
        } else {
            pastInputExecutor.execute("");
        }
    }

    /**
     * Handles the Tab button pressed event.
     */
    @FXML
    private void handleAutofillWithSuggestion() {
        if (autocompleteTextBox.getText().equals("")) {
            // Invalid autofill request
            setStyleToIndicateCommandFailure();
        }

        setInput(commandTextField.getText() + autocompleteTextBox.getText());
        clearAutocompleteBox();

    }

    /**
     * Handles the PageDown and PageUp button pressed events.
     */
    private void handleScrollPanel(KeyCode keyCode) throws CommandException, ParseException {
        if (keyCode.equals(KeyCode.PAGE_DOWN)) {
            scrollPanelExecutor.execute("down");
        } else {
            scrollPanelExecutor.execute("up");
        }
    }

    /**
     * Dispatches events based on button press inputs from the user.
     */
    @FXML
    private void handleKeyPressed(KeyEvent keyEvent) throws CommandException, ParseException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleCommandEntered();
        } else if (keyEvent.getCode().equals(KeyCode.TAB)) {
            handleAutofillWithSuggestion();
        } else if (keyEvent.getCode().equals(KeyCode.CONTROL)) {
            handleNextSuggestion();
        } else if (keyEvent.getCode().equals(KeyCode.UP) || keyEvent.getCode().equals(KeyCode.DOWN)) {
            handlePastInput(keyEvent.getCode());
        } else if (keyEvent.getCode().equals(KeyCode.PAGE_DOWN) || keyEvent.getCode().equals(KeyCode.PAGE_UP)) {
            handleScrollPanel(keyEvent.getCode());
        } else if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
            commandTextField.setAlignment(BASELINE_LEFT);
        } else {
            commandTextField.setAlignment(BASELINE_RIGHT);
        }

    }

    /**
     * Handles user clicking on autocomplete text box.
     */
    @FXML
    private void handleClick() throws CommandException, ParseException {
        inputFieldFocusExecutor.execute("");
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
     * Updates commandTextField max width based on window width
     */
    void updateWindowWidth(double newWidth) {
        commandTextField.setMaxWidth(newWidth - 100);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface InputExecutor {
        /**
         * Executes an action based on the user's input and returns the result.
         *
         * @see seedu.tarence.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
