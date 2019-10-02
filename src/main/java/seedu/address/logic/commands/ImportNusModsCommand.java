package seedu.address.logic.commands;

import org.json.simple.JSONObject;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.websocket.NusModApi;

/**
 * Import all NusMods data which Timebook requires to storage for offline compatibility.
 */
public class ImportNusModsCommand extends Command {
    public static final String COMMAND_WORD = "importmods";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public ImportNusModsCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        NusModApi api = new NusModApi();

        JSONObject moduleListJson = api.getModuleList();


        String result = "";
        if (moduleListJson == null) {
            result = "Error! Unable to get module details";
            return new CommandResult(result);
        } else {
            result = moduleListJson.toString();
            return new CommandResult(result);
        }
    }

    @Override
    public boolean equals(Command command) {
        return false;
    }
}
