package mams.logic.commands;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import mams.logic.commands.exceptions.CommandException;
import mams.logic.history.FilterOnlyCommandHistory;
import mams.model.Model;
import mams.model.ReadOnlyMams;
import mams.storage.JsonMamsStorage;

/**
 * saves data under mamshistory_tag.json
 */
public class SaveCommand extends StoreCommand {
    private String tag = "";

    public SaveCommand(String tag) {
        this.tag = tag;
    }

    public SaveCommand() {
        this.tag = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        if (this.tag.equals("")) {
            this.tag = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        }
        ReadOnlyMams mamsToSave = model.getMams();
        JsonMamsStorage history = new JsonMamsStorage(Paths.get("data/mamshistory_" + this.tag + ".json"));
        try {
            history.saveMams(mamsToSave);
        } catch (IOException e) {
            throw new CommandException("Unable to backup");
        }
        return new CommandResult("Backup Successful, Saved in \"data/mamshistory_ "
                + this.tag + ".json\"");

    }

    /** execute used for undo and redo objects
     *
     * @param model
     * @return
     * @throws CommandException
     */
    public CommandResult privateExecute(Model model) throws CommandException {
        ReadOnlyMams mamsToSave = model.getMams();
        JsonMamsStorage history = new JsonMamsStorage(Paths.get("data/mamshistory_" + this.tag + ".json"));
        try {
            history.saveMams(mamsToSave);
        } catch (IOException e) {
            throw new CommandException("Unable to save");
        }
        return new CommandResult("Save Successful ");
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
