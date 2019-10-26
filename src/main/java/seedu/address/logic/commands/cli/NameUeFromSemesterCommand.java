package seedu.address.logic.commands.cli;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.SemesterName;

/**
 * Names a UE from a given semester, e.g. UE -> GER1000
 */
public class NameUeFromSemesterCommand extends Command {
    public static final String COMMAND_WORD = "nameue";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Naming a UE";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Names a UE from the given semester with the given module name. "
            + "This feature is purely for aesthetic purposes and has no functional implications.\n"
            + "Parameters: "
            + "SEMESTER "
            + "MODULE_CODE\n";

    public static final String MESSAGE_SUCCESS = "Module %1$s added to %2$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the semester";

    private final SemesterName sem;
    private final String moduleCode;

    public NameUeFromSemesterCommand(String moduleCode, SemesterName sem) {
        requireNonNull(moduleCode);
        requireNonNull(sem);
        this.sem = sem;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.semesterHasUe(this.sem)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.renameUeInSemester(sem, moduleCode);
        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleCode, sem));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameUeFromSemesterCommand // instanceof handles nulls
                && sem.equals(((NameUeFromSemesterCommand) other).sem)
                && moduleCode.equals(((NameUeFromSemesterCommand) other).moduleCode));
    }
}
