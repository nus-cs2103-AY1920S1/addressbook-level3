package seedu.address.logic.parser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;

/**
 * Contains utility methods used for suggesting user commands in the *SuggestingCommandBox classes.
 */
public class SuggestingCommandUtil {
    private static ObservableList<String> commandWords = FXCollections.observableArrayList(
            AddCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD
    );
    private static ObservableList<String> readOnlyCommandWords = FXCollections.unmodifiableObservableList(commandWords);

    public static ObservableList<String> getCommandWords() {
        return readOnlyCommandWords;
    }
}
