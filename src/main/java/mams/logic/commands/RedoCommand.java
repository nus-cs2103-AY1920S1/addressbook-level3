package mams.logic.commands;

import java.io.File;
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
public class RedoCommand extends StoreCommand {

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        JsonMamsStorage history = new JsonMamsStorage(Paths.get("data/mamshistory_redo.json"));

        ReadOnlyMams mamsToReplace;
        try {
            if (history.readMams().isPresent()) {
                mamsToReplace = history.readMams().get();
            } else {
                throw new DataConversionException(new Exception("DataConversionException"));
            }
            new SaveCommand("undo").privateExecute(model);
            model.replaceMams(mamsToReplace);
            File file = new File("data/mamshistory_redo.json");
            file.delete();

        } catch (DataConversionException e) {
            throw new CommandException("Unable to redo");
        }
        return new CommandResult("Redo Successful ");

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return false;
    }

}
