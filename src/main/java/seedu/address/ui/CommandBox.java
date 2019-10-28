package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.exception.EnumNotPresentException;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
//    private final GraphGenerator graphGenerator;

    @FXML
    private AutoCompleteTextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, Logic logic) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        new GraphGenerator(logic);

//        this.commandTextField = new AutoCompleteTextField(new GraphGenerator(logic));

//        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//            // calls #setStyleToDefault() whenever there is a change to the text of the command box.
//            commandTextField.setStyleToDefault();
//            String enteredText = commandTextField.getText();
//            String stringToCompare = enteredText;
//            // hide suggestions if no input
//            if (enteredText == null || enteredText.isEmpty()) {
//                commandTextField.entriesPopup.hide();
//            } else {
//                int firstSpace = enteredText.indexOf(" ");
//                if (firstSpace != -1) {
//                    String commandWord = enteredText.substring(0, firstSpace);
//                    Optional<Graph<?>> graph = graphGenerator.getGraph(commandWord);
//                    if (graph.isPresent()) {
//                        String remaining = enteredText.substring(firstSpace);
//                        SortedSet<String> values = graph.get().process(remaining);
//                        if (remaining.endsWith(" ")) {
//                            commandTextField.entries.clear();
//                            commandTextField.entries.addAll(values);
//                            stringToCompare = "";
//                        } else {
//                            commandTextField.entries.clear();
//                            commandTextField.entries.addAll(values);
//                            stringToCompare = graph.get().lastMatchEnd;
//                        }
//                    }
//                } else {
//                    commandTextField.entries.clear();
//                    commandTextField.entries.addAll(CommandSuggestions.getSuggestions());
//                }
//
//                // filter
//                String finalStringToCompare = stringToCompare;
//                List<String> filteredEntries = commandTextField.entries.stream()
//                        .filter(e -> e.toLowerCase().contains(finalStringToCompare.toLowerCase()))
//                        .sorted((e1, e2) -> commandTextField.compareEntries(e1, e2, finalStringToCompare))
//                        .collect(Collectors.toList());
//                if (!filteredEntries.isEmpty() && !filteredEntries.contains(finalStringToCompare)) {
//                    commandTextField.populatePopup(filteredEntries, stringToCompare);
//                    commandTextField.refreshDropdown();
//                } else {
//                    commandTextField.entriesPopup.hide();
//                }
//            }
//        });
//
//        commandTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
//            commandTextField.entriesPopup.hide();
//        }));
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            String before = commandTextField.getText();
            System.out.println("Before: " + before);
            commandExecutor.execute(commandTextField.getText());
            System.out.println("After: " + commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException | EnumNotPresentException e) {
            setStyleToIndicateCommandFailure();
        }
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
        CommandResult execute(String commandText) throws CommandException, ParseException, EnumNotPresentException;
    }

}
