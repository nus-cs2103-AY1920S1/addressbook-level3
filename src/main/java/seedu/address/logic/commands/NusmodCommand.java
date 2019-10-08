package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

import org.json.simple.JSONObject;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.websocket.NusmodApi;

/**
 * Gets details about a module from NusMods
 */
public class NusmodCommand extends Command {
    public static final String COMMAND_WORD = "nusmod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_MODULECODE
            + " <module code> <semester number> <class number>";

    private final String moduleCode;
    private final String semesterNo;
    private final String classNo;

    public NusmodCommand(String moduleCode) {
        this.moduleCode = moduleCode;
        this.semesterNo = null;
        this.classNo = null;
    }

    public NusmodCommand(String moduleCode, String semesterNo) {
        this.moduleCode = moduleCode;
        this.semesterNo = semesterNo;
        this.classNo = null;
    }

    public NusmodCommand(String moduleCode, String semesterNo, String classNo) {
        this.moduleCode = moduleCode;
        this.semesterNo = semesterNo;
        this.classNo = classNo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        NusmodApi api = new NusmodApi();
        JSONObject obj = api.getModules(moduleCode);

        String result = "";
        if (obj == null) {
            result = "Error! Unable to get module details";
            return new CommandResult(result);
        } else {
            Module module = new Module(obj);
            if (semesterNo == null && classNo == null) {
                result += module.getModuleCode().toString() + "\n";
                result += module.getDescription().toString();
            } else if (classNo == null) {
                result += module.getModuleCode().toString() + "\n";
                result += module.getDescription().toString() + "\n";
                result += module.getSemester(semesterNo).toString();
            } else {
                result += module.getModuleCode().toString() + "\n";
                result += module.getDescription().toString() + "\n";
                result += module.getSemester(semesterNo).toString() + "\n";
                result += module.getTimeTable(semesterNo, classNo).toString();
            }
            return new CommandResult(result);
        }

    }

    @Override
    public boolean equals(Command command) {
        return false;
    }
}
