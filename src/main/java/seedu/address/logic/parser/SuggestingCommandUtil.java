package seedu.address.logic.parser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.*;

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
            NusmodCommand.COMMAND_WORD,
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
