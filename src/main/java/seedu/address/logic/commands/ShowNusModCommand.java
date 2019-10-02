package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import org.json.simple.JSONObject;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.websocket.NusModApi;

/**
 * Gets details about a module from NusMods
 */
public class ShowNusModCommand extends Command {
    public static final String COMMAND_WORD = "showmod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_MODULE_CODE + "MODULE_CODE "
            + PREFIX_SEMESTER + "SEMESTER\n";

    private final String moduleCode;
    private final String semesterNo;

    public ShowNusModCommand(String moduleCode, String semesterNo) {
        this.moduleCode = moduleCode;
        this.semesterNo = semesterNo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        NusModApi api = new NusModApi();
        JSONObject obj = api.getModule(moduleCode);

        String result = "";
        if (obj == null) {
            result = "Error! Unable to get module details";
            return new CommandResult(result);
        } else {
            Module module = new Module(obj);
            result += module.toString() + "\n";
            result += module.getDescription().toString() + "\n";
            result += module.getSemester(semesterNo).toString();
            return new CommandResult(result);
        }
    }

    @Override
    public boolean equals(Command command) {
        return false;
    }
}
