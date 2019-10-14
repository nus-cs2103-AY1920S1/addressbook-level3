package seedu.address.logic.commands.cli;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.SemesterName;

public class AddModuleCommand extends Command {
    public static final String COMMAND_WORD = "addmodule";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns the specified module to a given semester of the current study plan.\n. "
            + "Parameters: "
            + PREFIX_SEMESTER + "SEMESTER "
            + PREFIX_MODULE_CODE + "MODULE_CODE\n";

    public static final String MESSAGE_SUCCESS = "Module %1$s added to %2$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the semester";

    private final SemesterName sem;
    private final String moduleCode;

    public AddModuleCommand(String moduleCode, SemesterName sem) {
        requireNonNull(moduleCode);
        requireNonNull(sem);
        this.sem = sem;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.semesterHasModule(this.moduleCode, this.sem)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModule(moduleCode, sem);
        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleCode, sem));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && sem.equals(((AddModuleCommand) other).sem)
                && moduleCode.equals(((AddModuleCommand) other).moduleCode));
    }
}
