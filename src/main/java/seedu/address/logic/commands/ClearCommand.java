package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.entity.UniqueIdentificationNumberMaps;

/**
 * Clears the address book. While the ClearCommand can be undone, it removes all past undone commands from history.
 * Commands executed before it will remain in history: only commands that were executed and undone will be removed.
 * This is to prevent a user trying to redo a command after redoing the ClearCommand, causing an error because Mortago
 * is now empty.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Mortago has been cleared!";
    public static final String MESSAGE_UNDO_SUCCESS = "Mortago has been restored!";

    private ReadOnlyAddressBook savedModel;


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        savedModel = new AddressBook(model.getAddressBook());
        model.setAddressBook(new AddressBook());
        UniqueIdentificationNumberMaps.clearAllEntries();
        SelectCommand selectCommand = new SelectCommand(Integer.MAX_VALUE); // deselect any bodies
        selectCommand.execute(model);

        // so that the lists are not showing only filtered entries from past find/filter commands
        listsAllEntities(model);

        setUndoable();
        model.addExecutedCommand(this);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author shaoyi1997
    /**
     * Lists all the entities.
     */
    private void listsAllEntities(Model model) {
        ListBodyCommand listBodyCommand = new ListBodyCommand();
        listBodyCommand.execute(model);
        ListWorkerCommand listWorkerCommand = new ListWorkerCommand();
        listWorkerCommand.execute(model);
        ListFridgeCommand listFridgeCommand = new ListFridgeCommand();
        listFridgeCommand.execute(model);
    }
    //@@author

    @Override
    public CommandResult undo(Model model) {
        model.clearUndoHistory();
        model.setAddressBook(savedModel);
        setRedoable();
        model.addUndoneCommand(this);
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}
