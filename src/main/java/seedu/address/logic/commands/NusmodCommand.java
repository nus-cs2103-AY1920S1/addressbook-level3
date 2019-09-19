package seedu.address.logic.commands;

import org.json.simple.JSONObject;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.websocket.NusmodApi;

public class NusmodCommand extends Command{
    public static final String COMMAND_WORD = "nusmod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "... to be confirmed";

    private final String moduleCode;

    public NusmodCommand(String moduleCode){
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        NusmodApi api = new NusmodApi();
        JSONObject module = api.getModules(moduleCode);
        String result = module.get("description").toString();

        return new CommandResult(result);
    }
}
