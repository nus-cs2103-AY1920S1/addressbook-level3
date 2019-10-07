package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import org.json.simple.JSONObject;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.SemesterNo;
import seedu.address.websocket.NusModApi;

/**
 * Gets details about a module from NusMods
 */
public class ShowNusModCommand extends Command {
    public static final String COMMAND_WORD = "showmod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_MODULE_CODE + "MODULE_CODE "
            + PREFIX_SEMESTER + "SEMESTER\n";

    public static final String MESSAGE_MODULE_NOT_FOUND = "Unable to get module details";

    private final ModuleCode moduleCode;
    private final SemesterNo semesterNo;

    public ShowNusModCommand(ModuleCode moduleCode, SemesterNo semesterNo) {
        this.moduleCode = moduleCode;
        this.semesterNo = semesterNo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Module module = model.findModule(moduleCode);
        if (module == null) {
            NusModApi api = new NusModApi();
            JSONObject obj = api.getModule(moduleCode);
            if (obj == null) {
                return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
            } else {
                module = new Module(obj);
            }
        }

        String result = module.toString() + "\n";
        result += module.getDescription().toString() + "\n";
        result += module.getSemester(semesterNo).toString();
        return new CommandResult(result);
    }

    @Override
    public boolean equals(Command command) {
        return false;
    }
}
