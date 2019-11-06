package seedu.address.logic.commands.verification;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.semester.SemesterName;
import seedu.address.ui.ResultViewType;

/**
 * Finds a list of all valid modules that can be taken in a semester, after checking for prerequisites.
 */
public class ValidModsCommand extends Command {

    public static final String COMMAND_WORD = "validmods";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Viewing valid modules that can be taken";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows all valid modules that can be taken in a given semester, based only on module prerequisites.\n"
            + "Parameters: SEMESTER\n"
            + "Example: " + COMMAND_WORD + " y1s1";
    public static final String MESSAGE_SUCCESS = "All valid modules in %s are shown";

    private final SemesterName semName;

    public ValidModsCommand(SemesterName semName) {
        this.semName = semName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Module> validMods = model.getValidMods(this.semName);
        UniqueModuleList moduleList = new UniqueModuleList();
        for (Module mod : validMods) {
            moduleList.add(mod);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, semName.toString()), ResultViewType.MODULE,
                moduleList.asUnmodifiableObservableList());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ValidModsCommand)) {
            return false;
        }

        // state check
        ValidModsCommand e = (ValidModsCommand) other;
        return semName.equals(e.semName);
    }
}
