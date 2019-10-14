package seedu.address.logic.commands.cli;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.SemesterName;

public class NameUEFromSemesterCommand extends Command {
    public static final String COMMAND_WORD = "nameue";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Names a UE from the given semester with the given module name. "
            + "This feature is purely for aesthetic purposes and has no functional implications.\n"
            + "Parameters: "
            + PREFIX_SEMESTER + "SEMESTER "
            + "MODULE_CODE\n";

    public static final String MESSAGE_SUCCESS = "Module %1$s added to %2$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the semester";

    private final SemesterName sem;
    private final String moduleCode;

    public NameUEFromSemesterCommand(String moduleCode, SemesterName sem) {
        requireNonNull(moduleCode);
        requireNonNull(sem);
        this.sem = sem;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.semesterHasUE(this.sem)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.renameUEInSemester(sem, moduleCode);
        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleCode, sem));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameUEFromSemesterCommand // instanceof handles nulls
                && sem.equals(((NameUEFromSemesterCommand) other).sem)
                && moduleCode.equals(((NameUEFromSemesterCommand) other).moduleCode));
    }
}
