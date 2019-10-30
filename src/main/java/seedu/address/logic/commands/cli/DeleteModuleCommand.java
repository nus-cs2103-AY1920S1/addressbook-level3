package seedu.address.logic.commands.cli;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.SemesterName;

/**
 * Removes a module from a semester
 */
public class DeleteModuleCommand extends Command {
    public static final String COMMAND_WORD = "removemod";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Removing a module from a given semester";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":Removes the specified module to a given semester of the current study plan.\n"
            + "Parameters: "
            + "SEMESTER "
            + "MODULE_CODE\n";

    public static final String MESSAGE_SUCCESS = "Module %1$s removed from %2$s";
    public static final String MODULE_DOES_NOT_EXIST = "This module does not exist in the semester";

    private final SemesterName sem;
    private final String moduleCode;

    public DeleteModuleCommand(String moduleCode, SemesterName sem) {
        requireNonNull(moduleCode);
        requireNonNull(sem);
        this.sem = sem;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.semesterHasModule(this.moduleCode, this.sem)) {
            throw new CommandException(MODULE_DOES_NOT_EXIST);
        }

        model.removeModule(moduleCode, sem);
        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleCode, sem));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && sem.equals(((DeleteModuleCommand) other).sem)
                && moduleCode.equals(((DeleteModuleCommand) other).moduleCode));
    }
}

