package seedu.address.ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.autocomplete.AutoComplete;
import seedu.address.ui.autocomplete.QueryCard;

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
    private ListView<String> acSuggestions;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        autoCompleteListener();
    }

    /**
     * Handles the autofill event
     */
    private void autoCompleteListener() {
        AutoComplete.initAc();
        commandTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                String[] terms = newValue.split(" ");
                String latestQuery = terms[terms.length - 1];
                ObservableList<String> suggestions =
                    FXCollections.observableArrayList(AutoComplete.getSuggestions(latestQuery));
                acSuggestions.setItems(suggestions);
                acSuggestions.setCellFactory(listView -> new AutocompleteListViewCell());
                commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.DOWN) {
                            acSuggestions.requestFocus();
                            acSuggestions.getSelectionModel().selectFirst();
                        }
                    }
                });
                acSuggestions.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        setTextField(latestQuery);
                        acSuggestions.getItems().clear();
                    }
                });
                acSuggestions.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER) {
                            setTextField(latestQuery);
                            acSuggestions.getItems().clear();
                        }
                    }
                });
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        commandTextField.positionCaret(commandTextField.getLength());
                    }
                });
            }
        });
    }

    private void setTextField(String caretSelection) {
        String currentText = commandTextField.getText();
        String sub = currentText.substring(0, currentText.lastIndexOf(caretSelection));
        commandTextField.setText(sub + acSuggestions.getSelectionModel().getSelectedItem());
        commandTextField.requestFocus();
        commandTextField.positionCaret(commandTextField.getLength());
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
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Query} using a {@code QueryCard}.
     */
    class AutocompleteListViewCell extends ListCell<String> {

        @Override
        protected void updateItem(String query, boolean empty) {
            super.updateItem(query, empty);

            if (empty || query == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new QueryCard(query).getRoot());
            }
        }
    }
}
