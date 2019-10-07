package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import org.json.simple.JSONArray;
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
        requireNonNull(model);

        NusModApi api = new NusModApi();
        JSONObject moduleListJson = api.getModuleList();

        // call api to get all necessary info and store in hard disk
        // for each moduleCode in moduleListJson
//        model.findModule(moduleCode);
//        if not found,
    //        api.getModule(moduleCode)
//        model.addModule(moduleCode);

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
