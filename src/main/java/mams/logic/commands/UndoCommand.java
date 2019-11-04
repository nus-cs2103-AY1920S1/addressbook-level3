package mams.logic.commands;

import java.nio.file.Paths;

import mams.commons.exceptions.DataConversionException;
import mams.logic.commands.exceptions.CommandException;
import mams.logic.history.FilterOnlyCommandHistory;
import mams.model.Model;
import mams.model.ReadOnlyMams;
import mams.storage.JsonMamsStorage;

/**
 * loads data from mamshistory_undo.json
 */
public class UndoCommand extends StoreCommand {

    public UndoCommand() {

    }

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        JsonMamsStorage history = new JsonMamsStorage(Paths.get("data/mamshistory_undo.json"));
        ReadOnlyMams mamsToReplace;
        try {
            if (history.readMams().isPresent()) {
                mamsToReplace = history.readMams().get();
            } else {
                throw new DataConversionException(new Exception());
            }
        } catch (DataConversionException e) {
            throw new CommandException("Unable to undo");
        }
        new SaveCommand("redo").privateExecute(model);
        model.replaceMams(mamsToReplace);
        return new CommandResult("Undo Successful ");

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UndoCommand)) {
            return false;
        }
        return false;
    }

}
