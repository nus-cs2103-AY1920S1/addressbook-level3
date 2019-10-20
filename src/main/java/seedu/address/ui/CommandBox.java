package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.BlockCurrentSemesterCommand;
import seedu.address.logic.commands.cli.DeleteModuleCommand;
import seedu.address.logic.commands.cli.NameUeFromSemesterCommand;
import seedu.address.logic.commands.cli.SetCurrentSemesterCommand;
import seedu.address.logic.commands.datamanagement.CreateTagCommand;
import seedu.address.logic.commands.datamanagement.DeleteTagCommand;
import seedu.address.logic.commands.datamanagement.FindCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromAllCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromModuleCommand;
import seedu.address.logic.commands.datamanagement.TagModuleCommand;
import seedu.address.logic.commands.datamanagement.ViewModuleTagsCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.logic.commands.storage.ActivateStudyPlanCommand;
import seedu.address.logic.commands.storage.CommitStudyPlanEditCommand;
import seedu.address.logic.commands.storage.CreateStudyPlanCommand;
import seedu.address.logic.commands.storage.DeleteCommand;
import seedu.address.logic.commands.storage.EditTitleCommand;
import seedu.address.logic.commands.storage.ListAllStudyPlansCommand;
import seedu.address.logic.commands.storage.ViewCommitHistoryCommand;
import seedu.address.logic.commands.verification.CheckCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final List<String> commandKeywords = new ArrayList<>();
    private final List<String> argumentKeywords = new ArrayList<>();

    private final CommandExecutor commandExecutor;

    @FXML
    private StackPane commandBox;

    private Autocomplete autocomplete;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        generateKeywords();
        autocomplete = new Autocomplete(commandKeywords);
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        autocomplete.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        autocomplete.setId("commandTextField");
        autocomplete.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                handleCommandEntered();
            } else if (keyEvent.getCode() == KeyCode.TAB) {
                autocomplete.handleAutocomplete();
                keyEvent.consume();
            }
        });
        commandBox.getChildren().add(autocomplete);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(autocomplete.getText());
            autocomplete.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        autocomplete.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = autocomplete.getStyleClass();

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
     * Generates the lists of required keywords.
     */
    private void generateKeywords() {
        commandKeywords.add(AddModuleCommand.COMMAND_WORD);
        commandKeywords.add(BlockCurrentSemesterCommand.COMMAND_WORD);
        commandKeywords.add(DeleteModuleCommand.COMMAND_WORD);
        commandKeywords.add(NameUeFromSemesterCommand.COMMAND_WORD);
        commandKeywords.add(SetCurrentSemesterCommand.COMMAND_WORD);
        commandKeywords.add(FindCommand.COMMAND_WORD);
        commandKeywords.add(CheckCommand.COMMAND_WORD);
        commandKeywords.add(CommitStudyPlanEditCommand.COMMAND_WORD);
        commandKeywords.add(CreateStudyPlanCommand.COMMAND_WORD);
        commandKeywords.add(DeleteCommand.COMMAND_WORD);
        commandKeywords.add(CreateTagCommand.COMMAND_WORD);
        commandKeywords.add(TagModuleCommand.COMMAND_WORD);
        commandKeywords.add(ViewCommitHistoryCommand.COMMAND_WORD);
        commandKeywords.add(RemoveTagFromModuleCommand.COMMAND_WORD);
        commandKeywords.add(ViewModuleTagsCommand.COMMAND_WORD);
        commandKeywords.add(DeleteTagCommand.COMMAND_WORD);
        commandKeywords.add(RemoveTagFromAllCommand.COMMAND_WORD);
        commandKeywords.add(EditTitleCommand.COMMAND_WORD);
        commandKeywords.add(ActivateStudyPlanCommand.COMMAND_WORD);
        commandKeywords.add(ListAllStudyPlansCommand.COMMAND_WORD);
        commandKeywords.add(HelpCommand.COMMAND_WORD);
    }

}
