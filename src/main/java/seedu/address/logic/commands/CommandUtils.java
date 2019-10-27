package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.util.Tuple;
import seedu.address.logic.commands.addcommand.AddCommand;
import seedu.address.logic.commands.csvcommand.ExportCommand;
import seedu.address.logic.commands.csvcommand.ImportCommand;
import seedu.address.logic.commands.deletecommand.DeleteCommand;
import seedu.address.logic.commands.editcommand.EditCommand;
import seedu.address.logic.commands.findcommand.FindCommand;
import seedu.address.logic.commands.historycommand.RedoCommand;
import seedu.address.logic.commands.historycommand.UndoCommand;
import seedu.address.logic.commands.listcommand.ListCommand;

/**
 * Utils for the commands.
 */
public class CommandUtils {
    public static final List<Tuple<String>> ALL_COMMANDS_IN_HELP = new ArrayList<Tuple<String>>(
        Arrays.asList(
            new Tuple<String>(
                    "Add Command",
                    AddCommand.MESSAGE_USAGE
            ),
            new Tuple<String>(
                    "CSV Import Command",
                    ImportCommand.MESSAGE_USAGE
            ),
            new Tuple<String>(
                    "CSV Export Command",
                    ExportCommand.MESSAGE_USAGE
            ),
            new Tuple<String>(
                    "Delete Command",
                    DeleteCommand.MESSAGE_USAGE
            ),
            new Tuple<String>(
                    "Edit Command",
                    EditCommand.MESSAGE_USAGE
            ),
            new Tuple<String>(
                    "Find Command",
                    FindCommand.MESSAGE_USAGE
            ),
            new Tuple<String>(
                    "List Command",
                    ListCommand.MESSAGE_USAGE
            ),
            new Tuple<String>(
                    "Undo Command",
                    UndoCommand.MESSAGE_USAGE
            ),
            new Tuple<String>(
                    "Redo Command",
                    RedoCommand.MESSAGE_USAGE
            ),
            new Tuple<String>(
                    "Help Command",
                    HelpCommand.MESSAGE_USAGE
            )
        )
    );
}
