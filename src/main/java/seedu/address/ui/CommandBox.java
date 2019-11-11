package seedu.address.ui;

import java.util.List;
import java.util.function.UnaryOperator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.search.AutoComplete;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private static final char separatorSpace = ' ';
    private static final char separatorSlash = '/';

    private final CommandExecutor commandExecutor;
    private final List<String> history;
    private int caretPos = 0;
    private int anchorPos = 0;
    private String newText;
    private ListElementPointer historySnapshot;

    @FXML
    private TextField commandTextField;

    @FXML
    private ListView<String> acSuggestions;

    public CommandBox(CommandExecutor commandExecutor, List<String> history) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.history = history;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = new ListElementPointer(history);
        handleHistoryNavigation();
        caretChangeListener();
        autoCompleteListener();
    }

    /*
    Following is another approach, therefore commented out
     */
    //    /**
    //     * Handles the autofill event
    //     */
    //    private void autoCompleteListener() {
    //        AutoComplete.initAc();
    //        commandTextField.textProperty().addListener(new ChangeListener<String>() {
    //
    //            @Override
    //            public void changed(ObservableValue<? extends String> observable,
    //                                String oldText, String newText) {
    //
    //                // Following will return previous caret position
    //                commandTextField.getCaretPosition();
    //
    //                commandTextField.caretPositionProperty().addListener(new ChangeListener<Number>() {
    //                    @Override
    //                    public void changed(ObservableValue<? extends Number> observable, Number oldCursor,
    //                                        Number newCursor) {
    //                        if (newCursor.intValue() >= 1) {
    //                            String targetText = commandTextField.getText(0, newCursor.intValue());
    //                            int indexSpace = targetText.lastIndexOf(' ');
    //                            int indexSlash = targetText.lastIndexOf('/');
    //                            String[] terms = null;
    //                            if (indexSpace > indexSlash) {
    //                                terms = newText.split(" ");
    //                            } else if (indexSpace < indexSlash) {
    //                                terms = newText.split("/");
    //                            }
    ////                if (identifier == '/') {
    ////                    terms = newValue.split("/");
    ////                } else {
    ////                    terms = newValue.split(" ");
    ////                }
    ////                String latestQuery = terms[terms.length - 1];
    ////                        String queryFirst = termsSpace[termsSpace.length - 1];
    ////                        String querySecond = termsSlash[termsSlash.length - 1];
    //                            String latestQuery = terms == null ? newText : terms[terms.length - 1];
    ////                int tst = commandTextField.getCaretPosition();
    ////                ObservableList<String> suggestions =
    ////                    FXCollections.observableArrayList(AutoComplete.getSuggestions(latestQuery));
    ////                            ObservableList<String> suggestionsFirst =
    ////                                FXCollections.observableArrayList(AutoComplete.getSuggestions(queryFirst));
    ////                            ObservableList<String> suggestionsSecond =
    ////                                FXCollections.observableArrayList(AutoComplete.getSuggestions(querySecond));
    ////                            List<String> suggestions = new ArrayList<>();
    ////                            suggestions.addAll(AutoComplete.getSuggestions(queryFirst));
    ////                            suggestions.addAll(AutoComplete.getSuggestions(querySecond));
    ////                            Set<String> suggestionSet = new HashSet<>(suggestions);
    ////                            suggestions = suggestions.stream().distinct().collect(Collectors.toList());
    ////                            ObservableList<String> suggestionsFinal = FXCollections.observableArrayList
    ////                            (suggestions);
    ////                acSuggestions.setItems(suggestions);
    //                            ObservableList<String> suggestions =
    //                                FXCollections.observableArrayList(AutoComplete.getSuggestions(latestQuery));
    //                            acSuggestions.setItems(suggestions);
    //                            acSuggestions.setCellFactory(listView -> new AutocompleteListViewCell());
    //                            commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
    //                                @Override
    //                                public void handle(KeyEvent event) {
    //                                    if (event.getCode() == KeyCode.DOWN) {
    //                                        acSuggestions.requestFocus();
    //                                        acSuggestions.getSelectionModel().selectFirst();
    //                                    }
    //                                }
    //                            });
    //                            acSuggestions.setOnMouseClicked(new EventHandler<MouseEvent>() {
    //                                @Override
    //                                public void handle(MouseEvent event) {
    //                                    setTextField(latestQuery, newCursor);
    //                                    acSuggestions.getItems().clear();
    //                                }
    //                            });
    //                            acSuggestions.setOnKeyPressed(new EventHandler<KeyEvent>() {
    //                                @Override
    //                                public void handle(KeyEvent event) {
    //                                    if (event.getCode() == KeyCode.ENTER) {
    //                                        setTextField(latestQuery, newCursor);
    //                                        acSuggestions.getItems().clear();
    //                                    }
    //                                }
    //                            });
    ////                            Platform.runLater(new Runnable() {
    ////                                @Override
    ////                                public void run() {
    ////                                    commandTextField.positionCaret(commandTextField.getLength());
    ////                                }
    ////                            });
    //                        }
    //                    }
    //                });
    //            }
    //        });
    //    }

    //    private void caretListener() {
    //
    //        commandTextField.caretPositionProperty().addListener(new ChangeListener<Number>() {
    //            @Override
    //            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    //                caretPos = newValue.intValue();
    //            }
    //        });
    //      }

    /**
     * Detect change of caret position. This method is used for retrieving the cursor position.
     */
    private void caretChangeListener() {
        TextFormatter<?> formatter = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
            if (commandTextField.isFocused()) {
                // Hack "UP" and "DOWN" key
                if ((change.isContentChange() || change.getAnchor() != change.getControlNewText().length()) && (
                    change.isContentChange() || change.getAnchor() != 0)) {
                    anchorPos = change.getAnchor();
                    caretPos = change.getCaretPosition();
                    newText = change.getControlNewText();
                    acSuggestions.getItems().clear();
                }
            }
            return change;
        });
        commandTextField.setTextFormatter(formatter);
    }

    /**
     * Handles the autofill event
     */
    private void autoCompleteListener() {
        AutoComplete.initAc();
        commandTextField.textProperty().addListener((observable, oldText, newText) -> {
            if (caretPos >= 1) {
                String targetText = newText.substring(0, Math.min(anchorPos, caretPos));
                String latestQuery;
                // Customized Separators
                int indexSpace = targetText.lastIndexOf(separatorSpace);
                int indexSlash = targetText.lastIndexOf(separatorSlash);
                String[] terms = null;
                // Ignore separator
                if (indexSpace == targetText.length() || indexSlash == targetText.length()) {
                    return;
                }
                if (indexSpace > indexSlash) {
                    terms = targetText.split(" ");
                } else if (indexSpace < indexSlash) {
                    terms = targetText.split("/");
                }
                if (terms == null) {
                    latestQuery = targetText;
                } else if (terms.length == 0) {
                    return;
                } else {
                    latestQuery = terms[terms.length - 1];
                }
                ObservableList<String> suggestions =
                    FXCollections.observableArrayList(AutoComplete.getSuggestions(latestQuery));
                acSuggestions.setItems(suggestions);
                acSuggestions.setCellFactory(listView -> new AutocompleteListViewCell());

                /*
                Press DOWN to navigate to suggestion list.
                Press TAB to autocomplete the first word.
                 */
                commandTextField.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.DOWN) {
                        if (!(acSuggestions.getItems().isEmpty())) {
                            acSuggestions.requestFocus();
                            acSuggestions.getSelectionModel().selectFirst();
                        }
                    } else if (event.getCode() == KeyCode.TAB) {
                        acSuggestions.requestFocus();
                        setFirstEntry(latestQuery);
                        acSuggestions.getItems().clear();
                    }
                });

                /*
                Click on the word to autofill
                 */
                acSuggestions.setOnMouseClicked(event -> {
                    if (acSuggestions.getSelectionModel().getSelectedItem() != null) {
                        setTextField(latestQuery);
                        acSuggestions.getItems().clear();
                    }
                });

                /*
                Press ENTER or TAB when a word is selected to autofill
                */
                acSuggestions.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                        setTextField(latestQuery);
                        acSuggestions.getItems().clear();
                    }
                });
            }
        });
    }

    /**
     * Update textField based on the query and caret position
     *
     * @param currentQuery target query to be replaced
     */
    private void setTextField(String currentQuery) {
        StringBuilder sb = new StringBuilder(newText);
        int diffLength = caretPos - currentQuery.length();
        if (caretPos == anchorPos) {
            sb.replace(diffLength, caretPos,
                acSuggestions.getSelectionModel().getSelectedItem());
        }
        int newCaretPos = diffLength + acSuggestions.getSelectionModel().getSelectedItem().length();
        commandTextField.setText(sb.toString());
        commandTextField.requestFocus();
        /*
        A bit of hack to force the caret appear at updated position
         */
        commandTextField.positionCaret(newCaretPos);
    }

    /**
     * Automatically set the first word to textField
     *
     * @param currentQuery target query to be filled
     */
    private void setFirstEntry(String currentQuery) {
        StringBuilder sb = new StringBuilder(newText);
        acSuggestions.getSelectionModel().selectFirst();
        int diffLength = caretPos - currentQuery.length();
        if (caretPos == anchorPos) {
            sb.replace(diffLength, caretPos,
                acSuggestions.getSelectionModel().getSelectedItem());
        }
        int newCaretPos = diffLength + acSuggestions.getSelectionModel().getSelectedItem().length();
        commandTextField.setText(sb.toString());
        commandTextField.requestFocus();
        /*
        A bit of hack to force the caret appear at updated position
         */
        commandTextField.positionCaret(newCaretPos);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            initHistory();
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = new ListElementPointer(history);
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
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
     * Handles the key for navigating history, {@code keyCombination}.
     */
    private void handleHistoryNavigation() {
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && KeyCombination.valueOf("F3").match(event)) {
                event.consume();
                navigateToPreviousInput();
            } else if (event.getTarget() instanceof TextInputControl && KeyCombination.valueOf("F4").match(event)) {
                event.consume();
                navigateToNextInput();
            }
        });
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
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
