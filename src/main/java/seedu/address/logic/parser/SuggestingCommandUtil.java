package seedu.address.logic.parser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddToGroupCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.commands.ShowNusModCommand;

/**
 * Contains utility methods used for suggesting user commands in the *SuggestingCommandBox classes.
 */
public class SuggestingCommandUtil {
    private static ObservableList<String> commandWords = FXCollections.observableArrayList(
            AddEventCommand.COMMAND_WORD,
            AddGroupCommand.COMMAND_WORD,
            AddPersonCommand.COMMAND_WORD,
            AddToGroupCommand.COMMAND_WORD,
            DeleteGroupCommand.COMMAND_WORD,
            DeletePersonCommand.COMMAND_WORD,
            EditGroupCommand.COMMAND_WORD,
            EditPersonCommand.COMMAND_WORD,
            FindGroupCommand.COMMAND_WORD,
            ShowNusModCommand.COMMAND_WORD,
            ShowCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD
    );
    private static ObservableList<String> readOnlyCommandWords = FXCollections.unmodifiableObservableList(commandWords);

    public static ObservableList<String> getCommandWords() {
        return readOnlyCommandWords;
    }
}
