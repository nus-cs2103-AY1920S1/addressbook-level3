package mams.logic.commands;

import java.nio.file.Paths;

import mams.commons.exceptions.DataConversionException;
import mams.logic.commands.exceptions.CommandException;
import mams.logic.history.FilterOnlyCommandHistory;
import mams.model.Model;
import mams.model.ReadOnlyMams;
import mams.storage.JsonMamsStorage;

/**
 * saves data under mamshistory_tag.json
 */
public class RestoreCommand extends StoreCommand {
    private String tag = "";

    public RestoreCommand(String tag) {
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        JsonMamsStorage target = new JsonMamsStorage(Paths.get("data/mamshistory_" + this.getTag() + ".json"));
        ReadOnlyMams mamsToReplace;
        try {
            if (target.readMams().isPresent()) {
                mamsToReplace = target.readMams().get();
            } else {
                throw new DataConversionException(new Exception());
            }
        } catch (DataConversionException e) {
            throw new CommandException("No backup with name found");
        }
        model.replaceMams(mamsToReplace);
        return new CommandResult("Backup  mamshistory_" + this.getTag() + " restored!");
    }

    public String getTag() {
        return this.tag;
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
