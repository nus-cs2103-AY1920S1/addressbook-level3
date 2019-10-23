package mams.logic.commands;

import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;
import mams.model.ReadOnlyMams;
import mams.storage.JsonMamsStorage;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveCommand extends StoreCommand {
    String tag = "";

    public SaveCommand(String tag) {
        this.tag = tag;
    }

    public SaveCommand() {
        this.tag = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        ReadOnlyMams mamsToSave = model.getMams();
        JsonMamsStorage history = new JsonMamsStorage(Paths.get("data/mamshistory_"+ this.tag +".json"));
        try {
            history.saveMams(mamsToSave);
        }catch (IOException e) {
            throw new CommandException("Unable to save");
        }
        return new CommandResult("Save Successful ");

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
